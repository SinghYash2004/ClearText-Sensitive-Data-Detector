package com.cleartext.service;

import com.cleartext.model.DetectedMatch;
import com.cleartext.model.RedactRequest;
import com.cleartext.model.RedactResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RedactionService {
    public RedactResponse redact(RedactRequest request) {
        Set<Integer> selected = new HashSet<>(request.selectedIndexes());
        List<DetectedMatch> matches = request.matches().stream()
                .filter(m -> selected.contains(m.index()))
                .sorted(Comparator.comparingInt(DetectedMatch::start).reversed())
                .toList();

        String result = request.text();
        int count = 0;
        for (DetectedMatch match : matches) {
            // Validate offsets and source value to prevent replacing the wrong substring.
            if (match.start() < 0 || match.end() > result.length()
                    || match.start() >= match.end()
                    || !result.substring(match.start(), match.end()).equals(match.value())) {
                continue;
            }

            String replacement = switch (request.style().toUpperCase()) {
                case "TYPE" -> "[REDACTED: " + (match.type().name().equals("IDENTIFIER")
                        ? request.identifierLabel().toUpperCase() : match.type().name()) + "]";
                case "MASK" -> "•".repeat(Math.max(4, Math.min(match.value().length(), 12)));
                default -> "[REDACTED]";
            };
            result = result.substring(0, match.start()) + replacement + result.substring(match.end());
            count++;
        }
        return new RedactResponse(result, count);
    }
}

package com.cleartext.service;

import com.cleartext.model.DetectedMatch;
import com.cleartext.model.DetectionType;
import com.cleartext.model.ScanRequest;
import com.cleartext.model.ScanResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DetectionService {
    private static final Pattern EMAIL = Pattern.compile(
            "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PHONE = Pattern.compile(
            "(?<!\\w)(?:\\+?\\d{1,3}[\\s.-]?)?(?:\\(?\\d{2,4}\\)?[\\s.-]?)\\d{3,4}[\\s.-]?\\d{3,4}(?!\\w)"
    );
    private static final Pattern LABELLED_ID = Pattern.compile(
            "\\b(?:EMP|ACCT|PAY|CS|ID)[-_ ]?\\d{4,}\\b",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern LONG_NUMBER = Pattern.compile("\\b\\d{9,12}\\b");
    private static final Pattern ID_LABEL = Pattern.compile(
            "\\b(?:reference number|account number|employee id|customer id|identifier)\\s*[:#-]?\\s*[A-Z0-9-]{5,}\\b",
            Pattern.CASE_INSENSITIVE
    );

    public ScanResponse scan(ScanRequest request) {
        String text = request.text();
        List<DetectedMatch> found = new ArrayList<>();

        if (request.detectEmails()) collect(EMAIL, text, DetectionType.EMAIL, found);
        if (request.detectPhones()) collect(PHONE, text, DetectionType.PHONE, found);
        if (request.detectIdentifiers()) {
            collect(LABELLED_ID, text, DetectionType.IDENTIFIER, found);
            collect(ID_LABEL, text, DetectionType.IDENTIFIER, found);
            collect(LONG_NUMBER, text, DetectionType.IDENTIFIER, found);
        }

        found.sort(Comparator.comparingInt(DetectedMatch::start)
                .thenComparing(Comparator.comparingInt(DetectedMatch::end).reversed()));

        // Avoid overlapping spans so redaction offsets remain deterministic.
        List<DetectedMatch> clean = new ArrayList<>();
        for (DetectedMatch match : found) {
            if (clean.isEmpty() || match.start() >= clean.get(clean.size() - 1).end()) {
                clean.add(match);
            } else {
                DetectedMatch previous = clean.get(clean.size() - 1);
                if (match.start() == previous.start() && match.end() > previous.end()) {
                    clean.set(clean.size() - 1, match);
                }
            }
        }

        List<DetectedMatch> indexed = new ArrayList<>();
        for (int i = 0; i < clean.size(); i++) {
            DetectedMatch m = clean.get(i);
            indexed.add(new DetectedMatch(i, m.start(), m.end(), m.value(), m.type()));
        }

        return new ScanResponse(
                indexed.size(),
                indexed.stream().filter(m -> m.type() == DetectionType.EMAIL).count(),
                indexed.stream().filter(m -> m.type() == DetectionType.PHONE).count(),
                indexed.stream().filter(m -> m.type() == DetectionType.IDENTIFIER).count(),
                indexed
        );
    }

    private void collect(Pattern pattern, String text, DetectionType type, List<DetectedMatch> out) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            out.add(new DetectedMatch(-1, matcher.start(), matcher.end(), matcher.group(), type));
        }
    }
}

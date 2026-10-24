package com.cleartext.controller;

import com.cleartext.model.RedactRequest;
import com.cleartext.model.RedactResponse;
import com.cleartext.model.ScanRequest;
import com.cleartext.model.ScanResponse;
import com.cleartext.service.DetectionService;
import com.cleartext.service.RedactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class DetectorController {
    private final DetectionService detectionService;
    private final RedactionService redactionService;

    public DetectorController(DetectionService detectionService, RedactionService redactionService) {
        this.detectionService = detectionService;
        this.redactionService = redactionService;
    }

    @PostMapping("/scan")
    public ScanResponse scan(@Valid @RequestBody ScanRequest request) {
        if (request.text().length() > 1_000_000) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Text must be 1 MB or smaller.");
        }
        return detectionService.scan(request);
    }

    @PostMapping("/redact")
    public RedactResponse redact(@Valid @RequestBody RedactRequest request) {
        if (request.text().length() > 1_000_000) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Text must be 1 MB or smaller.");
        }
        return redactionService.redact(request);
    }

    @GetMapping("/health")
    public String health() {
        return "ClearText API is running";
    }
}

package com.cleartext.model;

import java.util.List;

public record ScanResponse(
        int totalMatches,
        long emailCount,
        long phoneCount,
        long identifierCount,
        List<DetectedMatch> matches
) {}

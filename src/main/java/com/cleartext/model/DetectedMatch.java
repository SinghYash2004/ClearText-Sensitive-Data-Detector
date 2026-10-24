package com.cleartext.model;

public record DetectedMatch(
        int index,
        int start,
        int end,
        String value,
        DetectionType type
) {}

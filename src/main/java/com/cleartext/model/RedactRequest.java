package com.cleartext.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RedactRequest(
        @NotNull String text,
        @NotNull List<DetectedMatch> matches,
        @NotNull List<Integer> selectedIndexes,
        String style,
        String identifierLabel
) {
    public RedactRequest {
        if (text == null) text = "";
        if (matches == null) matches = List.of();
        if (selectedIndexes == null) selectedIndexes = List.of();
        if (style == null) style = "BLOCK";
        if (identifierLabel == null || identifierLabel.isBlank()) identifierLabel = "IDENTIFIER";
    }
}

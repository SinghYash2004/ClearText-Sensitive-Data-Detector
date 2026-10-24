package com.cleartext.model;

import jakarta.validation.constraints.NotNull;

public record ScanRequest(
        @NotNull String text,
        boolean detectEmails,
        boolean detectPhones,
        boolean detectIdentifiers
) {
    public ScanRequest {
        if (text == null) text = "";
    }
}

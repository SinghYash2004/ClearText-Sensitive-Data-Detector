package com.cleartext.service;

import com.cleartext.model.ScanRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectionServiceTest {
    private final DetectionService service = new DetectionService();

    @Test
    void detectsEmailAndIdentifier() {
        var result = service.scan(new ScanRequest(
                "Contact jane.doe@example.com, Employee ID: EMP-12345",
                true, false, true
        ));
        assertTrue(result.emailCount() >= 1);
        assertTrue(result.identifierCount() >= 1);
    }

    @Test
    void emptyTextReturnsNoMatches() {
        var result = service.scan(new ScanRequest("", true, true, true));
        assertEquals(0, result.totalMatches());
    }
}

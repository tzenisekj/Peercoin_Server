package com.peercoin.web.responses;

import org.springframework.http.ResponseEntity;

public class SuccessResponses {
    public static ResponseEntity<String> success(String successData) {
        return ResponseEntity.ok("{\"success\": \"" + successData + "\"}");
    }
}

package com.peercoin.web.responses;

import org.springframework.http.ResponseEntity;

public class FailureResponses {
    public static ResponseEntity<String> failure(String failureBody) {
        return ResponseEntity.badRequest().body("{\"failure\": \"" + failureBody + "\"}");
    }
}

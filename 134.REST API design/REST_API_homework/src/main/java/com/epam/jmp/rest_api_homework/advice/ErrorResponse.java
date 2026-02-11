package com.epam.jmp.rest_api_homework.advice;

// ErrorResponse.java
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // Constructors, getters, setters
}

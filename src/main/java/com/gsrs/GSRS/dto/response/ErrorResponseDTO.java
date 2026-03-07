package com.gsrs.GSRS.dto.response;

import lombok.*;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorResponseDTO {
    private int status;
    private String message;
    private Instant timestamp;
}
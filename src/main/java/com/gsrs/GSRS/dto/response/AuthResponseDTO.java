package com.gsrs.GSRS.dto.response;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private String token;
}
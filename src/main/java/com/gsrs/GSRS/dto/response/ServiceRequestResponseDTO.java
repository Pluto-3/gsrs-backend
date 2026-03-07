package com.gsrs.GSRS.dto.response;

import com.gsrs.GSRS.enums.RequestStatus;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ServiceRequestResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String imageUrl;
    private UUID citizenId;
    private String citizenName;
    private UUID departmentId;
    private String departmentName;
    private RequestStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}

package com.gsrs.GSRS.dto.request;

import com.gsrs.GSRS.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateRequestStatusDTO {

    @NotNull(message = "Status is required")
    private RequestStatus status;

    private UUID departmentId;
}
package com.gsrs.GSRS.controller;

import com.gsrs.GSRS.dto.request.CreateServiceRequestDTO;
import com.gsrs.GSRS.dto.request.UpdateRequestStatusDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import com.gsrs.GSRS.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping
    public ResponseEntity<ServiceRequestResponseDTO> createRequest(
            Authentication authentication,
            @Valid @RequestBody CreateServiceRequestDTO dto) {
        UUID citizenId = (UUID) authentication.getCredentials();
        return ResponseEntity.ok(serviceRequestService.createRequest(citizenId, dto));
    }

    @GetMapping
    public ResponseEntity<Page<ServiceRequestResponseDTO>> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(serviceRequestService.getAllRequests(pageable));
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<ServiceRequestResponseDTO>> getCitizenRequests(
            @PathVariable UUID citizenId) {
        return ResponseEntity.ok(serviceRequestService.getCitizenRequests(citizenId));
    }

    @PatchMapping("/{requestId}/status")
    public ResponseEntity<ServiceRequestResponseDTO> updateStatus(
            @PathVariable UUID requestId,
            @Valid @RequestBody UpdateRequestStatusDTO dto) {
        return ResponseEntity.ok(serviceRequestService.updateRequestStatus(requestId, dto));
    }
}
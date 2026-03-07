package com.gsrs.GSRS.controller;

import com.gsrs.GSRS.dto.request.AssignDepartmentDTO;
import com.gsrs.GSRS.dto.response.RequestStatsDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import com.gsrs.GSRS.enums.RequestStatus;
import com.gsrs.GSRS.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/requests")
    public ResponseEntity<Page<ServiceRequestResponseDTO>> getAllRequests(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(adminService.getAllRequestsFiltered(status, departmentId, category, pageable));
    }

    @GetMapping("/requests/stats")
    public ResponseEntity<RequestStatsDTO> getStats() {
        return ResponseEntity.ok(adminService.getRequestStats());
    }

    @PutMapping("/requests/{requestId}/assign")
    public ResponseEntity<ServiceRequestResponseDTO> assignDepartment(
            @PathVariable UUID requestId,
            @RequestBody AssignDepartmentDTO dto) {
        return ResponseEntity.ok(adminService.assignDepartment(requestId, dto));
    }

    @GetMapping("/departments/{departmentId}/requests")
    public ResponseEntity<List<ServiceRequestResponseDTO>> getByDepartment(
            @PathVariable UUID departmentId) {
        return ResponseEntity.ok(adminService.getRequestsByDepartment(departmentId));
    }
}
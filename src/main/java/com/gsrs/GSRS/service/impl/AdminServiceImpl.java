package com.gsrs.GSRS.service.impl;

import com.gsrs.GSRS.dto.request.AssignDepartmentDTO;
import com.gsrs.GSRS.dto.response.RequestStatsDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import com.gsrs.GSRS.entity.Department;
import com.gsrs.GSRS.entity.ServiceRequest;
import com.gsrs.GSRS.enums.RequestStatus;
import com.gsrs.GSRS.exception.ResourceNotFoundException;
import com.gsrs.GSRS.repository.DepartmentRepository;
import com.gsrs.GSRS.repository.ServiceRequestRepository;
import com.gsrs.GSRS.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Page<ServiceRequestResponseDTO> getAllRequestsFiltered(
            RequestStatus status, UUID departmentId, String category, Pageable pageable) {

        Page<ServiceRequest> requests = serviceRequestRepository.findAll(pageable);

        List<ServiceRequestResponseDTO> filtered = requests.getContent()
                .stream()
                .filter(r -> status == null || r.getStatus() == status)
                .filter(r -> departmentId == null || r.getDepartment().getId().equals(departmentId))
                .filter(r -> category == null || r.getCategory().equalsIgnoreCase(category))
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new org.springframework.data.domain.PageImpl<>(filtered, pageable, filtered.size());
    }

    @Override
    public RequestStatsDTO getRequestStats() {
        List<ServiceRequest> all = serviceRequestRepository.findAll();

        return RequestStatsDTO.builder()
                .total(all.size())
                .submitted(all.stream().filter(r -> r.getStatus() == RequestStatus.SUBMITTED).count())
                .inProgress(all.stream().filter(r -> r.getStatus() == RequestStatus.IN_PROGRESS).count())
                .resolved(all.stream().filter(r -> r.getStatus() == RequestStatus.RESOLVED).count())
                .rejected(all.stream().filter(r -> r.getStatus() == RequestStatus.REJECTED).count())
                .build();
    }

    @Override
    public ServiceRequestResponseDTO assignDepartment(UUID requestId, AssignDepartmentDTO dto) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        request.setDepartment(department);
        ServiceRequest updated = serviceRequestRepository.save(request);
        return mapToDTO(updated);
    }

    @Override
    public List<ServiceRequestResponseDTO> getRequestsByDepartment(UUID departmentId) {
        return serviceRequestRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ServiceRequestResponseDTO mapToDTO(ServiceRequest request) {
        return ServiceRequestResponseDTO.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .imageUrl(request.getImageUrl())
                .citizenId(request.getCitizen().getId())
                .citizenName(request.getCitizen().getName())
                .departmentId(request.getDepartment().getId())
                .departmentName(request.getDepartment().getName())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
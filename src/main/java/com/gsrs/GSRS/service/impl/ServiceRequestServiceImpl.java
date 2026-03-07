package com.gsrs.GSRS.service.impl;

import com.gsrs.GSRS.exception.ResourceNotFoundException;
import com.gsrs.GSRS.dto.request.CreateServiceRequestDTO;
import com.gsrs.GSRS.dto.request.UpdateRequestStatusDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import com.gsrs.GSRS.entity.Department;
import com.gsrs.GSRS.entity.ServiceRequest;
import com.gsrs.GSRS.entity.User;
import com.gsrs.GSRS.enums.RequestStatus;
import com.gsrs.GSRS.exception.ResourceNotFoundException;
import com.gsrs.GSRS.repository.DepartmentRepository;
import com.gsrs.GSRS.repository.ServiceRequestRepository;
import com.gsrs.GSRS.repository.UserRepository;
import com.gsrs.GSRS.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ServiceRequestResponseDTO createRequest(UUID citizenId, CreateServiceRequestDTO dto) {
        User citizen = userRepository.findById(citizenId)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        ServiceRequest request = ServiceRequest.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .location(dto.getLocation())
                .imageUrl(dto.getImageUrl())
                .citizen(citizen)
                .department(department)
                .status(RequestStatus.SUBMITTED)
                .build();

        ServiceRequest saved = serviceRequestRepository.save(request);
        return mapToDTO(saved);
    }

    @Override
    public List<ServiceRequestResponseDTO> getCitizenRequests(UUID citizenId) {
        return serviceRequestRepository.findByCitizenId(citizenId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ServiceRequestResponseDTO> getAllRequests(Pageable pageable) {
        return serviceRequestRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public ServiceRequestResponseDTO updateRequestStatus(UUID requestId, UpdateRequestStatusDTO dto) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        request.setStatus(dto.getStatus());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            request.setDepartment(department);
        }

        ServiceRequest updated = serviceRequestRepository.save(request);
        return mapToDTO(updated);
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

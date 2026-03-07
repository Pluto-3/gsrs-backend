package com.gsrs.GSRS.service;

import com.gsrs.GSRS.dto.request.AssignDepartmentDTO;
import com.gsrs.GSRS.dto.response.RequestStatsDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import com.gsrs.GSRS.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminService {
    Page<ServiceRequestResponseDTO> getAllRequestsFiltered(RequestStatus status, UUID departmentId, String category, Pageable pageable);
    RequestStatsDTO getRequestStats();
    ServiceRequestResponseDTO assignDepartment(UUID requestId, AssignDepartmentDTO dto);
    List<ServiceRequestResponseDTO> getRequestsByDepartment(UUID departmentId);
}
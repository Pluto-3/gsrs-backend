package com.gsrs.GSRS.service;

import com.gsrs.GSRS.dto.request.CreateServiceRequestDTO;
import com.gsrs.GSRS.dto.request.UpdateRequestStatusDTO;
import com.gsrs.GSRS.dto.response.ServiceRequestResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface ServiceRequestService {
    ServiceRequestResponseDTO createRequest(UUID citizenId, CreateServiceRequestDTO dto);
    Page<ServiceRequestResponseDTO> getAllRequests(UUID callerId, String callerRole, Pageable pageable);
    List<ServiceRequestResponseDTO> getCitizenRequests(UUID citizenId);
    ServiceRequestResponseDTO updateRequestStatus(UUID requestId, UpdateRequestStatusDTO dto);
}
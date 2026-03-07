package com.gsrs.GSRS.repository;

import com.gsrs.GSRS.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {
    List<ServiceRequest> findByCitizenId(UUID citizenId);
    List<ServiceRequest> findByDepartmentId(UUID departmentId);
}

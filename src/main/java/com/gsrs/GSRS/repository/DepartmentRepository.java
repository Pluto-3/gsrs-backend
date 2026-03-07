package com.gsrs.GSRS.repository;

import com.gsrs.GSRS.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}
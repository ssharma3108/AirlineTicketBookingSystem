package com.admin.Admin_Service.repository;

import com.admin.Admin_Service.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
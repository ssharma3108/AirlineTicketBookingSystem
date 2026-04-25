package com.admin.Admin_Service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;        // e.g. "DELETE_USER"
    private String performedBy;   // Admin email or ID
    private String target;        // e.g. User ID

    private LocalDateTime timestamp;
}
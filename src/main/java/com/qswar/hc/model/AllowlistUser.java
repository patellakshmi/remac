package com.qswar.hc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "allowlist_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllowlistUser {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "email", unique = true, nullable = true, length = 128)
    private String email;

    @Column(name = "phone", unique = true, nullable = true, length = 20)
    private String phone;

    @Column(name = "endedi")
    private String endedi;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Use @CreationTimestamp for DATETIME DEFAULT CURRENT_TIMESTAMP

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Use @UpdateTimestamp for DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

}

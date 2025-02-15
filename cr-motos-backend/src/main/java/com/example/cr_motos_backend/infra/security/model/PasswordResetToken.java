package com.example.cr_motos_backend.infra.security.model;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_reset_token")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordResetToken {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idResetPassword;

    @ManyToOne
    @JoinColumn(name = "auth_id", referencedColumnName = "idAuth")
    private Auth auth;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

}

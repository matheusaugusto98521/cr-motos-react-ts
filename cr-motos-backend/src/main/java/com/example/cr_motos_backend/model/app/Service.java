package com.example.cr_motos_backend.model.app;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cr_motos_backend.utils.request.RequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_services")
@NoArgsConstructor
@Data
public class Service {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idService;

    @Column(nullable = false, length = 150)
    private String nameService;

    @Column(length = 250)
    private String descriptionService;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "idBrand")
    private Brand brand;

    public Service(RequestDTO<Service> request) {
        this.nameService = request.data().getNameService();
        this.descriptionService = request.data().getDescriptionService();
    }
}

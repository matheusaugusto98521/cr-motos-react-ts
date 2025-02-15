package com.example.cr_motos_backend.model.app;

import java.io.Serial;
import java.math.BigDecimal;
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
@Table(name = "tb_parts")
@NoArgsConstructor
@Data
public class Part {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPart;

    @Column(nullable = false, length = 150)
    private String namePart;

    @Column(nullable = false, length = 200)
    private String descriptionPart;

    private Boolean isAvailable;

    @Column(nullable = false)
    private BigDecimal pricePart;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "idBrand")
    private Brand brand;

    public Part(RequestDTO<Part> request) {
        this.namePart = request.data().getNamePart();
        this.descriptionPart = request.data().getDescriptionPart();
        this.pricePart = request.data().getPricePart();
        this.isAvailable = request.data().getIsAvailable();
    }
}

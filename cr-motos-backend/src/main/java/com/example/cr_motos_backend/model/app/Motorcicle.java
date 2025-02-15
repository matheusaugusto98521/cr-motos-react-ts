package com.example.cr_motos_backend.model.app;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cr_motos_backend.model.access.User;
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
@Table(name = "tb_motorcicles")
@NoArgsConstructor
@Data
public class Motorcicle {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idMotorcicle;

    @Column(length = 150, nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String plate;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String kmMotorcicle;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "idBrand")
    private Brand brand;

    public Motorcicle(RequestDTO<Motorcicle> request) {
        this.model = request.data().getModel();
        this.year = request.data().getYear();
        this.plate = request.data().getPlate();
        this.color = request.data().getColor();
        this.kmMotorcicle = request.data().getKmMotorcicle();
    }
}

package com.example.cr_motos_backend.model.app;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cr_motos_backend.model.access.Administrator;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_brands")
@NoArgsConstructor
@Data
public class Brand {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idBrand;

    @Column(nullable = false)
    private String nameBrand;

    @ManyToOne
    @JoinColumn(name = "adm_id", referencedColumnName = "idAdmin")
    @ToString.Exclude
    private Administrator administrator;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Part> parts = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Motorcicle> motorcicles = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Service> services = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Brand(RequestDTO<Brand> request) {
        this.nameBrand = request.data().getNameBrand();
    }

    public void addMotorcicle(Motorcicle motorcicle) {
        motorcicle.setBrand(this);
        this.motorcicles.add(motorcicle);
    }

    public void addPart(Part part) {
        part.setBrand(this);
        this.parts.add(part);
    }

    public void addService(Service service) {
        service.setBrand(this);
        this.services.add(service);
    }
}

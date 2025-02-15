package com.example.cr_motos_backend.model.access;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.model.app.Brand;
import com.example.cr_motos_backend.utils.request.RequestAuthDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_administrators")
@NoArgsConstructor
@Data
public class Administrator {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idAdmin;

    @Column(length = 100, nullable = false)
    private String nameAdm;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthDateAdm;

    @Column(length = 20, nullable = false, unique = true)
    private String documentAdm;

    @Column(length = 50, nullable = false, unique = true)
    private String emailAdm;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Brand> brands = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", referencedColumnName = "idAuth")
    private Auth auth;

    public Administrator(RequestAuthDTO<Administrator> request) {
        this.nameAdm = request.data().getNameAdm();
        this.emailAdm = request.data().getEmailAdm();
        this.birthDateAdm = request.data().getBirthDateAdm();
        this.documentAdm = request.data().getDocumentAdm();
    }

    public void addUser(User user) {
        user.setAdministrator(this);
        this.users.add(user);
    }

    public void addBrand(Brand brand) {
        brand.setAdministrator(this);
        this.brands.add(brand);
    }

}

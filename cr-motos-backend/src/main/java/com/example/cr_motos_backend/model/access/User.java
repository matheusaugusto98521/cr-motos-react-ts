package com.example.cr_motos_backend.model.access;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.model.app.Motorcicle;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@Data
public class User {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Column(length = 150, nullable = false)
    private String nameUser;

    @Column(length = 20, nullable = false, unique = true)
    private String documentUser;

    @Column(length = 20, nullable = false, unique = true)
    private String phoneUser;

    private int ageUser;

    @Temporal(TemporalType.DATE)
    private Date birthDateUser;

    @Column(length = 30, nullable = false, unique = true)
    private String emailUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_adm", referencedColumnName = "idAdmin", nullable = false)
    private Administrator administrator;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Motorcicle> motorcicles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", referencedColumnName = "idAuth")
    private Auth auth;

    public User(RequestAuthDTO<User> request) {
        this.nameUser = request.data().getNameUser();
        this.phoneUser = request.data().getPhoneUser();
        this.birthDateUser = request.data().getBirthDateUser();
        this.emailUser = request.data().getEmailUser();
        this.documentUser = request.data().getDocumentUser();
    }

    public void addMotorcicle(Motorcicle motorcicle) {
        motorcicle.setUser(this);
        this.motorcicles.add(motorcicle);
    }
}

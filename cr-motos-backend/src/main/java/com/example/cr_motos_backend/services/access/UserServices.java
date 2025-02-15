package com.example.cr_motos_backend.services.access;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.infra.security.model.services.AuthServices;
import com.example.cr_motos_backend.model.access.Administrator;
import com.example.cr_motos_backend.model.access.User;
import com.example.cr_motos_backend.repositories.access.UserRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.exceptions.RegisterDeniedException;
import com.example.cr_motos_backend.utils.request.RequestAuthDTO;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.security.role.Roles;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServices {

    private UserRepository repository;
    private AdministratorServices admServices;
    private AuthServices authServices;

    public User createUser(UUID idAdm, RequestAuthDTO<User> request)
            throws InformationNotFoundException, RegisterDeniedException {

        Administrator admFounded = this.admServices.getById(idAdm);
        User user = new User(request);
        Auth auth = this.authServices.createAuth(request.data().getEmailUser(),
                this.authServices.encodePassword(request.password()), Roles.USER);

        if (calculateAge(request.data().getBirthDateUser()) < 18) {
            throw new RegisterDeniedException("Para se cadastrar vocÊ deve ser maior de idade");
        }
        user.setAuth(auth);
        user.setAgeUser(calculateAge(request.data().getBirthDateUser()));
        admFounded.addUser(user);
        auth.setUser(user);
        return this.repository.save(user);
    }

    public User updateUser(UUID idUser, RequestDTO<User> request) throws InformationNotFoundException {
        User userFounded = getById(idUser);

        userFounded.setNameUser(request.data().getNameUser());
        userFounded.setPhoneUser(request.data().getPhoneUser());
        userFounded.setEmailUser(request.data().getEmailUser());

        return this.repository.save(userFounded);
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    public void deleteUser(UUID idUser) throws InformationNotFoundException {
        User userFounded = getById(idUser);
        this.repository.delete(userFounded);
    }

    public User getById(UUID idUser) throws InformationNotFoundException {
        return this.repository.findById(idUser)
                .orElseThrow(() -> new InformationNotFoundException("Usuario não encontrado para o ID: " + idUser));
    }

    private int calculateAge(Date birthDate) {
        LocalDate birthDateLC = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate today = LocalDate.now();

        return Period.between(birthDateLC, today).getYears();
    }
}

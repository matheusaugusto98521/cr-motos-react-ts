package com.example.cr_motos_backend.services.access;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.infra.security.model.services.AuthServices;
import com.example.cr_motos_backend.model.access.Administrator;
import com.example.cr_motos_backend.repositories.access.AdministratorRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestAuthDTO;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.security.role.Roles;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdministratorServices {

    private AdministratorRepository repository;
    private AuthServices authServices;

    public Administrator createAdmin(RequestAuthDTO<Administrator> request) {
        Auth auth = this.authServices.createAuth(request
                .data().getEmailAdm(), this.authServices.encodePassword(request.password()), Roles.ADMIN);

        Administrator administrator = new Administrator(request);
        administrator.setAuth(auth);
        auth.setAdministrator(administrator);

        return this.repository.save(administrator);
    }

    public Administrator updateAdm(UUID idAdmin, RequestDTO<Administrator> request)
            throws InformationNotFoundException {
        Administrator administratorFounded = getById(idAdmin);

        var dataRequest = request.data();

        administratorFounded.setNameAdm(dataRequest.getNameAdm());
        administratorFounded.setEmailAdm(dataRequest.getEmailAdm());

        return this.repository.save(administratorFounded);
    }

    public void deleteAdmin(UUID idAdmin) throws InformationNotFoundException {
        Administrator administratorFounded = getById(idAdmin);
        this.repository.delete(administratorFounded);
    }

    public List<Administrator> getAllAdms() {
        return this.repository.findAll();
    }

    public Administrator getById(UUID idAdmin) throws InformationNotFoundException {
        return this.repository.findById(idAdmin).orElseThrow(
                () -> new InformationNotFoundException("Administrador não encontrado para o ID: " + idAdmin));
    }
}

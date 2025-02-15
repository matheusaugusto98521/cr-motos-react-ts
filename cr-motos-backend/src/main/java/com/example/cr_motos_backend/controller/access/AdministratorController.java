package com.example.cr_motos_backend.controller.access;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cr_motos_backend.model.access.Administrator;
import com.example.cr_motos_backend.services.access.AdministratorServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.RequestAuthDTO;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdministratorController {

    @Autowired
    private AdministratorServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Administrator>> createAdmin(@RequestBody RequestAuthDTO<Administrator> request) {
        Administrator adm = this.services.createAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO<Administrator>("Criado com sucesso", adm));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Administrator>> updateAdm(@RequestParam("idAdm") UUID idAdmin,
            @RequestBody RequestDTO<Administrator> updateRequest) throws InformationNotFoundException {
        Administrator admUpdated = this.services.updateAdm(idAdmin, updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<Administrator>("Alterado com sucesso", admUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAdministrator(@RequestParam("idAdm") UUID idAdm)
            throws InformationNotFoundException {
        this.services.deleteAdmin(idAdm);
        return ResponseEntity.status(HttpStatus.OK).body("Excluido com sucesso");
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<Administrator>>> getAllAdm() {
        List<Administrator> admList = this.services.getAllAdms();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<List<Administrator>>("Administradores cadastrados", admList));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<Administrator>> getById(@RequestParam("idAdm") UUID idAdm)
            throws InformationNotFoundException {
        Administrator admFounded = this.services.getById(idAdm);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<Administrator>("Encontrado", admFounded));
    }

}

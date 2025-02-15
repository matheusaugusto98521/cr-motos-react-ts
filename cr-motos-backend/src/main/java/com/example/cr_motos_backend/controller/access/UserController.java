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

import com.example.cr_motos_backend.model.access.User;
import com.example.cr_motos_backend.services.access.UserServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.exceptions.RegisterDeniedException;
import com.example.cr_motos_backend.utils.request.RequestAuthDTO;
import com.example.cr_motos_backend.utils.request.RequestDTO;
import com.example.cr_motos_backend.utils.response.ResponseDTO;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserServices services;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<User>> createUser(@RequestParam("idAdm") UUID idAdm,
            @RequestBody RequestAuthDTO<User> request) throws InformationNotFoundException, RegisterDeniedException {
        User user = this.services.createUser(idAdm, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<User>("Criado com sucesso", user));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<User>> updateUser(@RequestParam("idUser") UUID idUser,
            @RequestBody RequestDTO<User> request) throws InformationNotFoundException {
        User userUpdated = this.services.updateUser(idUser, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<User>("Alterado com sucesso", userUpdated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("idUser") UUID idUser) throws InformationNotFoundException {
        this.services.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body("Excluido com sucesso");
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDTO<User>> getById(@RequestParam("idUser") UUID idUser)
            throws InformationNotFoundException {
        User userFounded = this.services.getById(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<User>("Usuário encontrado", userFounded));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<User>>> getAll() {
        List<User> usersList = this.services.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<List<User>>("Usuários cadastrados", usersList));
    }

}

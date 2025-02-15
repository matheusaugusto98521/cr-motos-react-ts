package com.example.cr_motos_backend.infra.security.model.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cr_motos_backend.infra.security.TokenService;
import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.infra.security.model.services.AuthServices;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.LoginRequestDTO;
import com.example.cr_motos_backend.utils.request.ResetPasswordDTO;
import com.example.cr_motos_backend.utils.response.LoginResponseDTO;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServices services;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.username(),
                data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = this.tokenService.generateToken((Auth) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/init-reset-password")
    public ResponseEntity<String> initResetPassword(@RequestParam("idAuth") UUID idAuth)
            throws InformationNotFoundException {
        this.services.initAlteratePassword(idAuth);
        return ResponseEntity.status(HttpStatus.OK).body("Código enviado com sucesso");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("idAuth") UUID idAuth,
            @RequestBody ResetPasswordDTO request) throws InformationNotFoundException {
        this.services.resetPasword(idAuth, request);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
    }

}

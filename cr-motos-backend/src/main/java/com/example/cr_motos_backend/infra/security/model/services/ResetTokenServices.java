package com.example.cr_motos_backend.infra.security.model.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.infra.security.model.PasswordResetToken;
import com.example.cr_motos_backend.infra.security.model.repository.PasswordResetTokenRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResetTokenServices {

    private PasswordResetTokenRepository repository;

    public PasswordResetToken createToken(Auth auth, String resetToken, LocalDateTime expirationToken) {
        PasswordResetToken token = new PasswordResetToken();
        token.setAuth(auth);
        token.setToken(resetToken);
        token.setExpirationToken(expirationToken);
        return this.repository.save(token);
    }

    public PasswordResetToken getByToken(String token) throws InformationNotFoundException {
        return this.repository.findByToken(token)
                .orElseThrow(() -> new InformationNotFoundException("Token não encontrado"));
    }

}

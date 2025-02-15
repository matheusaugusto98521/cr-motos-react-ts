package com.example.cr_motos_backend.infra.security.model.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cr_motos_backend.infra.mail.MailService;
import com.example.cr_motos_backend.infra.security.model.Auth;
import com.example.cr_motos_backend.infra.security.model.PasswordResetToken;
import com.example.cr_motos_backend.infra.security.model.repository.AuthRepository;
import com.example.cr_motos_backend.utils.exceptions.InformationNotFoundException;
import com.example.cr_motos_backend.utils.request.ResetPasswordDTO;
import com.example.cr_motos_backend.utils.security.role.Roles;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServices implements UserDetailsService {

    private AuthRepository repository;
    private MailService mailService;
    private ResetTokenServices resetTokenServices;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username);
    }

    public Auth createAuth(String username, String password, Roles role) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(password);
        auth.setRole(role);

        return auth;
    }

    public Auth getById(UUID idAuth) throws InformationNotFoundException {
        return this.repository.findById(idAuth)
                .orElseThrow(() -> new InformationNotFoundException("Perfil não encontrado para o ID: " + idAuth));
    }

    public void initAlteratePassword(UUID idAuth) throws InformationNotFoundException {
        Auth auth = getById(idAuth);
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationToken = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = this.resetTokenServices.createToken(auth, token, expirationToken);

        sendCodeMail(auth.getUsername(), resetToken.getToken());
    }

    public void resetPasword(UUID idAuth, ResetPasswordDTO request) throws InformationNotFoundException {
        PasswordResetToken tokenFounded = this.resetTokenServices.getByToken(request.token());
        Auth auth = getById(idAuth);

        if (tokenFounded.getExpirationToken().isBefore(LocalDateTime.now())) {
            throw new InformationNotFoundException("Token expirado");
        }

        auth.setPassword(encodePassword(request.password()));
        this.repository.save(auth);
    }

    private void sendCodeMail(String email, String token) {
        String emailContent = "<html>"
                + "<head>"
                + "<style>"
                + "  body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }"
                + "  .email-container { width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"
                + "  .header { text-align: center; color: #333333; padding-bottom: 20px; }"
                + "  .content { font-size: 16px; color: #555555; line-height: 1.5; }"
                + "  .footer { font-size: 12px; color: #888888; text-align: center; padding-top: 20px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='email-container'>"
                + "  <div class='header'>"
                + "    <h1 style='font-size: 24px;'>Redefinição de Senha</h1>"
                + "  </div>"
                + "  <div class='content'>"
                + "    <p>Olá, você solicitou a alteração da sua senha. Para continuar, use o código abaixo:</p>"
                + "    <h2 style='color: #4CAF50;'>Código: " + token + "</h2>"
                + "    <p>Se você não solicitou essa alteração, por favor, ignore este e-mail.</p>"
                + "  </div>"
                + "  <div class='footer'>"
                + "    <p>Obrigado por usar nosso serviço.</p>"
                + "    <p>Se precisar de ajuda, acesse nossa <a href='http://seu-site.com/ajuda'>página de ajuda</a>.</p>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";

        this.mailService.send(email, "Alteração de Senha", emailContent);
    }

    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}

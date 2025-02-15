package com.example.cr_motos_backend.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cr_motos_backend.infra.mail.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService service;

    @PostMapping("/send")
    public String sendTestMail(@RequestParam("to") String to) {
        this.service.send(to, "Email de teste", "<h1>Funciona!</h1><p>Seu e-mail foi enviado com sucesso.</p>");
        return "Email enviado";
    }

}

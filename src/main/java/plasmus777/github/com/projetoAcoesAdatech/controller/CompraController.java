package com.plasmus777.controller;

import com.plasmus777.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/realizar")
    public String realizarCompra() {
        emailService.enviarEmail("cliente@exemplo.com", "Compra Realizada", "Sua compra foi realizada com sucesso.");

        return "Compra realizada com sucesso!";
    }
}

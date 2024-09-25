package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plasmus777.github.com.projetoAcoesAdatech.service.EmailService;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    private EmailService emailService;

    public CompraController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarCompra() {
        emailService.enviarEmail("cliente@exemplo.com", "Compra Realizada", "Sua compra foi realizada com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body("Compra realizada com sucesso!");
    }
}

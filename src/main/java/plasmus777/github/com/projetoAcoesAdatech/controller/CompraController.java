package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plasmus777.github.com.projetoAcoesAdatech.service.EmailService;

@Tag(name = "CompraController", description = "Controller para gerenciar compras de ativos na aplicação")
@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    private EmailService emailService;

    public CompraController(EmailService emailService){
        this.emailService = emailService;
    }

    @Operation(
            summary = "Realiza a compra de um ativo",
            description = "Realiza a compra de um ativo no sistema e retorna uma resposta.",
            tags = { "compra", "post" })
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarCompra() {
        emailService.enviarEmail("cliente@exemplo.com", "Compra Realizada", "Sua compra foi realizada com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body("Compra realizada com sucesso!");
    }
}

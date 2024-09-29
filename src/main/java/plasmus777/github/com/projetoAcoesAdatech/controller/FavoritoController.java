package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plasmus777.github.com.projetoAcoesAdatech.service.EmailService;

@Tag(name = "FavoritoController", description = "Controller para gerenciar favoritos de ativos na aplicação")
@RestController
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {

    private EmailService emailService;

    public FavoritoController(EmailService emailService){
        this.emailService = emailService;
    }

    @Operation(
            summary = "Adiciona um ativo aos favoritos",
            description = "Adiciona um ativo à lista de favoritos no sistema e retorna uma resposta.",
            tags = { "favoritar", "post" })
    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarFavorito() {
        emailService.enviarEmail("cliente@exemplo.com", "Favorito Adicionado", "O ativo foi adicionado aos seus favoritos.");

        return ResponseEntity.status(HttpStatus.OK).body("Ativo adicionado aos favoritos com sucesso!");
    }
}

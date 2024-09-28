package plasmus777.github.com.projetoAcoesAdatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Tag(name = "UsuarioController", description = "Controller para gerenciar usuários na aplicação")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Retorna todos os usuários cadastrados",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    @GetMapping
    public List<UsuarioDTO> obterUsuarios() {
        return usuarioService.obterLista();
    }

    @Operation(
            summary = "Retorna um usuário específico cadastrado",
            description = "Busca por um usuário através de um identificador especificado e o retorna caso encontrado no sistema.")
    @GetMapping("/{id}")
    public UsuarioDTO obterUsuario(@PathVariable Long id){
        Optional<UsuarioDTO> opt = usuarioService.obter(id);

        return opt.orElse(null);
    }

    @Operation(
            summary = "Atualiza um usuário específico cadastrado",
            description = "Atualiza um usuário através de um identificador especificado e retorna uma resposta.")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO novoUsuario){
        return usuarioService.atualizar(id, novoUsuario);
    }

    @Operation(
            summary = "Cadastra um usuário no sistema",
            description = "Cadastra um usuário no sistema e retorna uma resposta.")
    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuario){
        return usuarioService.cadastrar(usuario);
    }

    @Operation(
            summary = "Apaga um usuário específica cadastrado",
            description = "Apaga um usuário do sistema através de um identificador especificado e retorna uma resposta.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarUsuario(@PathVariable Long id){
        return usuarioService.apagar(id);
    }
}

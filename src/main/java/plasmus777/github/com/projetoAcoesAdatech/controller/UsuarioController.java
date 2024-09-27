package plasmus777.github.com.projetoAcoesAdatech.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> obterUsuarios() {
        return usuarioService.obterLista();
    }

    @GetMapping("/{id}")
    public UsuarioDTO obterUsuario(@PathVariable Long id){
        Optional<UsuarioDTO> opt = usuarioService.obter(id);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO novoUsuario){
        return usuarioService.atualizar(id, novoUsuario);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuario){
        return usuarioService.cadastrar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarUsuario(@PathVariable Long id){
        return usuarioService.apagar(id);
    }
}

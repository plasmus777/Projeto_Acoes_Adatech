package plasmus777.github.com.projetoAcoesAdatech.controller;

import org.springframework.web.bind.annotation.*;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Usuario;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public List<Usuario> obterUsuarios() {
        return usuarioService.obterLista();
    }

    @GetMapping("/{id}")
    public Usuario obterUsuario(@PathVariable Long id){
        Optional<Usuario> opt = usuarioService.obter(id);

        return opt.orElse(null);
    }

    @PutMapping("/{id}")
    public void atualizarUsuario(@PathVariable Long id, @RequestBody Usuario novoUsuario){
        usuarioService.atualizar(id, novoUsuario);
    }

    @PostMapping
    public void cadastrarUsuario(@RequestBody Usuario usuario){
        usuarioService.cadastrar(usuario);
    }

    @DeleteMapping("/{id}")
    public void apagarUsuario(@PathVariable Long id){
        usuarioService.apagar(id);
    }
}

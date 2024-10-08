package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements RestService<UsuarioDTO>{

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioDTO> obterLista() {
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
    }

    @Override
    public Optional<UsuarioDTO> obter(Long id) {
        return usuarioRepository.findUsuarioById(id).map(UsuarioDTO::fromEntity);
    }

    public Optional<UsuarioDTO> obterPorEmail(String email){
        return usuarioRepository.findUsuarioByEmail(email).map(UsuarioDTO::fromEntity);
    }

    @Override
    public ResponseEntity<String> atualizar(Long id, UsuarioDTO usuario) {
        Optional<Usuario> opt = usuarioRepository.findUsuarioById(id);
        if(opt.isPresent()){
            Usuario u = opt.get();

            if(usuario != null){
                u.setNome(usuario.getNome());
                u.setEmail(usuario.getEmail());
                u.setSenha(usuario.getSenha());

                try {
                    usuarioRepository.save(u);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Usuário atualizado com sucesso.");
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o usuário a ser atualizado.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O usuário não pôde ser atualizado.");
    }

    @Override
    public ResponseEntity<String> cadastrar(UsuarioDTO usuario) {
        try{
            Usuario u = new Usuario();
            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());
            u.setSenha(usuario.getSenha());
            u.setAcoesFavoritas(usuario.getAcoesFavoritas());
            u.setFundosImobiliariosFavoritos(usuario.getFundosImobiliariosFavoritos());
            u.setRendasFixasFavoritas(usuario.getRendasFixasFavoritas());

            usuarioRepository.save(u);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o usuário a ser cadastrado.");
        }
    }

    @Override
    public ResponseEntity<String> apagar(Long id) {
        Optional<Usuario> opt = usuarioRepository.findUsuarioById(id);
        if(opt.isPresent()){
            Usuario u = opt.get();
            try{
                usuarioRepository.delete(u);
                return ResponseEntity.status(HttpStatus.OK).body("Usuário apagado com sucesso.");
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar o usuário.");
            }
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("O usuário não pôde ser apagado.");
    }
}

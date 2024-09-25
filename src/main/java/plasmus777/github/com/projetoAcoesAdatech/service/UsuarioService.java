package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements RestService<Usuario>{

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> obterLista() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obter(Long id) {
        return usuarioRepository.findUsuarioById(id);
    }

    @Override
    public boolean atualizar(Long id, Usuario usuario) {
        Optional<Usuario> opt = usuarioRepository.findUsuarioById(id);
        if(opt.isPresent()){
            Usuario u = opt.get();

            if(usuario != null){
                u.setNome(usuario.getNome());
                u.setEmail(usuario.getEmail());
                u.setSenha(usuario.getSenha());
                u.setAcoesFavoritas(usuario.getAcoesFavoritas());
                u.setFundosImobiliariosFavoritos(usuario.getFundosImobiliariosFavoritos());
                u.setRendasFixasFavoritas(usuario.getRendasFixasFavoritas());

                try {
                    usuarioRepository.save(u);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean cadastrar(Usuario usuario) {
        try{
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagar(Long id) {
        Optional<Usuario> opt = usuarioRepository.findUsuarioById(id);
        if(opt.isPresent()){
            Usuario u = opt.get();
            try{
                usuarioRepository.delete(u);
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}

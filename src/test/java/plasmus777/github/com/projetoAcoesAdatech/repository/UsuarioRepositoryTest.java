package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;

    Usuario usuarioTestes;

    @BeforeEach
    void beforeEach(){
        usuarioTestes = new Usuario();
        usuarioTestes.setEmail("compraevendas.acoes@gmail.com");
        usuarioTestes.setNome("Compra e vendas de ações");
        usuarioTestes.setSenha("Senha123!");
        usuarioTestes.setAcoesFavoritas(new ArrayList<>());
        usuarioTestes.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuarioTestes.setRendasFixasFavoritas(new ArrayList<>());

        usuarioRepository.save(usuarioTestes);
    }

    @AfterEach
    void afterEach(){
        usuarioRepository.delete(usuarioTestes);
    }

    @Test
    void deveSalvarUsuarioComSucesso(){
        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes1@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("usuarioTestes1@mail.com", usuarioSalvo.getEmail());
        Assertions.assertEquals("Compra e vendas de ações", usuarioSalvo.getNome());
        Assertions.assertEquals("Senha123!", usuarioSalvo.getSenha());
        Assertions.assertNotNull(usuario.getAcoesFavoritas());
        Assertions.assertNotNull(usuario.getFundosImobiliariosFavoritos());
        Assertions.assertNotNull(usuario.getRendasFixasFavoritas());
    }

    @Test
    void deveListarUsuariosSalvosComSucesso(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        Assertions.assertNotNull(usuarios);
        Assertions.assertFalse(usuarios.isEmpty());
    }

    @Test
    void deveEncontrarUsuarioPorIdComSucesso(){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findUsuarioById(usuarioTestes.getId());

        Assertions.assertTrue(usuarioSalvo.isPresent());
        Assertions.assertEquals("compraevendas.acoes@gmail.com", usuarioSalvo.get().getEmail());
        Assertions.assertEquals("Compra e vendas de ações", usuarioSalvo.get().getNome());
        Assertions.assertEquals("Senha123!", usuarioSalvo.get().getSenha());
        Assertions.assertNotNull(usuarioSalvo.get().getAcoesFavoritas());
        Assertions.assertNotNull(usuarioSalvo.get().getFundosImobiliariosFavoritos());
        Assertions.assertNotNull(usuarioSalvo.get().getRendasFixasFavoritas());
    }

    @Test
    void deveEncontrarUsuarioPorEmailComSucesso(){
        Optional<Usuario> usuarioSalvo = usuarioRepository.findUsuarioByEmail(usuarioTestes.getEmail());

        Assertions.assertTrue(usuarioSalvo.isPresent());
        Assertions.assertEquals("compraevendas.acoes@gmail.com", usuarioSalvo.get().getEmail());
        Assertions.assertEquals("Compra e vendas de ações", usuarioSalvo.get().getNome());
        Assertions.assertEquals("Senha123!", usuarioSalvo.get().getSenha());
        Assertions.assertNotNull(usuarioSalvo.get().getAcoesFavoritas());
        Assertions.assertNotNull(usuarioSalvo.get().getFundosImobiliariosFavoritos());
        Assertions.assertNotNull(usuarioSalvo.get().getRendasFixasFavoritas());
    }

    @Test
    void deveAtualizarUsuarioComSucesso(){
        Long idAntes = usuarioTestes.getId();
        String nomeAntes = usuarioTestes.getNome();
        String senhaAntes = usuarioTestes.getSenha();

        String nomeDepois = "Compra e vendas de ações - atualizado";
        String senhaDepois = "Senha1234!";

        Usuario usuario = usuarioRepository.findUsuarioById(usuarioTestes.getId()).orElseThrow();
        usuario.setNome(nomeDepois);
        usuario.setSenha(senhaDepois);

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioAtualizado);
        Assertions.assertEquals(usuarioAtualizado.getId(), idAntes);
        Assertions.assertNotEquals(usuarioAtualizado.getNome(), nomeAntes);
        Assertions.assertNotEquals(usuarioAtualizado.getSenha(), senhaAntes);
        Assertions.assertEquals(usuarioAtualizado.getNome(), nomeDepois);
        Assertions.assertEquals(usuarioAtualizado.getSenha(), senhaDepois);
    }

    @Test
    void deveApagarUsuarioComSucesso(){
        usuarioRepository.delete(usuarioTestes);

        Optional<Usuario> usuario = usuarioRepository.findUsuarioByEmail("compraevendas.acoes@gmail.com");

        Assertions.assertTrue(usuario.isEmpty());
    }
}

package plasmus777.github.com.projetoAcoesAdatech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    Usuario usuario;

    @BeforeEach
    public void beforeEach(){
        usuario = new Usuario();
        usuario.setId(1l);
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
    }

    @Test
    public void deveObterListaDeUsuariosComSucesso(){
        List<Usuario> usuarios = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            Usuario usuario = new Usuario();
            usuario.setId((long) i);
            usuario.setEmail("usuarioTestes" + i + "@mail.com");
            usuario.setNome("Compra e vendas de ações - " + i);
            usuario.setSenha("Senha123!");
            usuario.setAcoesFavoritas(new ArrayList<>());
            usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
            usuario.setRendasFixasFavoritas(new ArrayList<>());
            usuarios.add(usuario);
        }

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        List<UsuarioDTO> usuariosDto = usuarioService.obterLista();

        Assertions.assertNotNull(usuariosDto);
        Assertions.assertFalse(usuariosDto.isEmpty());
        Assertions.assertEquals(3, usuariosDto.size());
    }

    @Test
    public void deveObterUsuarioComSucessoAtravesDoId(){
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(optionalUsuario);

        Optional<UsuarioDTO> optionalUsuarioDTO = usuarioService.obter(1l);

        Assertions.assertNotNull(optionalUsuarioDTO);
        Assertions.assertTrue(optionalUsuarioDTO.isPresent());
        Assertions.assertEquals("usuarioTestes@mail.com", optionalUsuarioDTO.get().getEmail());
        Assertions.assertEquals("Compra e vendas de ações", optionalUsuarioDTO.get().getNome());
        Assertions.assertEquals("Senha123!", optionalUsuarioDTO.get().getSenha());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getAcoesFavoritas());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getFundosImobiliariosFavoritos());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getRendasFixasFavoritas());
    }

    @Test
    public void deveObterUsuarioComSucessoAtravesDoEmail(){
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.anyString())).thenReturn(optionalUsuario);

        Optional<UsuarioDTO> optionalUsuarioDTO = usuarioService.obterPorEmail("usuarioTestes@mail.com");

        Assertions.assertNotNull(optionalUsuarioDTO);
        Assertions.assertTrue(optionalUsuarioDTO.isPresent());
        Assertions.assertEquals("usuarioTestes@mail.com", optionalUsuarioDTO.get().getEmail());
        Assertions.assertEquals("Compra e vendas de ações", optionalUsuarioDTO.get().getNome());
        Assertions.assertEquals("Senha123!", optionalUsuarioDTO.get().getSenha());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getAcoesFavoritas());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getFundosImobiliariosFavoritos());
        Assertions.assertNotNull(optionalUsuarioDTO.get().getRendasFixasFavoritas());
    }

    @Test
    public void deveAtualizarUsuarioComSucesso(){
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(optionalUsuario);

        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
        usuarioDTO.setNome("Compra e vendas de ações - atualizado");
        usuarioDTO.setSenha("Senha1234!");

        ResponseEntity<String> resposta = usuarioService.atualizar(usuario.getId(), usuarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Usuário atualizado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarAtualizarUsuarioComValoresInvalidos(){
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(optionalUsuario);

        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
        usuarioDTO.setEmail(null);
        usuarioDTO.setNome("Compra e vendas de ações - atualizado");
        usuarioDTO.setSenha("Senha1234!");

        Mockito.when(usuarioRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o usuário a ser atualizado."));

        ResponseStatusException resposta = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.atualizar(usuario.getId(), usuarioDTO));

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getReason(), "O repositório não pôde salvar o usuário a ser atualizado.");
    }

    @Test
    public void deveFalharAoTentarAtualizarUsuarioInexistente(){
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(Optional.empty());

        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
        usuarioDTO.setNome("Compra e vendas de ações - atualizado");
        usuarioDTO.setSenha("Senha1234!");

        ResponseEntity<String> resposta = usuarioService.atualizar(usuario.getId(), usuarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "O usuário não pôde ser atualizado.");
    }

    @Test
    public void deveCadastrarUsuarioComSucesso(){
        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);

        ResponseEntity<String> resposta = usuarioService.cadastrar(usuarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Usuário cadastrado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarCadastrarUsuarioInvalido(){
        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
        usuarioDTO.setEmail(null);

        Mockito.when(usuarioRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o usuário a ser cadastrado."));

        ResponseStatusException resposta = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.cadastrar(usuarioDTO));
        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getReason(), "O repositório não pôde salvar o usuário a ser cadastrado.");
    }

    @Test
    public void deveApagarUsuarioComSucesso(){
        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(usuarioOpt);

        ResponseEntity<String> resposta = usuarioService.apagar(usuario.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resposta.getBody(), "Usuário apagado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarApagarUsuarioInexistente(){
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = usuarioService.apagar(usuario.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "O usuário não pôde ser apagado.");
    }

    @Test
    public void deveFalharAoApagarUsuarioPorErrosDoRepositorio(){
        Optional<Usuario> usuarioOpt = Optional.of(usuario);
        Mockito.when(usuarioRepository.findUsuarioById(Mockito.anyLong())).thenReturn(usuarioOpt);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar o usuário.")).when(usuarioRepository).delete(usuario);

        ResponseStatusException resposta = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.apagar(usuario.getId()));

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getReason(), "O repositório não pôde apagar o usuário.");
    }
}

package plasmus777.github.com.projetoAcoesAdatech.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.AcaoRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AcaoServiceTest {

    @Mock
    private AcaoRepository acaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FinnhubClient finnhubClient;

    @InjectMocks
    private AcaoService acaoService;

    private Acao acao;
    private AcaoDTO acaoDTO;
    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        acao = new Acao();
        acao.setUsuario(usuario);
        acaoDTO = AcaoDTO.fromEntity(acao);
    }

    @Test
    public void deveCadastrarAcaoComSucesso() {
        AcaoApi api = new AcaoApi();
        api.setPrecoAtual(BigDecimal.valueOf(150.0));
        when(finnhubClient.buscarInformacoesAtivo(acaoDTO.getCodigoNegociacao())).thenReturn(api);
        when(usuarioRepository.findUsuarioByEmail(acaoDTO.getUsuarioEmail())).thenReturn(Optional.of(usuario));

        ResponseEntity<String> response = acaoService.cadastrar(acaoDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Ação cadastrada com sucesso.", response.getBody());
        verify(acaoRepository, times(1)).save(any(Acao.class));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void deveAtualizarAcaoComSucesso() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.of(acao));
        AcaoApi api = new AcaoApi();
        api.setPrecoAtual(BigDecimal.valueOf(200.0));
        when(finnhubClient.buscarInformacoesAtivo(acaoDTO.getCodigoNegociacao())).thenReturn(api);

        ResponseEntity<String> response = acaoService.atualizar(1L, acaoDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Ação atualizada com sucesso.", response.getBody());
        verify(acaoRepository, times(1)).save(acao);
    }

    @Test
    public void deveRetornarErroAoAtualizarAcaoInexistente() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = acaoService.atualizar(1L, acaoDTO);

        assertEquals(417, response.getStatusCode().value());
        assertEquals("A ação não pôde ser atualizada.", response.getBody());
    }

    @Test
    public void deveApagarAcaoComSucesso() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.of(acao));
        when(usuarioRepository.findUsuarioByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        usuario.setAcoesFavoritas(Collections.singletonList(acao));

        ResponseEntity<String> response = acaoService.apagar(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Ação apagada com sucesso.", response.getBody());
        verify(acaoRepository, times(1)).delete(acao);
    }

    @Test
    public void deveRetornarErroAoApagarAcaoInexistente() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = acaoService.apagar(1L);

        assertEquals(417, response.getStatusCode().value());
        assertEquals("A ação não pôde ser apagada.", response.getBody());
    }

    @Test
    public void deveObterListaDeAcoes() {
        when(acaoRepository.findAll()).thenReturn(Collections.singletonList(acao));

        var acoes = acaoService.obterLista();

        assertFalse(acoes.isEmpty());
        assertEquals(1, acoes.size());
    }

    @Test
    public void deveObterAcaoPorIdComSucesso() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.of(acao));

        Optional<AcaoDTO> resultado = acaoService.obter(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Acao Teste", resultado.get().getNome());
    }

    @Test
    public void deveRetornarNuloAoBuscarAcaoInexistente() {
        when(acaoRepository.findAcaoById(1L)).thenReturn(Optional.empty());

        Optional<AcaoDTO> resultado = acaoService.obter(1L);

        assertFalse(resultado.isPresent());
    }
}

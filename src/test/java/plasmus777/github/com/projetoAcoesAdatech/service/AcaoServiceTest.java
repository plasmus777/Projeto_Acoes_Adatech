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
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.AcaoRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AcaoServiceTest {
    @InjectMocks
    AcaoService acaoService;

    @Mock
    AcaoRepository acaoRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    FinnhubClient finnhubClient;

    Acao acao;

    @BeforeEach
    public void beforeEach(){
        acao = new Acao();
        acao.setId(1l);
        acao.setNome("Ativo financeiro de testes");
        acao.setCodigoNegociacao("TESTE");
        acao.setPrecoAtual(new BigDecimal("100.00"));
        acao.setQuantidade(2);
        acao.setDataCadastro(LocalDateTime.now());
        acao.setPrecoCompra(new BigDecimal("95.57"));
        acao.setPrecoMinimo(new BigDecimal("90.00"));
        acao.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setId(1l);
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        acao.setUsuario(usuario);
        usuario.getAcoesFavoritas().add(acao);
    }

    @Test
    public void deveObterListaDeAcoesComSucesso(){
        List<Acao> lista = new ArrayList<>();
        lista.add(acao);

        Mockito.when(acaoRepository.findAll()).thenReturn(lista);

        List<AcaoDTO> acoesDTOS = acaoService.obterLista();

        Assertions.assertNotNull(acoesDTOS);
        Assertions.assertFalse(acoesDTOS.isEmpty());
        Assertions.assertEquals(1, acoesDTOS.size());
    }

    @Test
    public void deveObterAcaoComSucessoAtravesDoId(){
        Optional<Acao> optionalAcao = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(optionalAcao);

        Optional<AcaoDTO> optionalAcaoDTO = acaoService.obter(1l);

        Assertions.assertNotNull(optionalAcaoDTO);
        Assertions.assertTrue(optionalAcaoDTO.isPresent());
        Assertions.assertEquals(acao.getCodigoNegociacao(), optionalAcaoDTO.get().getCodigoNegociacao());
        Assertions.assertEquals(acao.getNome(), optionalAcaoDTO.get().getNome());
        Assertions.assertEquals(acao.getQuantidade(), optionalAcaoDTO.get().getQuantidade());
        Assertions.assertEquals(acao.getDataCadastro(), optionalAcaoDTO.get().getDataCadastro());
        Assertions.assertEquals(acao.getUsuario().getEmail(), optionalAcaoDTO.get().getUsuarioEmail());
        Assertions.assertEquals(acao.getPrecoAtual(), optionalAcaoDTO.get().getPrecoAtual());
        Assertions.assertEquals(acao.getPrecoCompra(), optionalAcaoDTO.get().getPrecoCompra());
        Assertions.assertEquals(acao.getPrecoMinimo(), optionalAcaoDTO.get().getPrecoMinimo());
        Assertions.assertEquals(acao.getPrecoMaximo(), optionalAcaoDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveObterAcaoComSucessoAtravesDoCodigoDeNegociacao(){
        Optional<Acao> optionalAcao = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoByCodigoNegociacao(Mockito.anyString())).thenReturn(optionalAcao);

        Optional<AcaoDTO> optionalAcaoDTO = acaoService.obterPorCodigoNegociacao(acao.getCodigoNegociacao());

        Assertions.assertNotNull(optionalAcaoDTO);
        Assertions.assertTrue(optionalAcaoDTO.isPresent());
        Assertions.assertEquals(acao.getCodigoNegociacao(), optionalAcaoDTO.get().getCodigoNegociacao());
        Assertions.assertEquals(acao.getNome(), optionalAcaoDTO.get().getNome());
        Assertions.assertEquals(acao.getQuantidade(), optionalAcaoDTO.get().getQuantidade());
        Assertions.assertEquals(acao.getDataCadastro(), optionalAcaoDTO.get().getDataCadastro());
        Assertions.assertEquals(acao.getUsuario().getEmail(), optionalAcaoDTO.get().getUsuarioEmail());
        Assertions.assertEquals(acao.getPrecoAtual(), optionalAcaoDTO.get().getPrecoAtual());
        Assertions.assertEquals(acao.getPrecoCompra(), optionalAcaoDTO.get().getPrecoCompra());
        Assertions.assertEquals(acao.getPrecoMinimo(), optionalAcaoDTO.get().getPrecoMinimo());
        Assertions.assertEquals(acao.getPrecoMaximo(), optionalAcaoDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveAtualizarAcaoComSucesso(){
        Optional<Acao> optionalAcao = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(optionalAcao);

        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);
        acaoDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acaoDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = acaoService.atualizar(acao.getId(), acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Ação atualizada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarAtualizarAcaoComValorAtualIgualAZero(){
        Optional<Acao> optionalAcao = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(optionalAcao);

        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);
        acaoDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = acaoService.atualizar(acao.getId(), acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "A ação não pode ser atualizada com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarAtualizarAcaoComValorInvalido(){
        Optional<Acao> optionalAcao = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(optionalAcao);

        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);
        acaoDTO.setUsuarioEmail(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acaoDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(acaoRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a ação atualizada."));

        ResponseEntity<String> resposta = acaoService.atualizar(acao.getId(), acaoDTO);
        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar a ação atualizada."));
    }

    @Test
    public void deveFalharAoTentarAtualizarAcaoInexistente(){
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(Optional.empty());

        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);

        ResponseEntity<String> resposta = acaoService.atualizar(acao.getId(), acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "A ação não pôde ser atualizada.");
    }

    @Test
    public void deveCadastrarAcaoComSucesso(){
        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acaoDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(acao.getUsuario()));

        ResponseEntity<String> resposta = acaoService.cadastrar(acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Ação cadastrada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarCadastrarAcaoComValorAtualIgualAZero(){
        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = acaoService.cadastrar(acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "A ação não pode ser cadastrada com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarCadastrarAcaoInvalida(){
        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);
        acaoDTO.setCodigoNegociacao(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acaoDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(acao.getUsuario()));

        ResponseEntity<String> resposta = acaoService.cadastrar(acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar a ação a ser cadastrada."));
    }

    @Test
    public void deveFalharAoTentarCadastrarAcaoComUsuarioInexistente(){
        AcaoDTO acaoDTO = AcaoDTO.fromEntity(acao);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acaoDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = acaoService.cadastrar(acaoDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(resposta.getBody(), "Não há um usuário com o e-mail registrado pela ação.");
    }

    @Test
    public void deveApagarAcaoComSucesso(){
        Optional<Acao> acaoOpt = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(acaoOpt);

        ResponseEntity<String> resposta = acaoService.apagar(acao.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resposta.getBody(), "Ação apagada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarApagarAcaoInexistente(){
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = acaoService.apagar(acao.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "A ação não pôde ser apagada.");
    }

    @Test
    public void deveFalharAoApagarAcaoPorErrosDoRepositorio(){
        Optional<Acao> acaoOpt = Optional.of(acao);
        Mockito.when(acaoRepository.findAcaoById(Mockito.anyLong())).thenReturn(acaoOpt);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar a ação.")).when(acaoRepository).delete(acao);

        ResponseEntity<String> resposta = acaoService.apagar(acao.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde apagar a ação."));
    }
}

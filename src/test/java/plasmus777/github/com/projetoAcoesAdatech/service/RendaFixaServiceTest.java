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
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.RendaFixaRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RendaFixaServiceTest {
    @InjectMocks
    RendaFixaService rendaFixaService;

    @Mock
    RendaFixaRepository rendaFixaRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    FinnhubClient finnhubClient;

    RendaFixa rendaFixa;

    @BeforeEach
    public void beforeEach(){
        rendaFixa = new RendaFixa();
        rendaFixa.setId(1l);
        rendaFixa.setNome("Ativo financeiro de testes");
        rendaFixa.setCodigo("TESTE");
        rendaFixa.setPrecoAtual(new BigDecimal("100.00"));
        rendaFixa.setTaxaRetorno(new BigDecimal("0.25"));
        rendaFixa.setDataVencimento(LocalDateTime.now().plusYears(5l));
        rendaFixa.setDataCadastro(LocalDateTime.now());
        rendaFixa.setPrecoCompra(new BigDecimal("95.57"));
        rendaFixa.setPrecoMinimo(new BigDecimal("90.00"));
        rendaFixa.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setId(1l);
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        rendaFixa.setUsuario(usuario);
        usuario.getRendasFixasFavoritas().add(rendaFixa);
    }

    @Test
    public void deveObterListaDeRendasFixasComSucesso(){
        List<RendaFixa> lista = new ArrayList<>();
        lista.add(rendaFixa);

        Mockito.when(rendaFixaRepository.findAll()).thenReturn(lista);

        List<RendaFixaDTO> rendasFixasDTOS = rendaFixaService.obterLista();

        Assertions.assertNotNull(rendasFixasDTOS);
        Assertions.assertFalse(rendasFixasDTOS.isEmpty());
        Assertions.assertEquals(1, rendasFixasDTOS.size());
    }

    @Test
    public void deveObterRendaFixaComSucessoAtravesDoId(){
        Optional<RendaFixa> optionalRendaFixa = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(optionalRendaFixa);

        Optional<RendaFixaDTO> optionalRendaFixaDTO = rendaFixaService.obter(1l);

        Assertions.assertNotNull(optionalRendaFixaDTO);
        Assertions.assertTrue(optionalRendaFixaDTO.isPresent());
        Assertions.assertEquals(rendaFixa.getCodigo(), optionalRendaFixaDTO.get().getCodigo());
        Assertions.assertEquals(rendaFixa.getNome(), optionalRendaFixaDTO.get().getNome());
        Assertions.assertEquals(rendaFixa.getTaxaRetorno(), optionalRendaFixaDTO.get().getTaxaRetorno());
        Assertions.assertEquals(rendaFixa.getDataCadastro(), optionalRendaFixaDTO.get().getDataCadastro());
        Assertions.assertEquals(rendaFixa.getUsuario().getEmail(), optionalRendaFixaDTO.get().getUsuarioEmail());
        Assertions.assertEquals(rendaFixa.getDataVencimento(), optionalRendaFixaDTO.get().getDataVencimento());
        Assertions.assertEquals(rendaFixa.getPrecoAtual(), optionalRendaFixaDTO.get().getPrecoAtual());
        Assertions.assertEquals(rendaFixa.getPrecoCompra(), optionalRendaFixaDTO.get().getPrecoCompra());
        Assertions.assertEquals(rendaFixa.getPrecoMinimo(), optionalRendaFixaDTO.get().getPrecoMinimo());
        Assertions.assertEquals(rendaFixa.getPrecoMaximo(), optionalRendaFixaDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveObterRendaFixaComSucessoAtravesDoCodigo(){
        Optional<RendaFixa> optionalRendaFixa = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaByCodigo(Mockito.anyString())).thenReturn(optionalRendaFixa);

        Optional<RendaFixaDTO> optionalRendaFixaDTO = rendaFixaService.obterPorCodigo(rendaFixa.getCodigo());

        Assertions.assertNotNull(optionalRendaFixaDTO);
        Assertions.assertTrue(optionalRendaFixaDTO.isPresent());
        Assertions.assertEquals(rendaFixa.getCodigo(), optionalRendaFixaDTO.get().getCodigo());
        Assertions.assertEquals(rendaFixa.getNome(), optionalRendaFixaDTO.get().getNome());
        Assertions.assertEquals(rendaFixa.getTaxaRetorno(), optionalRendaFixaDTO.get().getTaxaRetorno());
        Assertions.assertEquals(rendaFixa.getDataCadastro(), optionalRendaFixaDTO.get().getDataCadastro());
        Assertions.assertEquals(rendaFixa.getUsuario().getEmail(), optionalRendaFixaDTO.get().getUsuarioEmail());
        Assertions.assertEquals(rendaFixa.getDataVencimento(), optionalRendaFixaDTO.get().getDataVencimento());
        Assertions.assertEquals(rendaFixa.getPrecoAtual(), optionalRendaFixaDTO.get().getPrecoAtual());
        Assertions.assertEquals(rendaFixa.getPrecoCompra(), optionalRendaFixaDTO.get().getPrecoCompra());
        Assertions.assertEquals(rendaFixa.getPrecoMinimo(), optionalRendaFixaDTO.get().getPrecoMinimo());
        Assertions.assertEquals(rendaFixa.getPrecoMaximo(), optionalRendaFixaDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveAtualizarRendaFixaComSucesso(){
        Optional<RendaFixa> optionalRendaFixa = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(optionalRendaFixa);

        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);
        rendaFixaDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(rendaFixaDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = rendaFixaService.atualizar(rendaFixa.getId(), rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Renda fixa atualizada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarAtualizarRendaFixaComValorAtualIgualAZero(){
        Optional<RendaFixa> optionalRendaFixa = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(optionalRendaFixa);

        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);
        rendaFixaDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = rendaFixaService.atualizar(rendaFixa.getId(), rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "A renda fixa não pode ser atualizada com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarAtualizarRendaFixaComValorInvalido(){
        Optional<RendaFixa> optionalRendaFixa = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(optionalRendaFixa);

        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);
        rendaFixaDTO.setUsuarioEmail(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(rendaFixaDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(rendaFixaRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar a renda fixa atualizada."));

        ResponseEntity<String> resposta = rendaFixaService.atualizar(rendaFixa.getId(), rendaFixaDTO);
        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar a renda fixa atualizada."));
    }

    @Test
    public void deveFalharAoTentarAtualizarRendaFixaInexistente(){
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(Optional.empty());

        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);

        ResponseEntity<String> resposta = rendaFixaService.atualizar(rendaFixa.getId(), rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "A renda fixa não pôde ser atualizada.");
    }

    @Test
    public void deveCadastrarRendaFixaComSucesso(){
        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(rendaFixaDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(rendaFixa.getUsuario()));

        ResponseEntity<String> resposta = rendaFixaService.cadastrar(rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Renda fixa cadastrada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarCadastrarRendaFixaComValorAtualIgualAZero(){
        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = rendaFixaService.cadastrar(rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "A renda fixa não pode ser cadastrada com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarCadastrarRendaFixaInvalida(){
        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);
        rendaFixaDTO.setCodigo(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(rendaFixaDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(rendaFixa.getUsuario()));

        ResponseEntity<String> resposta = rendaFixaService.cadastrar(rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar a renda fixa a ser cadastrada."));
    }

    @Test
    public void deveFalharAoTentarCadastrarRendaFixaComUsuarioInexistente(){
        RendaFixaDTO rendaFixaDTO = RendaFixaDTO.fromEntity(rendaFixa);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(rendaFixaDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = rendaFixaService.cadastrar(rendaFixaDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(resposta.getBody(), "Não há um usuário com o e-mail registrado pela renda fixa.");
    }

    @Test
    public void deveApagarRendaFixaComSucesso(){
        Optional<RendaFixa> rendaFixaOpt = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(rendaFixaOpt);

        ResponseEntity<String> resposta = rendaFixaService.apagar(rendaFixa.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resposta.getBody(), "Renda fixa apagada com sucesso.");
    }

    @Test
    public void deveFalharAoTentarApagarRendaFixaInexistente(){
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = rendaFixaService.apagar(rendaFixa.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "A renda fixa não pôde ser apagado.");
    }

    @Test
    public void deveFalharAoApagarRendaFixaPorErrosDoRepositorio(){
        Optional<RendaFixa> rendaFixaOpt = Optional.of(rendaFixa);
        Mockito.when(rendaFixaRepository.findRendaFixaById(Mockito.anyLong())).thenReturn(rendaFixaOpt);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar a renda fixa.")).when(rendaFixaRepository).delete(rendaFixa);

        ResponseEntity<String> resposta = rendaFixaService.apagar(rendaFixa.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde apagar a renda fixa."));
    }
}

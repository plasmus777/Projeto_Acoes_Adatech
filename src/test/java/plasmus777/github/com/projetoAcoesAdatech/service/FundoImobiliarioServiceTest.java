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
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.repository.FundoImobiliarioRepository;
import plasmus777.github.com.projetoAcoesAdatech.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FundoImobiliarioServiceTest {
    @InjectMocks
    FundoImobiliarioService fundoImobiliarioService;

    @Mock
    FundoImobiliarioRepository fundoImobiliarioRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    FinnhubClient finnhubClient;

    FundoImobiliario fundoImobiliario;

    @BeforeEach
    public void beforeEach(){
        fundoImobiliario = new FundoImobiliario();
        fundoImobiliario.setId(1l);
        fundoImobiliario.setNome("Ativo financeiro de testes");
        fundoImobiliario.setCodigoFii("TESTE");
        fundoImobiliario.setPrecoAtual(new BigDecimal("100.00"));
        fundoImobiliario.setRendimentoMensal(new BigDecimal("0.25"));
        fundoImobiliario.setDataCadastro(LocalDateTime.now());
        fundoImobiliario.setPrecoCompra(new BigDecimal("95.57"));
        fundoImobiliario.setPrecoMinimo(new BigDecimal("90.00"));
        fundoImobiliario.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setId(1l);
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        fundoImobiliario.setUsuario(usuario);
        usuario.getFundosImobiliariosFavoritos().add(fundoImobiliario);
    }

    @Test
    public void deveObterListaDeFundosImobiliariosComSucesso(){
        List<FundoImobiliario> lista = new ArrayList<>();
        lista.add(fundoImobiliario);

        Mockito.when(fundoImobiliarioRepository.findAll()).thenReturn(lista);

        List<FundoImobiliarioDTO> fundosImobiliariosDTOS = fundoImobiliarioService.obterLista();

        Assertions.assertNotNull(fundosImobiliariosDTOS);
        Assertions.assertFalse(fundosImobiliariosDTOS.isEmpty());
        Assertions.assertEquals(1, fundosImobiliariosDTOS.size());
    }

    @Test
    public void deveObterFundoImobiliarioComSucessoAtravesDoId(){
        Optional<FundoImobiliario> optionalFundoImobiliario = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(optionalFundoImobiliario);

        Optional<FundoImobiliarioDTO> optionalFundoImobiliarioDTO = fundoImobiliarioService.obter(1l);

        Assertions.assertNotNull(optionalFundoImobiliarioDTO);
        Assertions.assertTrue(optionalFundoImobiliarioDTO.isPresent());
        Assertions.assertEquals(fundoImobiliario.getCodigoFii(), optionalFundoImobiliarioDTO.get().getCodigoFii());
        Assertions.assertEquals(fundoImobiliario.getNome(), optionalFundoImobiliarioDTO.get().getNome());
        Assertions.assertEquals(fundoImobiliario.getRendimentoMensal(), optionalFundoImobiliarioDTO.get().getRendimentoMensal());
        Assertions.assertEquals(fundoImobiliario.getDataCadastro(), optionalFundoImobiliarioDTO.get().getDataCadastro());
        Assertions.assertEquals(fundoImobiliario.getUsuario().getEmail(), optionalFundoImobiliarioDTO.get().getUsuarioEmail());
        Assertions.assertEquals(fundoImobiliario.getPrecoAtual(), optionalFundoImobiliarioDTO.get().getPrecoAtual());
        Assertions.assertEquals(fundoImobiliario.getPrecoCompra(), optionalFundoImobiliarioDTO.get().getPrecoCompra());
        Assertions.assertEquals(fundoImobiliario.getPrecoMinimo(), optionalFundoImobiliarioDTO.get().getPrecoMinimo());
        Assertions.assertEquals(fundoImobiliario.getPrecoMaximo(), optionalFundoImobiliarioDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveObterFundoImobiliarioComSucessoAtravesDoCodigoFii(){
        Optional<FundoImobiliario> optionalFundoImobiliario = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioByCodigoFii(Mockito.anyString())).thenReturn(optionalFundoImobiliario);

        Optional<FundoImobiliarioDTO> optionalFundoImobiliarioDTO = fundoImobiliarioService.obterPorCodigoFii(fundoImobiliario.getCodigoFii());

        Assertions.assertNotNull(optionalFundoImobiliarioDTO);
        Assertions.assertTrue(optionalFundoImobiliarioDTO.isPresent());
        Assertions.assertEquals(fundoImobiliario.getCodigoFii(), optionalFundoImobiliarioDTO.get().getCodigoFii());
        Assertions.assertEquals(fundoImobiliario.getNome(), optionalFundoImobiliarioDTO.get().getNome());
        Assertions.assertEquals(fundoImobiliario.getRendimentoMensal(), optionalFundoImobiliarioDTO.get().getRendimentoMensal());
        Assertions.assertEquals(fundoImobiliario.getDataCadastro(), optionalFundoImobiliarioDTO.get().getDataCadastro());
        Assertions.assertEquals(fundoImobiliario.getUsuario().getEmail(), optionalFundoImobiliarioDTO.get().getUsuarioEmail());
        Assertions.assertEquals(fundoImobiliario.getPrecoAtual(), optionalFundoImobiliarioDTO.get().getPrecoAtual());
        Assertions.assertEquals(fundoImobiliario.getPrecoCompra(), optionalFundoImobiliarioDTO.get().getPrecoCompra());
        Assertions.assertEquals(fundoImobiliario.getPrecoMinimo(), optionalFundoImobiliarioDTO.get().getPrecoMinimo());
        Assertions.assertEquals(fundoImobiliario.getPrecoMaximo(), optionalFundoImobiliarioDTO.get().getPrecoMaximo());
    }

    @Test
    public void deveAtualizarFundoImobiliarioComSucesso(){
        Optional<FundoImobiliario> optionalFundoImobiliario = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(optionalFundoImobiliario);

        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);
        fundoImobiliarioDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(fundoImobiliarioDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = fundoImobiliarioService.atualizar(fundoImobiliario.getId(), fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Fundo Imobiliário atualizado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarAtualizarFundoImobiliarioComValorAtualIgualAZero(){
        Optional<FundoImobiliario> optionalFundoImobiliario = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(optionalFundoImobiliario);

        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);
        fundoImobiliarioDTO.setPrecoAtual(new BigDecimal("98.43"));

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = fundoImobiliarioService.atualizar(fundoImobiliario.getId(), fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "O fundo imobiliário não pode ser atualizado com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarAtualizarFundoImobiliarioComValorInvalido(){
        Optional<FundoImobiliario> optionalFundoImobiliario = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(optionalFundoImobiliario);

        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);
        fundoImobiliarioDTO.setUsuarioEmail(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(fundoImobiliarioDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(fundoImobiliarioRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde salvar o fundo imobiliário atualizado."));

        ResponseEntity<String> resposta = fundoImobiliarioService.atualizar(fundoImobiliario.getId(), fundoImobiliarioDTO);
        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar o fundo imobiliário atualizado."));
    }

    @Test
    public void deveFalharAoTentarAtualizarFundoImobiliarioInexistente(){
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(Optional.empty());

        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);

        ResponseEntity<String> resposta = fundoImobiliarioService.atualizar(fundoImobiliario.getId(), fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "O fundo imobiliário não pôde ser atualizado.");
    }

    @Test
    public void deveCadastrarFundoImobiliarioComSucesso(){
        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(fundoImobiliarioDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(fundoImobiliario.getUsuario()));

        ResponseEntity<String> resposta = fundoImobiliarioService.cadastrar(fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(resposta.getBody(), "Fundo imobiliário cadastrado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarCadastrarFundoImobiliarioComValorAtualIgualAZero(){
        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(BigDecimal.ZERO);
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        ResponseEntity<String> resposta = fundoImobiliarioService.cadastrar(fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(resposta.getBody(), "O fundo imobiliário não pode ser cadastrado com um código de um ativo inexistente.");
    }

    @Test
    public void deveFalharAoTentarCadastrarFundoImobiliarioInvalida(){
        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);
        fundoImobiliarioDTO.setCodigoFii(null);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(fundoImobiliarioDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.ofNullable(fundoImobiliario.getUsuario()));

        ResponseEntity<String> resposta = fundoImobiliarioService.cadastrar(fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde salvar o fundo imobiliário a ser cadastrado."));
    }

    @Test
    public void deveFalharAoTentarCadastrarFundoImobiliarioComUsuarioInexistente(){
        FundoImobiliarioDTO fundoImobiliarioDTO = FundoImobiliarioDTO.fromEntity(fundoImobiliario);

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(fundoImobiliarioDTO.getPrecoAtual());
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Mockito.when(usuarioRepository.findUsuarioByEmail(Mockito.any())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = fundoImobiliarioService.cadastrar(fundoImobiliarioDTO);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(resposta.getBody(), "Não há um usuário com o e-mail registrado pelo fundo imobiliário.");
    }

    @Test
    public void deveApagarFundoImobiliarioComSucesso(){
        Optional<FundoImobiliario> fundoImobiliarioOpt = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(fundoImobiliarioOpt);

        ResponseEntity<String> resposta = fundoImobiliarioService.apagar(fundoImobiliario.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resposta.getBody(), "Fundo imobiliário apagado com sucesso.");
    }

    @Test
    public void deveFalharAoTentarApagarFundoImobiliarioInexistente(){
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = fundoImobiliarioService.apagar(fundoImobiliario.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertEquals(resposta.getBody(), "O fundo imobiliário não pôde ser apagado.");
    }

    @Test
    public void deveFalharAoApagarFundoImobiliarioPorErrosDoRepositorio(){
        Optional<FundoImobiliario> fundoImobiliarioOpt = Optional.of(fundoImobiliario);
        Mockito.when(fundoImobiliarioRepository.findFundoImobiliarioById(Mockito.anyLong())).thenReturn(fundoImobiliarioOpt);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "O repositório não pôde apagar o fundo imobiliário.")).when(fundoImobiliarioRepository).delete(fundoImobiliario);

        ResponseEntity<String> resposta = fundoImobiliarioService.apagar(fundoImobiliario.getId());

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals(resposta.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
        Assertions.assertTrue(resposta.getBody() != null && resposta.getBody().contains("O repositório não pôde apagar o fundo imobiliário."));
    }
}

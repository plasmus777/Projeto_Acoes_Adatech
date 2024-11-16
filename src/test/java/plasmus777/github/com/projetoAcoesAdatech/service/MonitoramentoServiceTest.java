package plasmus777.github.com.projetoAcoesAdatech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MonitoramentoServiceTest {

    @InjectMocks
    MonitoramentoService monitoramentoService;

    @Mock
    FinnhubClient finnhubClient;

    @Mock
    UsuarioService usuarioService;

    @Mock
    EmailService emailService;

    @Mock
    AcaoService acaoService;

    @Mock
    FundoImobiliarioService fundoImobiliarioService;

    @Mock
    RendaFixaService rendaFixaService;

    Acao acao;
    FundoImobiliario fundoImobiliario;
    RendaFixa rendaFixa;

    AcaoApi acaoApi;

    @BeforeEach
    void beforeEach(){
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

        acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(acao.getPrecoAtual());

        List<Long> acoesMarcadasParaRemocao = new ArrayList<>();
        acoesMarcadasParaRemocao.add(1L);
        ReflectionTestUtils.setField(monitoramentoService, "acoesMarkedForRemoval", acoesMarcadasParaRemocao);

        List<Long> fundosMarcadosParaRemocao = new ArrayList<>();
        fundosMarcadosParaRemocao.add(1L);
        ReflectionTestUtils.setField(monitoramentoService, "fundosMarkedForRemoval", fundosMarcadosParaRemocao);

        List<Long> rendasMarcadasParaRemocao = new ArrayList<>();
        rendasMarcadasParaRemocao.add(1L);
        ReflectionTestUtils.setField(monitoramentoService, "rendasMarkedForRemoval", rendasMarcadasParaRemocao);
    }

    @Test
    void deveMonitorarAtualizacoesDeDadosComSucessoParaPreçosEntreMinimoEMaximo(){
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setEmail("testador" + i + "@email.com");
            usuarioDTO.setNome("Usuário de testes " + i);
            usuarioDTO.setSenha("Senha123!@#");
            usuarioDTO.setAcoesFavoritas(new ArrayList<>());
            usuarioDTO.setFundosImobiliariosFavoritos(new ArrayList<>());
            usuarioDTO.setRendasFixasFavoritas(new ArrayList<>());

            Usuario usuario = usuarioDTO.toEntity();
            usuario.setId(Integer.toUnsignedLong(i));
            acao.setUsuario(usuario);
            fundoImobiliario.setUsuario(usuario);
            rendaFixa.setUsuario(usuario);
            if(i % 2 != 0){
                acao.setDataCadastro(acao.getDataCadastro().minusMonths(2));
                fundoImobiliario.setDataCadastro(fundoImobiliario.getDataCadastro().minusMonths(2));
                rendaFixa.setDataCadastro(rendaFixa.getDataCadastro().minusMonths(2));

                acao.setPrecoAtual(new BigDecimal("88.12"));
                fundoImobiliario.setPrecoAtual(new BigDecimal("88.12"));
                rendaFixa.setPrecoAtual(new BigDecimal("88.12"));
            }
            usuarioDTO.getAcoesFavoritas().add(acao);
            usuarioDTO.getFundosImobiliariosFavoritos().add(fundoImobiliario);
            usuarioDTO.getRendasFixasFavoritas().add(rendaFixa);

            usuarios.add(usuarioDTO);
        }

        Mockito.when(usuarioService.obterLista()).thenReturn(usuarios);

        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Assertions.assertDoesNotThrow(() -> monitoramentoService.monitorarAtualizacoes());
    }

    @Test
    void deveMonitorarAtualizacoesDeDadosComSucessoParaPreçosMaioresQueOMaximo(){
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setEmail("testador" + i + "@email.com");
            usuarioDTO.setNome("Usuário de testes " + i);
            usuarioDTO.setSenha("Senha123!@#");
            usuarioDTO.setAcoesFavoritas(new ArrayList<>());
            usuarioDTO.setFundosImobiliariosFavoritos(new ArrayList<>());
            usuarioDTO.setRendasFixasFavoritas(new ArrayList<>());

            Usuario usuario = usuarioDTO.toEntity();
            usuario.setId(Integer.toUnsignedLong(i));
            acao.setUsuario(usuario);
            fundoImobiliario.setUsuario(usuario);
            rendaFixa.setUsuario(usuario);
            if(i % 2 != 0){
                acao.setDataCadastro(acao.getDataCadastro().minusMonths(2));
                fundoImobiliario.setDataCadastro(fundoImobiliario.getDataCadastro().minusMonths(2));
                rendaFixa.setDataCadastro(rendaFixa.getDataCadastro().minusMonths(2));

                acao.setPrecoAtual(new BigDecimal("88.12"));
                fundoImobiliario.setPrecoAtual(new BigDecimal("88.12"));
                rendaFixa.setPrecoAtual(new BigDecimal("88.12"));
            }
            usuarioDTO.getAcoesFavoritas().add(acao);
            usuarioDTO.getFundosImobiliariosFavoritos().add(fundoImobiliario);
            usuarioDTO.getRendasFixasFavoritas().add(rendaFixa);

            usuarios.add(usuarioDTO);
        }

        Mockito.when(usuarioService.obterLista()).thenReturn(usuarios);

        acaoApi.setPrecoAtual(new BigDecimal("127.98"));
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Assertions.assertDoesNotThrow(() -> monitoramentoService.monitorarAtualizacoes());
    }

    @Test
    void deveMonitorarAtualizacoesDeDadosComSucessoParaPreçosMenoresQueOMinimo(){
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setEmail("testador" + i + "@email.com");
            usuarioDTO.setNome("Usuário de testes " + i);
            usuarioDTO.setSenha("Senha123!@#");
            usuarioDTO.setAcoesFavoritas(new ArrayList<>());
            usuarioDTO.setFundosImobiliariosFavoritos(new ArrayList<>());
            usuarioDTO.setRendasFixasFavoritas(new ArrayList<>());

            Usuario usuario = usuarioDTO.toEntity();
            usuario.setId(Integer.toUnsignedLong(i));
            acao.setUsuario(usuario);
            fundoImobiliario.setUsuario(usuario);
            rendaFixa.setUsuario(usuario);
            if(i % 2 != 0){
                acao.setDataCadastro(acao.getDataCadastro().minusMonths(2));
                fundoImobiliario.setDataCadastro(fundoImobiliario.getDataCadastro().minusMonths(2));
                rendaFixa.setDataCadastro(rendaFixa.getDataCadastro().minusMonths(2));

                acao.setPrecoAtual(new BigDecimal("88.12"));
                fundoImobiliario.setPrecoAtual(new BigDecimal("88.12"));
                rendaFixa.setPrecoAtual(new BigDecimal("88.12"));
            }
            usuarioDTO.getAcoesFavoritas().add(acao);
            usuarioDTO.getFundosImobiliariosFavoritos().add(fundoImobiliario);
            usuarioDTO.getRendasFixasFavoritas().add(rendaFixa);

            usuarios.add(usuarioDTO);
        }

        Mockito.when(usuarioService.obterLista()).thenReturn(usuarios);

        acaoApi.setPrecoAtual(new BigDecimal("81.13"));
        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Assertions.assertDoesNotThrow(() -> monitoramentoService.monitorarAtualizacoes());
    }

    @Test
    void deveRemoverAtivosAntigosComSucesso(){
        Assertions.assertDoesNotThrow(() -> monitoramentoService.removerAtivosAntigos());

        List<?> acoes = (List<?>) ReflectionTestUtils.getField(monitoramentoService, "acoesMarkedForRemoval");
        List<?> fundosImobiliarios = (List<?>) ReflectionTestUtils.getField(monitoramentoService, "fundosMarkedForRemoval");
        List<?> rendasFixas = (List<?>) ReflectionTestUtils.getField(monitoramentoService, "rendasMarkedForRemoval");

        Assertions.assertTrue(acoes != null && acoes.isEmpty());
        Assertions.assertTrue(fundosImobiliarios != null && fundosImobiliarios.isEmpty());
        Assertions.assertTrue(rendasFixas != null && rendasFixas.isEmpty());
    }
}

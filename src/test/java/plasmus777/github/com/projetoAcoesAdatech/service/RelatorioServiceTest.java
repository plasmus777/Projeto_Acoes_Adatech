package plasmus777.github.com.projetoAcoesAdatech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AtivoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.Relatorio;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.SearchAtivoApi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @InjectMocks
    RelatorioService relatorioService;

    @Mock
    FinnhubClient finnhubClient;

    SearchAtivoApi searchAtivoApi;

    AtivoApi ativoApi;

    AcaoApi acaoApi;

    @BeforeEach
    void beforeEach(){
        ativoApi = new AtivoApi();
        ativoApi.setSymbol("TESTE");
        ativoApi.setDisplaySymbol("TESTE");
        ativoApi.setType("Ação");
        ativoApi.setDescription("Este ativo é uma ação para testes.");

        acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(new BigDecimal("100.00"));
        acaoApi.setAlteracao(new BigDecimal("1.05"));
        acaoApi.setPorcentagemAlteracao(new BigDecimal("0.05"));
        acaoApi.setTimestamp(10000L);
        acaoApi.setMaiorPrecoDiario(new BigDecimal("102.45"));
        acaoApi.setMenorPrecoDiario(new BigDecimal("99.12"));
        acaoApi.setPrecoAbertura(new BigDecimal("101.21"));
        acaoApi.setPrecoFechamentoAnterior(new BigDecimal("97.34"));

        List<AtivoApi> lista = new ArrayList<>();
        lista.add(ativoApi);
        searchAtivoApi = new SearchAtivoApi();
        searchAtivoApi.setCount(1);
        searchAtivoApi.setResult(lista);
    }

    @Test
    void deveGerarRelatorioDeAtivoFinanceiroComSucesso(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isPresent());

        Assertions.assertEquals(acaoApi.getPrecoAtual(), retorno.get().getPrecoAtual());
        Assertions.assertEquals(acaoApi.getAlteracao(), retorno.get().getAlteracao());
        Assertions.assertEquals(acaoApi.getPorcentagemAlteracao(), retorno.get().getPorcentagemAlteracao());
        Assertions.assertEquals(acaoApi.getTimestamp(), retorno.get().getTimestamp());
        Assertions.assertEquals(acaoApi.getMaiorPrecoDiario(), retorno.get().getMaiorPrecoDiario());
        Assertions.assertEquals(acaoApi.getMenorPrecoDiario(), retorno.get().getMenorPrecoDiario());
        Assertions.assertEquals(acaoApi.getPrecoAbertura(), retorno.get().getPrecoAbertura());
        Assertions.assertEquals(acaoApi.getPrecoFechamentoAnterior(), retorno.get().getPrecoFechamentoAnterior());

        Assertions.assertEquals(ativoApi.getSymbol(), retorno.get().getCodigoNegociacao());
        Assertions.assertEquals(ativoApi.getDisplaySymbol(), retorno.get().getCodigoExibicao());
        Assertions.assertEquals(ativoApi.getType(), retorno.get().getTipo());
        Assertions.assertEquals(ativoApi.getDescription(), retorno.get().getDescricao());

    }

    @Test
    void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroComCodigoInvalido(){
        Optional<Relatorio> retorno1 = relatorioService.gerarRelatorio(null);
        Optional<Relatorio> retorno2 = relatorioService.gerarRelatorio("");

        Assertions.assertTrue(retorno1.isEmpty());
        Assertions.assertTrue(retorno2.isEmpty());
    }

    @Test
    void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosInvalidosDePesquisaDaApiParaSearchAtivoApi(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> relatorioService.gerarRelatorio(ativoApi.getSymbol()));
    }

    @Test
    void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosVaziosDePesquisaDaApiParaSearchAtivoApi(){
        searchAtivoApi.setCount(0);
        searchAtivoApi.setResult(new ArrayList<>());

        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosInvalidosDePesquisaDaApiParaAcaoapi(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(null);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isEmpty());
    }
}
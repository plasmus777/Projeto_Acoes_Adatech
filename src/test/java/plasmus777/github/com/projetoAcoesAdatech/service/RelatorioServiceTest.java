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
public class RelatorioServiceTest {

    @InjectMocks
    RelatorioService relatorioService;

    @Mock
    FinnhubClient finnhubClient;

    SearchAtivoApi searchAtivoApi;

    AtivoApi ativoApi;

    AcaoApi acaoApi;

    @BeforeEach
    public void beforeEach(){
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
    public void deveGerarRelatorioDeAtivoFinanceiroComSucesso(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(acaoApi);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isPresent());

        Assertions.assertEquals(retorno.get().getPrecoAtual(), acaoApi.getPrecoAtual());
        Assertions.assertEquals(retorno.get().getAlteracao(), acaoApi.getAlteracao());
        Assertions.assertEquals(retorno.get().getPorcentagemAlteracao(), acaoApi.getPorcentagemAlteracao());
        Assertions.assertEquals(retorno.get().getTimestamp(), acaoApi.getTimestamp());
        Assertions.assertEquals(retorno.get().getMaiorPrecoDiario(), acaoApi.getMaiorPrecoDiario());
        Assertions.assertEquals(retorno.get().getMenorPrecoDiario(), acaoApi.getMenorPrecoDiario());
        Assertions.assertEquals(retorno.get().getPrecoAbertura(), acaoApi.getPrecoAbertura());
        Assertions.assertEquals(retorno.get().getPrecoFechamentoAnterior(), acaoApi.getPrecoFechamentoAnterior());

        Assertions.assertEquals(retorno.get().getCodigoNegociacao(), ativoApi.getSymbol());
        Assertions.assertEquals(retorno.get().getCodigoExibicao(), ativoApi.getDisplaySymbol());
        Assertions.assertEquals(retorno.get().getTipo(), ativoApi.getType());
        Assertions.assertEquals(retorno.get().getDescricao(), ativoApi.getDescription());

    }

    @Test
    public void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroComCodigoInvalido(){
        Optional<Relatorio> retorno1 = relatorioService.gerarRelatorio(null);
        Optional<Relatorio> retorno2 = relatorioService.gerarRelatorio("");

        Assertions.assertTrue(retorno1.isEmpty());
        Assertions.assertTrue(retorno2.isEmpty());
    }

    @Test
    public void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosInvalidosDePesquisaDaApiParaSearchAtivoApi(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> relatorioService.gerarRelatorio(ativoApi.getSymbol()));
    }

    @Test
    public void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosVaziosDePesquisaDaApiParaSearchAtivoApi(){
        searchAtivoApi.setCount(0);
        searchAtivoApi.setResult(new ArrayList<>());

        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    public void deveFalharAoTentarGerarRelatorioDeAtivoFinanceiroPorEncontrarResultadosInvalidosDePesquisaDaApiParaAcaoapi(){
        Mockito.when(finnhubClient.buscarInformacoesAtivos(Mockito.anyString())).thenReturn(searchAtivoApi);

        Mockito.when(finnhubClient.buscarInformacoesAtivo(Mockito.anyString())).thenReturn(null);

        Optional<Relatorio> retorno = relatorioService.gerarRelatorio(ativoApi.getSymbol());

        Assertions.assertTrue(retorno.isEmpty());
    }
}
package plasmus777.github.com.projetoAcoesAdatech.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AtivoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.Relatorio;
import plasmus777.github.com.projetoAcoesAdatech.service.RelatorioService;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RelatorioControllerTest {
    static final String ENDPOINT = "/api/v1/relatorios";

    @InjectMocks
    RelatorioController relatorioController;

    @Mock
    RelatorioService relatorioService;

    MockMvc mockMvc;

    Relatorio relatorio;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(relatorioController).build();

        AtivoApi ativoApi = new AtivoApi();
        ativoApi.setSymbol("TESTE");
        ativoApi.setDisplaySymbol("TESTE");
        ativoApi.setType("Acao");
        ativoApi.setDescription("Este ativo e uma acao para testes.");

        AcaoApi acaoApi = new AcaoApi();
        acaoApi.setPrecoAtual(new BigDecimal("100.00"));
        acaoApi.setAlteracao(new BigDecimal("1.05"));
        acaoApi.setPorcentagemAlteracao(new BigDecimal("0.05"));
        acaoApi.setTimestamp(10000L);
        acaoApi.setMaiorPrecoDiario(new BigDecimal("102.45"));
        acaoApi.setMenorPrecoDiario(new BigDecimal("99.12"));
        acaoApi.setPrecoAbertura(new BigDecimal("101.21"));
        acaoApi.setPrecoFechamentoAnterior(new BigDecimal("97.34"));

        relatorio = new Relatorio(ativoApi, acaoApi);
    }

    @Test
    void deveRetornarRelatorioDeAtivoFinanceiroAtravesDoCodigoComSucesso() throws Exception {
        Mockito.when(relatorioService.gerarRelatorio(Mockito.anyString())).thenReturn(Optional.ofNullable(relatorio));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/TESTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        Relatorio relatorioRetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(relatorioRetorno);
        
        Assertions.assertEquals(relatorioRetorno.getPrecoAtual(), relatorio.getPrecoAtual());
        Assertions.assertEquals(relatorioRetorno.getAlteracao(), relatorio.getAlteracao());
        Assertions.assertEquals(relatorioRetorno.getPorcentagemAlteracao(), relatorio.getPorcentagemAlteracao());
        Assertions.assertEquals(relatorioRetorno.getTimestamp(), relatorio.getTimestamp());
        Assertions.assertEquals(relatorioRetorno.getMaiorPrecoDiario(), relatorio.getMaiorPrecoDiario());
        Assertions.assertEquals(relatorioRetorno.getMenorPrecoDiario(), relatorio.getMenorPrecoDiario());
        Assertions.assertEquals(relatorioRetorno.getPrecoAbertura(), relatorio.getPrecoAbertura());
        Assertions.assertEquals(relatorioRetorno.getPrecoFechamentoAnterior(), relatorio.getPrecoFechamentoAnterior());

        Assertions.assertEquals(relatorioRetorno.getCodigoNegociacao(), relatorio.getCodigoNegociacao());
        Assertions.assertEquals(relatorioRetorno.getCodigoExibicao(), relatorio.getCodigoExibicao());
        Assertions.assertEquals(relatorioRetorno.getTipo(), relatorio.getTipo());
        Assertions.assertEquals(relatorioRetorno.getDescricao(), relatorio.getDescricao());
    }

    @Test
    void deveFalharAoTentarRetornarRelatorioDeAtivoFinanceiroAtravesDoCodigoPorErrosEmRelatorioService() throws Exception {
        Mockito.when(relatorioService.gerarRelatorio(Mockito.anyString())).thenReturn(Optional.empty());

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/TESTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assertions.assertNotNull(resultado.getResponse().getErrorMessage());
        Assertions.assertTrue(resultado.getResponse().getErrorMessage().contains("Não foram encontrados ativos financeiros com código"));
    }
}

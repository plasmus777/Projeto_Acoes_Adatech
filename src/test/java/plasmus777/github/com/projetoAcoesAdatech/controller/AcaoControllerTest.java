package plasmus777.github.com.projetoAcoesAdatech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.AcaoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AcaoController.class)
public class AcaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcaoService acaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObterAcoes() throws Exception {
        List<AcaoDTO> acoes = Arrays.asList(
                new AcaoDTO("Codigo1", 100, "Nome1", new BigDecimal("50.5"), new BigDecimal("45.0"), LocalDateTime.now(), "usuario1@teste.com", new BigDecimal("40.0"), new BigDecimal("60.0")),
                new AcaoDTO("Codigo2", 200, "Nome2", new BigDecimal("60.0"), new BigDecimal("55.0"), LocalDateTime.now(), "usuario2@teste.com", new BigDecimal("50.0"), new BigDecimal("70.0"))
        );

        when(acaoService.obterLista()).thenReturn(acoes);

        mockMvc.perform(get("/api/v1/acoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoNegociacao").value("Codigo1"))
                .andExpect(jsonPath("$[0].nome").value("Nome1"))
                .andExpect(jsonPath("$[1].codigoNegociacao").value("Codigo2"))
                .andExpect(jsonPath("$[1].nome").value("Nome2"));

        verify(acaoService, times(1)).obterLista();
    }

    @Test
    public void testObterAcao() throws Exception {
        AcaoDTO acao = new AcaoDTO("Codigo1", 100, "Nome1", new BigDecimal("50.5"), new BigDecimal("45.0"), LocalDateTime.now(), "usuario1@teste.com", new BigDecimal("40.0"), new BigDecimal("60.0"));

        when(acaoService.obter(1L)).thenReturn(Optional.of(acao));

        mockMvc.perform(get("/api/v1/acoes/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoNegociacao").value("Codigo1"))
                .andExpect(jsonPath("$.nome").value("Nome1"));

        verify(acaoService, times(1)).obter(1L);
    }

    @Test
    public void testObterAcaoPorCodigo() throws Exception {
        AcaoDTO acao = new AcaoDTO("Codigo1", 100, "Nome1", new BigDecimal("50.5"), new BigDecimal("45.0"), LocalDateTime.now(), "usuario1@teste.com", new BigDecimal("40.0"), new BigDecimal("60.0"));

        when(acaoService.obterPorCodigoNegociacao("Codigo1")).thenReturn(Optional.of(acao));

        mockMvc.perform(get("/api/v1/acoes/codigo/{codigoNegociacao}", "Codigo1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoNegociacao").value("Codigo1"))
                .andExpect(jsonPath("$.nome").value("Nome1"));

        verify(acaoService, times(1)).obterPorCodigoNegociacao("Codigo1");
    }

    @Test
    public void testCadastrarAcao() throws Exception {
        AcaoDTO novaAcao = new AcaoDTO("Codigo3", 150, "Nova Acao", new BigDecimal("30.0"), new BigDecimal("25.0"), LocalDateTime.now(), "usuario3@teste.com", new BigDecimal("20.0"), new BigDecimal("35.0"));

        when(acaoService.cadastrar(any(AcaoDTO.class))).thenReturn(new ResponseEntity<>("Ação cadastrada com sucesso", HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/acoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaAcao)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ação cadastrada com sucesso"));

        verify(acaoService, times(1)).cadastrar(any(AcaoDTO.class));
    }

    @Test
    public void testAtualizarAcao() throws Exception {
        AcaoDTO acaoAtualizada = new AcaoDTO("Codigo1", 100, "Nome Atualizado", new BigDecimal("60.0"), new BigDecimal("50.0"), LocalDateTime.now(), "usuario1@teste.com", new BigDecimal("40.0"), new BigDecimal("70.0"));

        when(acaoService.atualizar(eq(1L), any(AcaoDTO.class))).thenReturn(new ResponseEntity<>("Ação atualizada com sucesso", HttpStatus.OK));

        mockMvc.perform(put("/api/v1/acoes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acaoAtualizada)))
                .andExpect(status().isOk())
                .andExpect(content().string("Ação atualizada com sucesso"));

        verify(acaoService, times(1)).atualizar(eq(1L), any(AcaoDTO.class));
    }

    @Test
    public void testApagarAcao() throws Exception {
        when(acaoService.apagar(1L)).thenReturn(new ResponseEntity<>("Ação apagada com sucesso", HttpStatus.OK));

        mockMvc.perform(delete("/api/v1/acoes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Ação apagada com sucesso"));

        verify(acaoService, times(1)).apagar(1L);
    }

    @Test
    public void testObterAcaoNotFound() throws Exception {
        when(acaoService.obter(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/acoes/id/{id}", 99L))
                .andExpect(status().isNotFound());

        verify(acaoService, times(1)).obter(99L);
    }

    @Test
    public void testObterAcaoPorCodigoNotFound() throws Exception {
        when(acaoService.obterPorCodigoNegociacao("CodigoInvalido")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/acoes/codigo/{codigoNegociacao}", "CodigoInvalido"))
                .andExpect(status().isNotFound());

        verify(acaoService, times(1)).obterPorCodigoNegociacao("CodigoInvalido");
    }
}

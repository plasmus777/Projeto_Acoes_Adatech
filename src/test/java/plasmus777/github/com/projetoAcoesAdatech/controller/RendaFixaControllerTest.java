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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.RendaFixaService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RendaFixaControllerTest {
    static final String ENDPOINT = "/api/v1/rendasfixas";

    @InjectMocks
    RendaFixaController rendaFixaController;

    @Mock
    RendaFixaService rendaFixaService;

    MockMvc mockMvc;

    RendaFixaDTO rendaFixaDTO;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(rendaFixaController).build();
        rendaFixaDTO = new RendaFixaDTO();
        rendaFixaDTO.setNome("Ativo financeiro de testes");
        rendaFixaDTO.setCodigo("TESTE");
        rendaFixaDTO.setPrecoAtual(new BigDecimal("100.00"));
        rendaFixaDTO.setTaxaRetorno(new BigDecimal("0.25"));
        rendaFixaDTO.setDataVencimento(LocalDateTime.now().plusYears(5l));
        rendaFixaDTO.setDataCadastro(LocalDateTime.now());
        rendaFixaDTO.setPrecoCompra(new BigDecimal("95.57"));
        rendaFixaDTO.setPrecoMinimo(new BigDecimal("90.00"));
        rendaFixaDTO.setPrecoMaximo(new BigDecimal("125.25"));
        rendaFixaDTO.setUsuarioEmail("usuarioTestes@mail.com");
    }

    @Test
    void deveRetornarRendasFixasCadastradas() throws Exception {
        List<RendaFixaDTO> lista = new ArrayList<>();
        lista.add(rendaFixaDTO);
        Mockito.when(rendaFixaService.obterLista()).thenReturn(lista);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        List<RendaFixaDTO> listaRetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(listaRetorno);
        Assertions.assertFalse(listaRetorno.isEmpty());
        Assertions.assertEquals(1, listaRetorno.size());
    }

    @Test
    void deveObterRendaFixaCadastradaPorId() throws Exception {
        Mockito.when(rendaFixaService.obter(Mockito.anyLong())).thenReturn(Optional.ofNullable(rendaFixaDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        RendaFixaDTO rendaFixaDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(rendaFixaDTORetorno);
        Assertions.assertEquals(rendaFixaDTO.getCodigo(), rendaFixaDTORetorno.getCodigo());
        Assertions.assertEquals(rendaFixaDTO.getNome(), rendaFixaDTORetorno.getNome());
        Assertions.assertEquals(rendaFixaDTO.getUsuarioEmail(), rendaFixaDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(rendaFixaDTO.getPrecoAtual(), rendaFixaDTORetorno.getPrecoAtual());
        Assertions.assertEquals(rendaFixaDTO.getDataCadastro(), rendaFixaDTORetorno.getDataCadastro());
        Assertions.assertEquals(rendaFixaDTO.getDataVencimento(), rendaFixaDTORetorno.getDataVencimento());
        Assertions.assertEquals(rendaFixaDTO.getPrecoCompra(), rendaFixaDTORetorno.getPrecoCompra());
        Assertions.assertEquals(rendaFixaDTO.getPrecoMaximo(), rendaFixaDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(rendaFixaDTO.getPrecoMinimo(), rendaFixaDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(rendaFixaDTO.getTaxaRetorno(), rendaFixaDTORetorno.getTaxaRetorno());

    }

    @Test
    void deveObterRendaFixaCadastradaPorCodigo() throws Exception {
        Mockito.when(rendaFixaService.obterPorCodigo(Mockito.anyString())).thenReturn(Optional.ofNullable(rendaFixaDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/codigo/TESTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        RendaFixaDTO rendaFixaDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(rendaFixaDTORetorno);
        Assertions.assertEquals(rendaFixaDTO.getCodigo(), rendaFixaDTORetorno.getCodigo());
        Assertions.assertEquals(rendaFixaDTO.getNome(), rendaFixaDTORetorno.getNome());
        Assertions.assertEquals(rendaFixaDTO.getUsuarioEmail(), rendaFixaDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(rendaFixaDTO.getPrecoAtual(), rendaFixaDTORetorno.getPrecoAtual());
        Assertions.assertEquals(rendaFixaDTO.getDataCadastro(), rendaFixaDTORetorno.getDataCadastro());
        Assertions.assertEquals(rendaFixaDTO.getDataVencimento(), rendaFixaDTORetorno.getDataVencimento());
        Assertions.assertEquals(rendaFixaDTO.getPrecoCompra(), rendaFixaDTORetorno.getPrecoCompra());
        Assertions.assertEquals(rendaFixaDTO.getPrecoMaximo(), rendaFixaDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(rendaFixaDTO.getPrecoMinimo(), rendaFixaDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(rendaFixaDTO.getTaxaRetorno(), rendaFixaDTORetorno.getTaxaRetorno());

    }

    @Test
    void deveAtualizarRendaFixaCadastradaComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa atualizada com sucesso.");
        Mockito.when(rendaFixaService.atualizar(Mockito.anyLong(), Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rendaFixaDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveCadastrarRendaFixaComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Renda fixa cadastrada com sucesso.");
        Mockito.when(rendaFixaService.cadastrar(Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rendaFixaDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveApagarRendaFixaComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.OK).body("Renda fixa apagada com sucesso.");
        Mockito.when(rendaFixaService.apagar(Mockito.anyLong())).thenReturn(respostaService);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

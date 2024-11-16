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
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.AcaoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AcaoControllerTest {
    static final String ENDPOINT = "/api/v1/acoes";

    @InjectMocks
    AcaoController acaoController;

    @Mock
    AcaoService acaoService;

    MockMvc mockMvc;

    AcaoDTO acaoDTO;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(acaoController).build();
        acaoDTO = new AcaoDTO();
        acaoDTO.setNome("Ativo financeiro de testes");
        acaoDTO.setCodigoNegociacao("TESTE");
        acaoDTO.setPrecoAtual(new BigDecimal("100.00"));
        acaoDTO.setQuantidade(2);
        acaoDTO.setDataCadastro(LocalDateTime.now());
        acaoDTO.setPrecoCompra(new BigDecimal("95.57"));
        acaoDTO.setPrecoMinimo(new BigDecimal("90.00"));
        acaoDTO.setPrecoMaximo(new BigDecimal("125.25"));
        acaoDTO.setUsuarioEmail("usuarioTestes@mail.com");
    }

    @Test
    void deveRetornarAcoesCadastradas() throws Exception {
        List<AcaoDTO> lista = new ArrayList<>();
        lista.add(acaoDTO);
        Mockito.when(acaoService.obterLista()).thenReturn(lista);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        List<AcaoDTO> listaRetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(listaRetorno);
        Assertions.assertFalse(listaRetorno.isEmpty());
        Assertions.assertEquals(1, listaRetorno.size());
    }

    @Test
    void deveObterAcaoCadastradaPorId() throws Exception {
        Mockito.when(acaoService.obter(Mockito.anyLong())).thenReturn(Optional.ofNullable(acaoDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        AcaoDTO acaoDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(acaoDTORetorno);
        Assertions.assertEquals(acaoDTO.getCodigoNegociacao(), acaoDTORetorno.getCodigoNegociacao());
        Assertions.assertEquals(acaoDTO.getNome(), acaoDTORetorno.getNome());
        Assertions.assertEquals(acaoDTO.getUsuarioEmail(), acaoDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(acaoDTO.getPrecoAtual(), acaoDTORetorno.getPrecoAtual());
        Assertions.assertEquals(acaoDTO.getDataCadastro(), acaoDTORetorno.getDataCadastro());
        Assertions.assertEquals(acaoDTO.getPrecoCompra(), acaoDTORetorno.getPrecoCompra());
        Assertions.assertEquals(acaoDTO.getPrecoMaximo(), acaoDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(acaoDTO.getPrecoMinimo(), acaoDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(acaoDTO.getQuantidade(), acaoDTORetorno.getQuantidade());

    }

    @Test
    void deveObterAcaoCadastradaPorCodigoNegociacao() throws Exception {
        Mockito.when(acaoService.obterPorCodigoNegociacao(Mockito.anyString())).thenReturn(Optional.ofNullable(acaoDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/codigo/TESTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        AcaoDTO acaoDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(acaoDTORetorno);
        Assertions.assertEquals(acaoDTO.getCodigoNegociacao(), acaoDTORetorno.getCodigoNegociacao());
        Assertions.assertEquals(acaoDTO.getNome(), acaoDTORetorno.getNome());
        Assertions.assertEquals(acaoDTO.getUsuarioEmail(), acaoDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(acaoDTO.getPrecoAtual(), acaoDTORetorno.getPrecoAtual());
        Assertions.assertEquals(acaoDTO.getDataCadastro(), acaoDTORetorno.getDataCadastro());
        Assertions.assertEquals(acaoDTO.getPrecoCompra(), acaoDTORetorno.getPrecoCompra());
        Assertions.assertEquals(acaoDTO.getPrecoMaximo(), acaoDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(acaoDTO.getPrecoMinimo(), acaoDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(acaoDTO.getQuantidade(), acaoDTORetorno.getQuantidade());

    }

    @Test
    void deveAtualizarAcaoCadastradaComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Ação atualizada com sucesso.");
        Mockito.when(acaoService.atualizar(Mockito.anyLong(), Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acaoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveCadastrarAcaoComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Ação cadastrada com sucesso.");
        Mockito.when(acaoService.cadastrar(Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(acaoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveApagarAcaoComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.OK).body("Ação apagada com sucesso.");
        Mockito.when(acaoService.apagar(Mockito.anyLong())).thenReturn(respostaService);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

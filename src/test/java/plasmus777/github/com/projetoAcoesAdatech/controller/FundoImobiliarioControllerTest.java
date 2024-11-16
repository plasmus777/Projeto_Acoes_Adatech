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
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.FundoImobiliarioService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FundoImobiliarioControllerTest {
    static final String ENDPOINT = "/api/v1/fundosimobiliarios";

    @InjectMocks
    FundoImobiliarioController fundoImobiliarioController;

    @Mock
    FundoImobiliarioService fundoImobiliarioService;

    MockMvc mockMvc;

    FundoImobiliarioDTO fundoImobiliarioDTO;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(fundoImobiliarioController).build();
        fundoImobiliarioDTO = new FundoImobiliarioDTO();
        fundoImobiliarioDTO.setNome("Ativo financeiro de testes");
        fundoImobiliarioDTO.setCodigoFii("TESTE");
        fundoImobiliarioDTO.setPrecoAtual(new BigDecimal("100.00"));
        fundoImobiliarioDTO.setRendimentoMensal(new BigDecimal("0.25"));
        fundoImobiliarioDTO.setDataCadastro(LocalDateTime.now());
        fundoImobiliarioDTO.setPrecoCompra(new BigDecimal("95.57"));
        fundoImobiliarioDTO.setPrecoMinimo(new BigDecimal("90.00"));
        fundoImobiliarioDTO.setPrecoMaximo(new BigDecimal("125.25"));
        fundoImobiliarioDTO.setUsuarioEmail("usuarioTestes@mail.com");
    }

    @Test
    void deveRetornarFundosImobiliariosCadastrados() throws Exception {
        List<FundoImobiliarioDTO> lista = new ArrayList<>();
        lista.add(fundoImobiliarioDTO);
        Mockito.when(fundoImobiliarioService.obterLista()).thenReturn(lista);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        List<FundoImobiliarioDTO> listaRetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(listaRetorno);
        Assertions.assertFalse(listaRetorno.isEmpty());
        Assertions.assertEquals(1, listaRetorno.size());
    }

    @Test
    void deveObterFundoImobiliarioCadastradoPorId() throws Exception {
        Mockito.when(fundoImobiliarioService.obter(Mockito.anyLong())).thenReturn(Optional.ofNullable(fundoImobiliarioDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        FundoImobiliarioDTO fundoImobiliarioDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(fundoImobiliarioDTORetorno);
        Assertions.assertEquals(fundoImobiliarioDTO.getCodigoFii(), fundoImobiliarioDTORetorno.getCodigoFii());
        Assertions.assertEquals(fundoImobiliarioDTO.getNome(), fundoImobiliarioDTORetorno.getNome());
        Assertions.assertEquals(fundoImobiliarioDTO.getUsuarioEmail(), fundoImobiliarioDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoAtual(), fundoImobiliarioDTORetorno.getPrecoAtual());
        Assertions.assertEquals(fundoImobiliarioDTO.getDataCadastro(), fundoImobiliarioDTORetorno.getDataCadastro());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoCompra(), fundoImobiliarioDTORetorno.getPrecoCompra());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoMaximo(), fundoImobiliarioDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoMinimo(), fundoImobiliarioDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(fundoImobiliarioDTO.getRendimentoMensal(), fundoImobiliarioDTORetorno.getRendimentoMensal());

    }

    @Test
    void deveObterFundoImobiliarioCadastradoPorCodigoFii() throws Exception {
        Mockito.when(fundoImobiliarioService.obterPorCodigoFii(Mockito.anyString())).thenReturn(Optional.ofNullable(fundoImobiliarioDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/codigo/TESTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        FundoImobiliarioDTO fundoImobiliarioDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(fundoImobiliarioDTORetorno);
        Assertions.assertEquals(fundoImobiliarioDTO.getCodigoFii(), fundoImobiliarioDTORetorno.getCodigoFii());
        Assertions.assertEquals(fundoImobiliarioDTO.getNome(), fundoImobiliarioDTORetorno.getNome());
        Assertions.assertEquals(fundoImobiliarioDTO.getUsuarioEmail(), fundoImobiliarioDTORetorno.getUsuarioEmail());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoAtual(), fundoImobiliarioDTORetorno.getPrecoAtual());
        Assertions.assertEquals(fundoImobiliarioDTO.getDataCadastro(), fundoImobiliarioDTORetorno.getDataCadastro());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoCompra(), fundoImobiliarioDTORetorno.getPrecoCompra());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoMaximo(), fundoImobiliarioDTORetorno.getPrecoMaximo());
        Assertions.assertEquals(fundoImobiliarioDTO.getPrecoMinimo(), fundoImobiliarioDTORetorno.getPrecoMinimo());
        Assertions.assertEquals(fundoImobiliarioDTO.getRendimentoMensal(), fundoImobiliarioDTORetorno.getRendimentoMensal());

    }

    @Test
    void deveAtualizarFundoImobiliarioCadastradoComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário atualizado com sucesso.");
        Mockito.when(fundoImobiliarioService.atualizar(Mockito.anyLong(), Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fundoImobiliarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveCadastrarFundoImobiliarioComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Fundo imobiliário cadastrado com sucesso.");
        Mockito.when(fundoImobiliarioService.cadastrar(Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fundoImobiliarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveApagarFundoImobiliarioComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.OK).body("Fundo imobiliário apagado com sucesso.");
        Mockito.when(fundoImobiliarioService.apagar(Mockito.anyLong())).thenReturn(respostaService);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

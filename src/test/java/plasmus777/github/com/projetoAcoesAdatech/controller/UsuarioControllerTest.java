package plasmus777.github.com.projetoAcoesAdatech.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    static final String ENDPOINT = "/api/v1/usuarios";

    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    UsuarioService usuarioService;

    MockMvc mockMvc;

    UsuarioDTO usuarioDTO;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuarioTestes@mail.com");
        usuarioDTO.setNome("Compra e vendas de acoes");
        usuarioDTO.setSenha("Senha123!");
        usuarioDTO.setAcoesFavoritas(new ArrayList<>());
        usuarioDTO.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuarioDTO.setRendasFixasFavoritas(new ArrayList<>());
    }

    @Test
    void deveRetornarUsuariosCadastrados() throws Exception {
        List<UsuarioDTO> lista = new ArrayList<>();
        lista.add(usuarioDTO);
        Mockito.when(usuarioService.obterLista()).thenReturn(lista);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<UsuarioDTO> listaRetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(listaRetorno);
        Assertions.assertFalse(listaRetorno.isEmpty());
        Assertions.assertEquals(1, listaRetorno.size());
    }

    @Test
    void deveObterUsuarioCadastradoPorId() throws Exception {
        Mockito.when(usuarioService.obter(Mockito.anyLong())).thenReturn(Optional.ofNullable(usuarioDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioDTO usuarioDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(usuarioDTORetorno);
        Assertions.assertEquals(usuarioDTO.getEmail(), usuarioDTORetorno.getEmail());
        Assertions.assertEquals(usuarioDTO.getNome(), usuarioDTORetorno.getNome());
        Assertions.assertEquals(usuarioDTO.getSenha(), usuarioDTORetorno.getSenha());
        Assertions.assertEquals(usuarioDTO.getAcoesFavoritas(), usuarioDTORetorno.getAcoesFavoritas());
        Assertions.assertEquals(usuarioDTO.getFundosImobiliariosFavoritos(), usuarioDTORetorno.getFundosImobiliariosFavoritos());
        Assertions.assertEquals(usuarioDTO.getRendasFixasFavoritas(), usuarioDTORetorno.getRendasFixasFavoritas());
    }

    @Test
    void deveObterUsuarioCadastradoPorEmail() throws Exception {
        Mockito.when(usuarioService.obterPorEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(usuarioDTO));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/email/" + usuarioDTO.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioDTO usuarioDTORetorno = objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertNotNull(usuarioDTORetorno);
        Assertions.assertEquals(usuarioDTO.getEmail(), usuarioDTORetorno.getEmail());
        Assertions.assertEquals(usuarioDTO.getNome(), usuarioDTORetorno.getNome());
        Assertions.assertEquals(usuarioDTO.getSenha(), usuarioDTORetorno.getSenha());
        Assertions.assertEquals(usuarioDTO.getAcoesFavoritas(), usuarioDTORetorno.getAcoesFavoritas());
        Assertions.assertEquals(usuarioDTO.getFundosImobiliariosFavoritos(), usuarioDTORetorno.getFundosImobiliariosFavoritos());
        Assertions.assertEquals(usuarioDTO.getRendasFixasFavoritas(), usuarioDTORetorno.getRendasFixasFavoritas());
    }

    @Test
    void deveAtualizarUsuarioCadastradoComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Usuário atualizado com sucesso.");
        Mockito.when(usuarioService.atualizar(Mockito.anyLong(), Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
        Mockito.when(usuarioService.cadastrar(Mockito.any())).thenReturn(respostaService);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deveApagarUsuarioComSucesso() throws Exception {
        ResponseEntity<String> respostaService = ResponseEntity.status(HttpStatus.OK).body("Usuário apagado com sucesso.");
        Mockito.when(usuarioService.apagar(Mockito.anyLong())).thenReturn(respostaService);

        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RendaFixaRepositoryTest {

    @Autowired
    RendaFixaRepository rendaFixaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    RendaFixa rendaFixa;

    @BeforeEach
    public void beforeEach(){
        rendaFixa = new RendaFixa();
        rendaFixa.setNome("Ativo financeiro de testes");
        rendaFixa.setCodigo("TESTE");
        rendaFixa.setPrecoAtual(new BigDecimal("100.00"));
        rendaFixa.setTaxaRetorno(new BigDecimal("0.25"));
        rendaFixa.setDataVencimento(LocalDateTime.now().plusYears(5l));
        rendaFixa.setDataCadastro(LocalDateTime.now());
        rendaFixa.setPrecoCompra(new BigDecimal("95.57"));
        rendaFixa.setPrecoMinimo(new BigDecimal("90.00"));
        rendaFixa.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        rendaFixa.setUsuario(usuario);
        usuario.getRendasFixasFavoritas().add(rendaFixa);

        usuarioRepository.save(usuario);
    }

    @AfterEach
    public void afterEach(){
        rendaFixaRepository.delete(rendaFixa);
        rendaFixa.getUsuario().getRendasFixasFavoritas().remove(rendaFixa);
        usuarioRepository.save(rendaFixa.getUsuario());
    }

    @Test
    public void deveSalvarRendaFixaComSucesso(){
        RendaFixa rendaFixa = new RendaFixa();
        rendaFixa.setNome("Ativo financeiro de testes");
        rendaFixa.setCodigo("TESTE2");
        rendaFixa.setPrecoAtual(new BigDecimal("100.00"));
        rendaFixa.setTaxaRetorno(new BigDecimal("0.25"));
        rendaFixa.setDataVencimento(LocalDateTime.now().plusYears(5l));
        rendaFixa.setDataCadastro(LocalDateTime.now());
        rendaFixa.setPrecoCompra(new BigDecimal("95.57"));
        rendaFixa.setPrecoMinimo(new BigDecimal("90.00"));
        rendaFixa.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes2@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        rendaFixa.setUsuario(usuario);
        usuario.getRendasFixasFavoritas().add(rendaFixa);

        usuarioRepository.save(usuario);

        RendaFixa rendaFixaSalva = rendaFixaRepository.findRendaFixaById(rendaFixa.getId()).orElseThrow();

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals(rendaFixaSalva.getCodigo(), rendaFixa.getCodigo());
        Assertions.assertEquals(rendaFixaSalva.getNome(), rendaFixa.getNome());
        Assertions.assertEquals(rendaFixaSalva.getTaxaRetorno(), rendaFixa.getTaxaRetorno());
        Assertions.assertEquals(rendaFixaSalva.getDataCadastro(), rendaFixa.getDataCadastro());
        Assertions.assertEquals(rendaFixaSalva.getUsuario().getEmail(), rendaFixa.getUsuario().getEmail());
        Assertions.assertEquals(rendaFixaSalva.getDataVencimento(), rendaFixa.getDataVencimento());
        Assertions.assertEquals(rendaFixaSalva.getPrecoAtual(), rendaFixa.getPrecoAtual());
        Assertions.assertEquals(rendaFixaSalva.getPrecoCompra(), rendaFixa.getPrecoCompra());
        Assertions.assertEquals(rendaFixaSalva.getPrecoMinimo(), rendaFixa.getPrecoMinimo());
        Assertions.assertEquals(rendaFixaSalva.getPrecoMaximo(), rendaFixa.getPrecoMaximo());
    }

    @Test
    public void deveListarRendasFixasSalvasComSucesso(){
        List<RendaFixa> rendasFixas = rendaFixaRepository.findAll();

        Assertions.assertNotNull(rendasFixas);
        Assertions.assertFalse(rendasFixas.isEmpty());
    }

    @Test
    public void deveEncontrarRendaFixaPorIdComSucesso(){
        Optional<RendaFixa> rendaFixaSalva = rendaFixaRepository.findRendaFixaById(rendaFixa.getId());

        Assertions.assertTrue(rendaFixaSalva.isPresent());
        Assertions.assertEquals(rendaFixaSalva.get().getCodigo(), "TESTE");
        Assertions.assertEquals(rendaFixaSalva.get().getNome(), "Ativo financeiro de testes");
        Assertions.assertEquals(rendaFixaSalva.get().getUsuario().getEmail(), "usuarioTestes@mail.com");
    }
    @Test
    public void deveEncontrarRendaFixaPorCodigoComSucesso(){
        Optional<RendaFixa> rendaFixaSalva = rendaFixaRepository.findRendaFixaByCodigo(rendaFixa.getCodigo());

        Assertions.assertTrue(rendaFixaSalva.isPresent());
        Assertions.assertEquals(rendaFixaSalva.get().getCodigo(), "TESTE");
        Assertions.assertEquals(rendaFixaSalva.get().getNome(), "Ativo financeiro de testes");
        Assertions.assertEquals(rendaFixaSalva.get().getUsuario().getEmail(), "usuarioTestes@mail.com");
    }

    @Test
    public void deveAtualizarRendaFixaComSucesso(){
        Long idAntes = rendaFixa.getId();
        String nomeAntes = rendaFixa.getNome();
        BigDecimal precoAnterior = rendaFixa.getPrecoAtual();

        String nomeDepois = "Ativo financeiro de testes - Atualizado";
        BigDecimal precoAtual = rendaFixa.getPrecoAtual().add(new BigDecimal("5.01"));

        RendaFixa rendaFixaParaAtualizacao = rendaFixaRepository.findRendaFixaById(rendaFixa.getId()).orElseThrow();
        rendaFixaParaAtualizacao.setNome(nomeDepois);
        rendaFixaParaAtualizacao.setPrecoAtual(precoAtual);

        RendaFixa rendaFixaAtualizada = rendaFixaRepository.save(rendaFixaParaAtualizacao);

        Assertions.assertNotNull(rendaFixaAtualizada);
        Assertions.assertEquals(rendaFixaAtualizada.getId(), idAntes);
        Assertions.assertNotEquals(rendaFixaAtualizada.getNome(), nomeAntes);
        Assertions.assertNotEquals(rendaFixaAtualizada.getPrecoAtual(), precoAnterior);
        Assertions.assertEquals(rendaFixaAtualizada.getNome(), nomeDepois);
        Assertions.assertEquals(rendaFixaAtualizada.getPrecoAtual(), precoAnterior.add(new BigDecimal("5.01")));
    }

    @Test
    public void deveApagarRendaFixaComSucesso(){
        rendaFixaRepository.delete(rendaFixa);
        rendaFixa.getUsuario().getRendasFixasFavoritas().remove(rendaFixa);
        usuarioRepository.save(rendaFixa.getUsuario());

        Optional<RendaFixa> rendaFixaOpt = rendaFixaRepository.findRendaFixaByCodigo(rendaFixa.getCodigo());

        Assertions.assertTrue(rendaFixaOpt.isEmpty());
    }
}

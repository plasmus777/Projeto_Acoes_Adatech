package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AcaoRepositoryTest {

    @Autowired
    AcaoRepository acaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    Acao acao;

    @BeforeEach
    void beforeEach() {
        acao = new Acao();
        acao.setNome("Ativo financeiro de testes");
        acao.setCodigoNegociacao("TESTE");
        acao.setPrecoAtual(new BigDecimal("100.00"));
        acao.setQuantidade(2);
        acao.setDataCadastro(LocalDateTime.now());
        acao.setPrecoCompra(new BigDecimal("95.57"));
        acao.setPrecoMinimo(new BigDecimal("90.00"));
        acao.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        acao.setUsuario(usuario);
        usuario.getAcoesFavoritas().add(acao);

        usuarioRepository.save(usuario);
    }

    @AfterEach
    void afterEach() {
        acaoRepository.delete(acao);
        acao.getUsuario().getAcoesFavoritas().remove(acao);
        usuarioRepository.save(acao.getUsuario());
    }

    @Test
    void deveSalvarAcaoComSucesso() {
        acao = new Acao();
        acao.setNome("Ativo financeiro de testes");
        acao.setCodigoNegociacao("TESTE2");
        acao.setPrecoAtual(new BigDecimal("100.00"));
        acao.setQuantidade(2);
        acao.setDataCadastro(LocalDateTime.now());
        acao.setPrecoCompra(new BigDecimal("95.57"));
        acao.setPrecoMinimo(new BigDecimal("90.00"));
        acao.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes2@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        acao.setUsuario(usuario);
        usuario.getAcoesFavoritas().add(acao);

        usuarioRepository.save(usuario);

        Acao acaoSalva = acaoRepository.findAcaoById(acao.getId()).orElseThrow();

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals(acaoSalva.getCodigoNegociacao(), acao.getCodigoNegociacao());
        Assertions.assertEquals(acaoSalva.getNome(), acao.getNome());
        Assertions.assertEquals(acaoSalva.getQuantidade(), acao.getQuantidade());
        Assertions.assertEquals(acaoSalva.getDataCadastro(), acao.getDataCadastro());
        Assertions.assertEquals(acaoSalva.getUsuario().getEmail(), acao.getUsuario().getEmail());
        Assertions.assertEquals(acaoSalva.getPrecoAtual(), acao.getPrecoAtual());
        Assertions.assertEquals(acaoSalva.getPrecoCompra(), acao.getPrecoCompra());
        Assertions.assertEquals(acaoSalva.getPrecoMinimo(), acao.getPrecoMinimo());
        Assertions.assertEquals(acaoSalva.getPrecoMaximo(), acao.getPrecoMaximo());
    }

    @Test
    void deveListarAcoesSalvasComSucesso() {
        List<Acao> acoes = acaoRepository.findAll();

        Assertions.assertNotNull(acoes);
        Assertions.assertFalse(acoes.isEmpty());
    }

    @Test
    void deveEncontrarAcaoPorIdComSucesso() {
        Optional<Acao> acaoSalva = acaoRepository.findAcaoById(acao.getId());

        Assertions.assertTrue(acaoSalva.isPresent());
        Assertions.assertEquals("TESTE", acaoSalva.get().getCodigoNegociacao());
        Assertions.assertEquals("Ativo financeiro de testes", acaoSalva.get().getNome());
        Assertions.assertEquals("usuarioTestes@mail.com", acaoSalva.get().getUsuario().getEmail());
    }

    @Test
    void deveEncontrarAcaoPorCodigoComSucesso() {
        Optional<Acao> acaoSalva = acaoRepository.findAcaoByCodigoNegociacao(acao.getCodigoNegociacao());

        Assertions.assertTrue(acaoSalva.isPresent());
        Assertions.assertEquals("TESTE", acaoSalva.get().getCodigoNegociacao());
        Assertions.assertEquals("Ativo financeiro de testes", acaoSalva.get().getNome());
        Assertions.assertEquals("usuarioTestes@mail.com", acaoSalva.get().getUsuario().getEmail());
    }

    @Test
    void deveAtualizarAcaoComSucesso() {
        Long idAntes = acao.getId();
        String nomeAntes = acao.getNome();
        BigDecimal precoAnterior = acao.getPrecoAtual();

        String nomeDepois = "Ativo financeiro de testes - Atualizado";
        BigDecimal precoAtual = acao.getPrecoAtual().add(new BigDecimal("5.01"));

        Acao acaoParaAtualizacao = acaoRepository.findAcaoById(acao.getId()).orElseThrow();
        acaoParaAtualizacao.setNome(nomeDepois);
        acaoParaAtualizacao.setPrecoAtual(precoAtual);

        Acao acaoAtualizada = acaoRepository.save(acaoParaAtualizacao);

        Assertions.assertNotNull(acaoAtualizada);
        Assertions.assertEquals(acaoAtualizada.getId(), idAntes);
        Assertions.assertNotEquals(acaoAtualizada.getNome(), nomeAntes);
        Assertions.assertNotEquals(acaoAtualizada.getPrecoAtual(), precoAnterior);
        Assertions.assertEquals(acaoAtualizada.getNome(), nomeDepois);
        Assertions.assertEquals(acaoAtualizada.getPrecoAtual(), precoAnterior.add(new BigDecimal("5.01")));
    }

    @Test
    void deveApagarAcaoComSucesso() {
        acaoRepository.delete(acao);
        acao.getUsuario().getAcoesFavoritas().remove(acao);
        usuarioRepository.save(acao.getUsuario());

        Optional<Acao> acaoOpt = acaoRepository.findAcaoByCodigoNegociacao(acao.getCodigoNegociacao());

        Assertions.assertTrue(acaoOpt.isEmpty());
    }
}

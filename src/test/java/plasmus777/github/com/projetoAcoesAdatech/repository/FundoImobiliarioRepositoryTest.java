package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class FundoImobiliarioRepositoryTest {

    @Autowired
    FundoImobiliarioRepository fundoImobiliarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    FundoImobiliario fundoImobiliario;

    @BeforeEach
    public void beforeEach(){
        fundoImobiliario = new FundoImobiliario();
        fundoImobiliario.setNome("Ativo financeiro de testes");
        fundoImobiliario.setCodigoFii("TESTE");
        fundoImobiliario.setPrecoAtual(new BigDecimal("100.00"));
        fundoImobiliario.setRendimentoMensal(new BigDecimal("0.25"));
        fundoImobiliario.setDataCadastro(LocalDateTime.now());
        fundoImobiliario.setPrecoCompra(new BigDecimal("95.57"));
        fundoImobiliario.setPrecoMinimo(new BigDecimal("90.00"));
        fundoImobiliario.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        fundoImobiliario.setUsuario(usuario);
        usuario.getFundosImobiliariosFavoritos().add(fundoImobiliario);

        usuarioRepository.save(usuario);
    }

    @AfterEach
    public void afterEach(){
        fundoImobiliarioRepository.delete(fundoImobiliario);
        fundoImobiliario.getUsuario().getFundosImobiliariosFavoritos().remove(fundoImobiliario);
        usuarioRepository.save(fundoImobiliario.getUsuario());
    }

    @Test
    public void deveSalvarFundoImobiliarioComSucesso(){
        FundoImobiliario fundoImobiliario = new FundoImobiliario();
        fundoImobiliario.setNome("Ativo financeiro de testes");
        fundoImobiliario.setCodigoFii("TESTE2");
        fundoImobiliario.setPrecoAtual(new BigDecimal("100.00"));
        fundoImobiliario.setRendimentoMensal(new BigDecimal("0.25"));
        fundoImobiliario.setDataCadastro(LocalDateTime.now());
        fundoImobiliario.setPrecoCompra(new BigDecimal("95.57"));
        fundoImobiliario.setPrecoMinimo(new BigDecimal("90.00"));
        fundoImobiliario.setPrecoMaximo(new BigDecimal("125.25"));

        Usuario usuario = new Usuario();
        usuario.setEmail("usuarioTestes2@mail.com");
        usuario.setNome("Compra e vendas de ações");
        usuario.setSenha("Senha123!");
        usuario.setAcoesFavoritas(new ArrayList<>());
        usuario.setFundosImobiliariosFavoritos(new ArrayList<>());
        usuario.setRendasFixasFavoritas(new ArrayList<>());
        fundoImobiliario.setUsuario(usuario);
        usuario.getFundosImobiliariosFavoritos().add(fundoImobiliario);

        usuarioRepository.save(usuario);

        FundoImobiliario fundoImobiliarioSalvo = fundoImobiliarioRepository.findFundoImobiliarioById(fundoImobiliario.getId()).orElseThrow();

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals(fundoImobiliarioSalvo.getCodigoFii(), fundoImobiliario.getCodigoFii());
        Assertions.assertEquals(fundoImobiliarioSalvo.getNome(), fundoImobiliario.getNome());
        Assertions.assertEquals(fundoImobiliarioSalvo.getRendimentoMensal(), fundoImobiliario.getRendimentoMensal());
        Assertions.assertEquals(fundoImobiliarioSalvo.getDataCadastro(), fundoImobiliario.getDataCadastro());
        Assertions.assertEquals(fundoImobiliarioSalvo.getUsuario().getEmail(), fundoImobiliario.getUsuario().getEmail());
        Assertions.assertEquals(fundoImobiliarioSalvo.getPrecoAtual(), fundoImobiliario.getPrecoAtual());
        Assertions.assertEquals(fundoImobiliarioSalvo.getPrecoCompra(), fundoImobiliario.getPrecoCompra());
        Assertions.assertEquals(fundoImobiliarioSalvo.getPrecoMinimo(), fundoImobiliario.getPrecoMinimo());
        Assertions.assertEquals(fundoImobiliarioSalvo.getPrecoMaximo(), fundoImobiliario.getPrecoMaximo());
    }

    @Test
    public void deveListarFundosImobiliariosSalvasComSucesso(){
        List<FundoImobiliario> fundosImobiliarios = fundoImobiliarioRepository.findAll();

        Assertions.assertNotNull(fundosImobiliarios);
        Assertions.assertFalse(fundosImobiliarios.isEmpty());
    }

    @Test
    public void deveEncontrarFundoImobiliarioPorIdComSucesso(){
        Optional<FundoImobiliario> fundoImobiliarioSalvo = fundoImobiliarioRepository.findFundoImobiliarioById(fundoImobiliario.getId());

        Assertions.assertTrue(fundoImobiliarioSalvo.isPresent());
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getCodigoFii(), "TESTE");
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getNome(), "Ativo financeiro de testes");
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getUsuario().getEmail(), "usuarioTestes@mail.com");
    }
    @Test
    public void deveEncontrarFundoImobiliarioPorCodigoComSucesso(){
        Optional<FundoImobiliario> fundoImobiliarioSalvo = fundoImobiliarioRepository.findFundoImobiliarioByCodigoFii(fundoImobiliario.getCodigoFii());

        Assertions.assertTrue(fundoImobiliarioSalvo.isPresent());
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getCodigoFii(), "TESTE");
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getNome(), "Ativo financeiro de testes");
        Assertions.assertEquals(fundoImobiliarioSalvo.get().getUsuario().getEmail(), "usuarioTestes@mail.com");
    }

    @Test
    public void deveAtualizarFundoImobiliarioComSucesso(){
        Long idAntes = fundoImobiliario.getId();
        String nomeAntes = fundoImobiliario.getNome();
        BigDecimal precoAnterior = fundoImobiliario.getPrecoAtual();

        String nomeDepois = "Ativo financeiro de testes - Atualizado";
        BigDecimal precoAtual = fundoImobiliario.getPrecoAtual().add(new BigDecimal("5.01"));

        FundoImobiliario fundoImobiliarioParaAtualizacao = fundoImobiliarioRepository.findFundoImobiliarioById(fundoImobiliario.getId()).orElseThrow();
        fundoImobiliarioParaAtualizacao.setNome(nomeDepois);
        fundoImobiliarioParaAtualizacao.setPrecoAtual(precoAtual);

        FundoImobiliario fundoImobiliarioAtualizado = fundoImobiliarioRepository.save(fundoImobiliarioParaAtualizacao);

        Assertions.assertNotNull(fundoImobiliarioAtualizado);
        Assertions.assertEquals(fundoImobiliarioAtualizado.getId(), idAntes);
        Assertions.assertNotEquals(fundoImobiliarioAtualizado.getNome(), nomeAntes);
        Assertions.assertNotEquals(fundoImobiliarioAtualizado.getPrecoAtual(), precoAnterior);
        Assertions.assertEquals(fundoImobiliarioAtualizado.getNome(), nomeDepois);
        Assertions.assertEquals(fundoImobiliarioAtualizado.getPrecoAtual(), precoAnterior.add(new BigDecimal("5.01")));
    }

    @Test
    public void deveApagarFundoImobiliarioComSucesso(){
        fundoImobiliarioRepository.delete(fundoImobiliario);
        fundoImobiliario.getUsuario().getFundosImobiliariosFavoritos().remove(fundoImobiliario);
        usuarioRepository.save(fundoImobiliario.getUsuario());

        Optional<FundoImobiliario> fundoImobiliarioOpt = fundoImobiliarioRepository.findFundoImobiliarioByCodigoFii(fundoImobiliario.getCodigoFii());

        Assertions.assertTrue(fundoImobiliarioOpt.isEmpty());
    }
}

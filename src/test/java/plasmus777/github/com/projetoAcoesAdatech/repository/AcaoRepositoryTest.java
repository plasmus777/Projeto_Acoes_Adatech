package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AcaoRepositoryTest {

    @Autowired
    private AcaoRepository acaoRepository;

    private Acao acao;

    @BeforeEach
    public void setUp() {
        acao = new Acao();
        acao.setId(1L);
        acao.setCodigoNegociacao("Codigo1");
        acao.setNome("Nome1");
        acaoRepository.save(acao);
    }

    @Test
    public void testFindAcaoById() {
        Optional<Acao> foundAcao = acaoRepository.findAcaoById(acao.getId());
        assertThat(foundAcao).isPresent();
        assertThat(foundAcao.get().getId()).isEqualTo(acao.getId());
    }

    @Test
    public void testFindAcaoByCodigoNegociacao() {
        Optional<Acao> foundAcao = acaoRepository.findAcaoByCodigoNegociacao(acao.getCodigoNegociacao());
        assertThat(foundAcao).isPresent();
        assertThat(foundAcao.get().getCodigoNegociacao()).isEqualTo(acao.getCodigoNegociacao());
    }

    @Test
    public void testFindAcaoById_NotFound() {
        Optional<Acao> foundAcao = acaoRepository.findAcaoById(99L);
        assertThat(foundAcao).isNotPresent();
    }

    @Test
    public void testFindAcaoByCodigoNegociacao_NotFound() {
        Optional<Acao> foundAcao = acaoRepository.findAcaoByCodigoNegociacao("CodigoInvalido");
        assertThat(foundAcao).isNotPresent();
    }
}

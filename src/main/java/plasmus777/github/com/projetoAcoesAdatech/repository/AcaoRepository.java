package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;

import java.util.Optional;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {
    Optional<Acao> findAcaoById(Long id);
    Optional<Acao> findAcaoByCodigoNegociacao(String codigoNegociacao);
}

package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

import java.util.Optional;

@Repository
public interface RendaFixaRepository extends JpaRepository<RendaFixa, Long> {
    Optional<RendaFixa> findRendaFixaById(Long id);
    Optional<RendaFixa> findRendaFixaByCodigo(String codigo);
}

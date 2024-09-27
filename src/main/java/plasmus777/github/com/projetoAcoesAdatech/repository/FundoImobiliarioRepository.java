package plasmus777.github.com.projetoAcoesAdatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;

import java.util.Optional;

@Repository
public interface FundoImobiliarioRepository extends JpaRepository<FundoImobiliario, Long> {
    Optional<FundoImobiliario> findFundoImobiliarioById(Long id);
    Optional<FundoImobiliario> findFundoImobiliarioByCodigoFii(String codigoFii);
}

package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Fundos_imobiliarios")
public class FundoImobiliario extends AtivoFinanceiro {

    @Column(name = "codigo_fii")
    private String codigoFii;

    @Column(name = "rendimento_mensal", precision = 10, scale = 2)
    private BigDecimal rendimentoMensal;

    @Override
    public BigDecimal getPrecoVenda() {
        return null;
    }
}

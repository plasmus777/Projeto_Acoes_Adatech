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
@Table(name = "acao")
public class Acao extends AtivoFinanceiro {
    @Column(name = "codigo_negociacao")
    private String codigoNegociacao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Override
    public BigDecimal getPrecoVenda() {
        return null;
    }
}

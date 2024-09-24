package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Rendas_fixas")
public class RendaFixa extends AtivoFinanceiro {

    @Column(name= "taxa_retorno", precision = 10, scale = 2)
    private BigDecimal taxaRetorno;

    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    @Override
    public BigDecimal getPrecoVenda() {
        return null;
    }
}

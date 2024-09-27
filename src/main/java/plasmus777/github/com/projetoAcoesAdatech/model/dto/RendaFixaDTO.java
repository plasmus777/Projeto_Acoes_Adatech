package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

public class RendaFixaDTO implements DTO<RendaFixaDTO, RendaFixa> {

    @NotNull(message = "O valor da taxa de retorno não pode ser nulo.")
    @DecimalMin(value = "0.01", message = "A taxa de retorno deve ser maior que 0.")
    private BigDecimal taxaRetorno;

    @NotNull(message = "A data de vencimento não pode ser nula.")
    @Future(message = "A data de vencimento deve ser uma data futura.")
    private LocalDateTime dataVencimento;

    public RendaFixaDTO(BigDecimal taxaRetorno, LocalDateTime dataVencimento) {
        this.taxaRetorno = taxaRetorno;
        this.dataVencimento = dataVencimento;
    }

    public RendaFixaDTO() {
    }

    public BigDecimal getTaxaRetorno() {
        return taxaRetorno;
    }

    public void setTaxaRetorno(BigDecimal taxaRetorno) {
        this.taxaRetorno = taxaRetorno;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Override
    public RendaFixa toEntity() {
        RendaFixa rendaFixa = new RendaFixa();
        rendaFixa.setTaxaRetorno(this.taxaRetorno);
        rendaFixa.setDataVencimento(this.dataVencimento);
        return rendaFixa;
    }

    @Override
    public RendaFixaDTO fromEntity(RendaFixa rendaFixa) {
        RendaFixaDTO dto = new RendaFixaDTO();
        dto.setTaxaRetorno(rendaFixa.getTaxaRetorno());
        dto.setDataVencimento(rendaFixa.getDataVencimento());
        return dto;
    }
}

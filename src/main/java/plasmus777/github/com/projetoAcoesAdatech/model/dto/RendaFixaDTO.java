package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RendaFixaDTO implements DTO<RendaFixaDTO, RendaFixa>{

    @NotNull(message = "O valor da taxa de retorno não pode ser nula.")
    @DecimalMin(value ="0.01", message = "A taxa de retorno deve ser maior que 0.")
    private BigDecimal taxaRetorno;

    @NotNull(message = "A data de vencimento não pode ser nula.")
    @Future(message = "A data de vencimento deve ser uma data futuro.")
    private LocalDateTime dataVencimento;


    public RendaFixaDTO (BigDecimal taxaRetorno, LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public RendaFixaDTO() {

    }

    // gs
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

    //

    public RendaFixa toEntity() {
        RendaFixa rendaFixa = new RendaFixa();
        rendaFixa.setTaxaRetorno(this.taxaRetorno);
        rendaFixa.setDataVencimento(this.dataVencimento);
        return rendaFixa;
    }

    public RendaFixaDTO fromEntity(RendaFixa rendaFixa) {
        RendaFixaDTO dto = new RendaFixaDTO();
        dto.setTaxaRetorno(rendaFixa.getTaxaRetorno());
        dto.setDataVencimento(rendaFixa.getDataVencimento());
        return dto;
    }
}

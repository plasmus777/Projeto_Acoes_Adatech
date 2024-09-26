package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;

public class FundoImobiliarioDTO {

    //perguntar professor
    @NotNull(message = "O código do FII não pode ser nulo.")
    @Pattern(regexp = "^[A-Z0-9]{1,10}$", message = "O código do FII deve conter apenas letras e números, com até 10 caracteres.")
    @Size(min = 1, max = 10, message = "O código do FII deve ter entre 1 e 10 caracteres.")
    private String codigoFii;

    @NotNull(message = "O rendimento mensal não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O rendimento mensal deve ser um valor positivo.")
    private BigDecimal rendimentoMensal;


    public FundoImobiliarioDTO() {
    }


    public FundoImobiliarioDTO(String codigoFii, BigDecimal rendimentoMensal) {
        this.codigoFii = codigoFii;
        this.rendimentoMensal = rendimentoMensal;
    }

   //gs

    public String getCodigoFii() {
        return codigoFii;
    }

    public BigDecimal getRendimentoMensal() {
        return rendimentoMensal;
    }

    public void setCodigoFii(String codigoFii) {
        this.codigoFii = codigoFii;
    }

    public void setRendimentoMensal(BigDecimal rendimentoMensal) {
        this.rendimentoMensal = rendimentoMensal;
    }

    //

    public FundoImobiliario toEntity() {
        FundoImobiliario fundoImobiliario = new FundoImobiliario();
        fundoImobiliario.setCodigoFii(this.codigoFii);
        fundoImobiliario.setRendimentoMensal(this.rendimentoMensal);
        return fundoImobiliario;
    }

    public static FundoImobiliarioDTO fromEntity(FundoImobiliario fundoImobiliario) {
        FundoImobiliarioDTO dto = new FundoImobiliarioDTO();
        dto.setCodigoFii(fundoImobiliario.getCodigoFii());
        dto.setRendimentoMensal(fundoImobiliario.getRendimentoMensal());
        return dto;
    }
}

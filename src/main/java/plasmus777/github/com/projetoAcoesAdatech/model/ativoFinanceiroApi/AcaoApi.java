package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AcaoApi {
    @JsonProperty("c")
    private BigDecimal precoAtual; //Current price
    @JsonProperty("d")
    private BigDecimal alteracao; //Change
    @JsonProperty("dp")
    private BigDecimal porcentagemAlteracao; //Percent change
    @JsonProperty("h")
    private BigDecimal maiorPrecoDiario; //High price of the day
    @JsonProperty("l")
    private BigDecimal menorPrecoDiario; //Low price of the day
    @JsonProperty("o")
    private BigDecimal precoAbertura; //Open price of the day
    @JsonProperty("pc")
    private BigDecimal precoFechamentoAnterior; //Previous close price
    @JsonProperty("t")
    private Long timestamp; //Timestamp
}

package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AcaoApi {
    private BigDecimal c; //Current price
    private BigDecimal d; //Change
    private BigDecimal dp; //Percent change
    private BigDecimal h; //High price of the day
    private BigDecimal l; //Low price of the day
    private BigDecimal o; //Open price of the day
    private BigDecimal pc; //Previous close price
    private Long t; //Timestamp
}

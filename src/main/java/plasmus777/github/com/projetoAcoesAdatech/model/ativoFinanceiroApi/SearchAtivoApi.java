package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi;

import lombok.Data;

import java.util.List;

@Data
public class SearchAtivoApi {

    private int count;
    List<AtivoApi> result;
}

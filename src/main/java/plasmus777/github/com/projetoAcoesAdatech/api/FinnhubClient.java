package plasmus777.github.com.projetoAcoesAdatech.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.SearchAtivoApi;

@Component
public class FinnhubClient {

    private final String baseUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public FinnhubClient(@Value("${api.finnhub.url}") String baseUrl,
                         @Value("${api.finnhub.key}") String apiKey,
                         RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public String getStockData(String symbol) {
        String url = String.format("%s/stock/quote?symbol=%s&token=%s", baseUrl, symbol, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    //Busca informações sobre ativos financeiros através de um código, e retorna um objeto do tipo SearchAtivoApi.
    public SearchAtivoApi buscarInformacoesAtivos(String codigo) {
        String url = String.format("%s/search?q=%s&token=%s", baseUrl, codigo, apiKey);
        return restTemplate.getForObject(url, SearchAtivoApi.class);
    }

    //Busca informações sobre um ativo financeiro em específico através de um código, e retorna um objeto do tipo AcaoApi.
    public AcaoApi buscarInformacoesAtivo(String codigo) {
        String url = String.format("%s/quote?symbol=%s&token=%s", baseUrl, codigo, apiKey);
        return restTemplate.getForObject(url, AcaoApi.class);
    }
}

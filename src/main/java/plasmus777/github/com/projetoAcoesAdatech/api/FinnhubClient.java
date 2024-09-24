package plasmus777.github.com.projetoAcoesAdatech.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}

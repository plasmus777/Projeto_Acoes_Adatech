package plasmus777.github.com.projetoAcoesAdatech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;

@Configuration
public class AppConfig {

    @Value("${api.finnhub.url}")
    private String finnHubUrl;

    @Value("${api.finnhub.key}")
    private String finnHubKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FinnhubClient finnhubClient(RestTemplate restTemplate) {
        return new FinnhubClient(finnHubUrl, finnHubKey, restTemplate);
    }
}

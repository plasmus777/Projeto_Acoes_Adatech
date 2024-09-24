package plasmus777.github.com.projetoAcoesAdatech.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

@Component
public class ApiAtivosFinanceirosClient {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ApiAtivosFinanceirosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Acao buscarAcao(String ticker) {
        ResponseEntity<Acao> response = restTemplate.getForEntity(apiUrl + "/acoes/" + ticker, Acao.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Falha ao buscar a ação: " + ticker);
    }

    public FundoImobiliario buscarFundoImobiliario(String ticker) {
        ResponseEntity<FundoImobiliario> response = restTemplate.getForEntity(apiUrl + "/fundosImobiliarios/" + ticker, FundoImobiliario.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Falha ao buscar o fundo imobiliário: " + ticker);
    }

    public RendaFixa buscarRendaFixa(String ticker) {
        ResponseEntity<RendaFixa> response = restTemplate.getForEntity(apiUrl + "/rendasFixas/" + ticker, RendaFixa.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Falha ao buscar a renda fixa: " + ticker);
    }
}

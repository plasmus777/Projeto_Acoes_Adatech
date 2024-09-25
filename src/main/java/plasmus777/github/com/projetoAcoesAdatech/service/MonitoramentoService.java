package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.ApiAtivosFinanceirosClient;

import java.util.List;

@Service
public class MonitoramentoService {

    private final ApiAtivosFinanceirosClient apiClient;

    public MonitoramentoService(ApiAtivosFinanceirosClient apiClient) {
        this.apiClient = apiClient;
    }

}

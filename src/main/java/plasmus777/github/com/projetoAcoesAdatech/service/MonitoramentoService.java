package plasmus777.github.com.projetoAcoesAdatech.service;

import plasmus777.github.com.projetoAcoesAdatech.api.ApiAtivosFinanceirosClient;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.AtivoFinanceiro;

import java.util.List;

public class MonitoramentoService {

    private final ApiAtivosFinanceirosClient apiClient;
    private final NotificacaoService notificacaoService;

    public MonitoramentoService(ApiAtivosFinanceirosClient apiClient, NotificacaoService notificacaoService) {
        this.apiClient = apiClient;
        this.notificacaoService = notificacaoService;
    }

}

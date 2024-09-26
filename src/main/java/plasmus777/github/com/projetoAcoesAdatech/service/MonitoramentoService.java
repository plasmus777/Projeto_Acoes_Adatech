package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;

@Service
public class MonitoramentoService {

    private final FinnhubClient finnhubClient;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    public MonitoramentoService(FinnhubClient finnhubClient, UsuarioService usuarioService, EmailService emailService) {
        this.finnhubClient = finnhubClient;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 86400000)
    public void monitorarAtualizacoes(){

    }
}

package plasmus777.github.com.projetoAcoesAdatech.service.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    public void enviarNotificacao(String mensagem) {
        System.out.println("Notificação: " + mensagem);
    }
}

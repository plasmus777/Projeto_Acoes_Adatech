package plasmus777.github.com.projetoAcoesAdatech.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AtivoApi;

import java.util.List;

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

    //Executa o código para atualizar os clientes de hora em hora
    @Scheduled(fixedDelay = 3600000)
    public void monitorarAtualizacoes(){
        List<Usuario> usuarios = usuarioService.obterLista();
        if(usuarios != null && !(usuarios.isEmpty())){
            for(Usuario u: usuarios){
                List<Acao> acoes = u.getAcoesFavoritas();
                if(acoes != null && !(acoes.isEmpty())){
                    for(Acao a: acoes){
                        AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(a.getCodigoNegociacao());
                        if(acaoApi != null){
                            a.setPrecoAtual(acaoApi.getPrecoAtual());
                            if(a.getPrecoAtual().compareTo(a.getPrecoMinimo()) < 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Ação Cadastrada Abaixo do Mínimo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma ação registrada pela sua conta possui preço atual abaixo do valor mínimo configurado.\n\n" +
                                        "Estamos a disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                            if(a.getPrecoAtual().compareTo(a.getPrecoMaximo()) > 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Ação Cadastrada Acima do Máximo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma ação registrada pela sua conta possui preço atual acima do valor máximo configurado.\n\n" +
                                        "Estamos a disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                        }
                    }
                }
                List<FundoImobiliario> fundosImobiliarios = u.getFundosImobiliariosFavoritos();
                if(fundosImobiliarios != null && !(fundosImobiliarios.isEmpty())){
                    for(FundoImobiliario f: fundosImobiliarios){
                        AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(f.getCodigoFii());
                        if(acaoApi != null){
                            f.setPrecoAtual(acaoApi.getPrecoAtual());
                            if(f.getPrecoAtual().compareTo(f.getPrecoMinimo()) < 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Fundo Imobiliário Cadastrado Abaixo do Mínimo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que um fundo imobiliário registrado pela sua conta possui preço atual abaixo do valor mínimo configurado.\n\n" +
                                        "Nome do ativo: " + f.getNome() + "\n" +
                                        "Valor atual do ativo: " + f.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + f.getPrecoMinimo() + "; " + f.getPrecoMaximo() + "\n\n" +
                                        "Estamos a disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                            if(f.getPrecoAtual().compareTo(f.getPrecoMaximo()) > 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Fundo Imobiliário Cadastrado Acima do Máximo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que um fundo imobiliário registrado pela sua conta possui preço atual acima do valor máximo configurado.\n\n" +
                                        "Nome do ativo: " + f.getNome() + "\n" +
                                        "Valor atual do ativo: " + f.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + f.getPrecoMinimo() + "; " + f.getPrecoMaximo() + "\n\n" +
                                        "Estamos a disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                        }
                    }
                }
            }
        }
    }
}

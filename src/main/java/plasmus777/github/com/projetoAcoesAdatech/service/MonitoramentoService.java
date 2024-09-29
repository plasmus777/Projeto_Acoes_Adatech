package plasmus777.github.com.projetoAcoesAdatech.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import plasmus777.github.com.projetoAcoesAdatech.api.FinnhubClient;
import plasmus777.github.com.projetoAcoesAdatech.dto.AcaoDTO;
import plasmus777.github.com.projetoAcoesAdatech.dto.FundoImobiliarioDTO;
import plasmus777.github.com.projetoAcoesAdatech.dto.RendaFixaDTO;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.AtivoFinanceiro;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi.AcaoApi;
import plasmus777.github.com.projetoAcoesAdatech.dto.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoramentoService {

    private final FinnhubClient finnhubClient;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final AcaoService acaoService;
    private final FundoImobiliarioService fundoImobiliarioService;
    private final RendaFixaService rendaFixaService;

    private final List<Long> acoesMarkedForRemoval;
    private final List<Long> fundosMarkedForRemoval;
    private final List<Long> rendasMarkedForRemoval;

    public MonitoramentoService(FinnhubClient finnhubClient, UsuarioService usuarioService, EmailService emailService,
                                AcaoService acaoService, FundoImobiliarioService fundoImobiliarioService, RendaFixaService rendaFixaService) {
        this.finnhubClient = finnhubClient;
        this.usuarioService = usuarioService;
        this.emailService = emailService;

        this.acaoService = acaoService;
        this.fundoImobiliarioService = fundoImobiliarioService;
        this.rendaFixaService = rendaFixaService;

        this.acoesMarkedForRemoval = new ArrayList<>();
        this.fundosMarkedForRemoval = new ArrayList<>();
        this.rendasMarkedForRemoval = new ArrayList<>();

    }

    //Executa o código para atualizar os clientes de hora em hora
    @Scheduled(fixedDelay = 3600000)
    public void monitorarAtualizacoes(){
        List<UsuarioDTO> usuarios = usuarioService.obterLista();
        if(usuarios != null && !(usuarios.isEmpty())){
            for(UsuarioDTO u: usuarios){
                List<Acao> acoes = u.getAcoesFavoritas();
                if(acoes != null && !(acoes.isEmpty())){
                    for(Acao a: acoes){
                        if(LocalDateTime.now().isAfter(a.getDataCadastro().plusMonths(1L))){
                            emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Cadastro de Ação Expirado", "Olá " + u.getNome() + ",\n\n" +
                                    "Notamos que uma ação registrada como favorita em sua conta já foi cadastrada há mais de um mês atrás.\n" +
                                    "Por políticas de manutenção do bom funcionamento sistema, notificamos que a ação será automaticamente removida da base de dados.\n" +
                                    "Caso o ativo ainda sejá útil para você, aqui estão seus dados, que podem ser cadastrados manualmente:\n" +
                                            "Código do ativo: " + a.getCodigoNegociacao() + "\n" +
                                            "Nome do ativo: " + a.getNome() + "\n" +
                                            "Preço atual: " + a.getPrecoAtual() + "\n" +
                                            "Preço de compra:" + a.getPrecoCompra() + "\n" +
                                            "Quantidade: " + a.getQuantidade() + "\n" +
                                            "Valor atual do ativo: " + a.getPrecoAtual() + "\n" +
                                            "Valores mínimos/máximos do ativo: " + a.getPrecoMinimo() + "; " + a.getPrecoMaximo() + "\n" +
                                            "Data de cadastro: " + a.getDataCadastro() + "\n" +
                                            "E-mail do usuário: " + a.getUsuario().getEmail() + "\n\n" +
                                    "Estamos à disposição para ajudar,\n" +
                                    "- Equipe do Projeto Ações Adatech");

                            acoesMarkedForRemoval.add(a.getId());
                        }

                        AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(a.getCodigoNegociacao());
                        if(acaoApi != null){
                            a.setPrecoAtual(acaoApi.getPrecoAtual());
                            acaoService.atualizar(a.getId(), AcaoDTO.fromEntity(a));
                            if(a.getPrecoAtual().compareTo(a.getPrecoMinimo()) < 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Ação Cadastrada Abaixo do Mínimo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma ação registrada pela sua conta possui preço atual abaixo do valor mínimo configurado.\n\n" +
                                        "Código do ativo: " + a.getCodigoNegociacao() + "\n" +
                                        "Nome do ativo: " + a.getNome() + "\n" +
                                        "Valor atual do ativo: " + a.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + a.getPrecoMinimo() + "; " + a.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                            if(a.getPrecoAtual().compareTo(a.getPrecoMaximo()) > 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Ação Cadastrada Acima do Máximo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma ação registrada pela sua conta possui preço atual acima do valor máximo configurado.\n\n" +
                                        "Código do ativo: " + a.getCodigoNegociacao() + "\n" +
                                        "Nome do ativo: " + a.getNome() + "\n" +
                                        "Valor atual do ativo: " + a.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + a.getPrecoMinimo() + "; " + a.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                        }
                    }
                }
                List<FundoImobiliario> fundosImobiliarios = u.getFundosImobiliariosFavoritos();
                if(fundosImobiliarios != null && !(fundosImobiliarios.isEmpty())){
                    for(FundoImobiliario f: fundosImobiliarios){
                        if(LocalDateTime.now().isAfter(f.getDataCadastro().plusMonths(1L))){
                            emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Cadastro de Fundo Imobiliário Expirado", "Olá " + u.getNome() + ",\n\n" +
                                    "Notamos que um fundo imobiliário registrado como favorito em sua conta já foi cadastrado há mais de um mês atrás.\n" +
                                    "Por políticas de manutenção do bom funcionamento sistema, notificamos que o fundo imobiliário será automaticamente removido da base de dados.\n" +
                                    "Caso o ativo ainda sejá útil para você, aqui estão seus dados, que podem ser cadastrados manualmente:\n" +
                                    "Código do ativo: " + f.getCodigoFii() + "\n" +
                                    "Nome do ativo: " + f.getNome() + "\n" +
                                    "Preço atual: " + f.getPrecoAtual() + "\n" +
                                    "Preço de compra:" + f.getPrecoCompra() + "\n" +
                                    "Rendimento mensal: " + f.getRendimentoMensal() + "\n" +
                                    "Valor atual do ativo: " + f.getPrecoAtual() + "\n" +
                                    "Valores mínimos/máximos do ativo: " + f.getPrecoMinimo() + "; " + f.getPrecoMaximo() + "\n" +
                                    "Data de cadastro: " + f.getDataCadastro() + "\n" +
                                    "E-mail do usuário: " + f.getUsuario().getEmail() + "\n\n" +
                                    "Estamos à disposição para ajudar,\n" +
                                    "- Equipe do Projeto Ações Adatech");

                            fundosMarkedForRemoval.add(f.getId());
                        }

                        AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(f.getCodigoFii());
                        if(acaoApi != null){
                            f.setPrecoAtual(acaoApi.getPrecoAtual());
                            fundoImobiliarioService.atualizar(f.getId(), FundoImobiliarioDTO.fromEntity(f));
                            if(f.getPrecoAtual().compareTo(f.getPrecoMinimo()) < 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Fundo Imobiliário Cadastrado Abaixo do Mínimo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que um fundo imobiliário registrado pela sua conta possui preço atual abaixo do valor mínimo configurado.\n\n" +
                                        "Código do ativo: " + f.getCodigoFii() + "\n" +
                                        "Nome do ativo: " + f.getNome() + "\n" +
                                        "Valor atual do ativo: " + f.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + f.getPrecoMinimo() + "; " + f.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                            if(f.getPrecoAtual().compareTo(f.getPrecoMaximo()) > 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Fundo Imobiliário Cadastrado Acima do Máximo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que um fundo imobiliário registrado pela sua conta possui preço atual acima do valor máximo configurado.\n\n" +
                                        "Código do ativo: " + f.getCodigoFii() + "\n" +
                                        "Nome do ativo: " + f.getNome() + "\n" +
                                        "Valor atual do ativo: " + f.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + f.getPrecoMinimo() + "; " + f.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                        }
                    }
                }
                List<RendaFixa> rendaFixas = u.getRendasFixasFavoritas();
                if(rendaFixas != null && !(rendaFixas.isEmpty())){
                    for(RendaFixa r: rendaFixas){
                        if(LocalDateTime.now().isAfter(r.getDataCadastro().plusMonths(1L))){
                            emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Cadastro de Renda Fixa Expirado", "Olá " + u.getNome() + ",\n\n" +
                                    "Notamos que uma renda fixa registrada como favorita em sua conta já foi cadastrada há mais de um mês atrás.\n" +
                                    "Por políticas de manutenção do bom funcionamento sistema, notificamos que a renda fixa será automaticamente removida da base de dados.\n" +
                                    "Caso o ativo ainda sejá útil para você, aqui estão seus dados, que podem ser cadastrados manualmente:\n" +
                                    "Código do ativo: " + r.getCodigo() + "\n" +
                                    "Nome do ativo: " + r.getNome() + "\n" +
                                    "Preço atual: " + r.getPrecoAtual() + "\n" +
                                    "Preço de compra:" + r.getPrecoCompra() + "\n" +
                                    "Data de vencimento: " + r.getDataVencimento() + "\n" +
                                    "Taxa de retorno: " + r.getTaxaRetorno() + "\n" +
                                    "Valor atual do ativo: " + r.getPrecoAtual() + "\n" +
                                    "Valores mínimos/máximos do ativo: " + r.getPrecoMinimo() + "; " + r.getPrecoMaximo() + "\n" +
                                    "Data de cadastro: " + r.getDataCadastro() + "\n" +
                                    "E-mail do usuário: " + r.getUsuario().getEmail() + "\n\n" +
                                    "Estamos à disposição para ajudar,\n" +
                                    "- Equipe do Projeto Ações Adatech");

                            rendasMarkedForRemoval.add(r.getId());
                        }

                        AcaoApi acaoApi = finnhubClient.buscarInformacoesAtivo(r.getCodigo());
                        if(acaoApi != null){
                            r.setPrecoAtual(acaoApi.getPrecoAtual());
                            rendaFixaService.atualizar(r.getId(), RendaFixaDTO.fromEntity(r));
                            if(r.getPrecoAtual().compareTo(r.getPrecoMinimo()) < 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Renda Fixa Cadastrada Abaixo do Mínimo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma renda fixa registrada pela sua conta possui preço atual abaixo do valor mínimo configurado.\n\n" +
                                        "Código do ativo: " + r.getCodigo() + "\n" +
                                        "Nome do ativo: " + r.getNome() + "\n" +
                                        "Valor atual do ativo: " + r.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + r.getPrecoMinimo() + "; " + r.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                            if(r.getPrecoAtual().compareTo(r.getPrecoMaximo()) > 0){
                                emailService.enviarEmail(u.getEmail(), "Projeto Ações Adatech - Renda Fixa Cadastrada Acima do Máximo", "Olá " + u.getNome() + ",\n\n" +
                                        "Nosso sistema detectou que uma renda fixa registrada pela sua conta possui preço atual acima do valor máximo configurado.\n\n" +
                                        "Código do ativo: " + r.getCodigo() + "\n" +
                                        "Nome do ativo: " + r.getNome() + "\n" +
                                        "Valor atual do ativo: " + r.getPrecoAtual() + "\n" +
                                        "Valores mínimos/máximos do ativo: " + r.getPrecoMinimo() + "; " + r.getPrecoMaximo() + "\n\n" +
                                        "Estamos à disposição para ajudar,\n" +
                                        "- Equipe do Projeto Ações Adatech");
                            }
                        }
                    }
                }
            }
        }
    }

    //Executa o código para remover ativos com mais de 30 dias diariamente
    @Scheduled(fixedDelay = 86400000)
    public void removerAtivosAntigos(){
        if(!(acoesMarkedForRemoval.isEmpty())){
            for(Long l: acoesMarkedForRemoval){
                acaoService.apagar(l);
            }
        }
        if(!(fundosMarkedForRemoval.isEmpty())){
            for(Long l: fundosMarkedForRemoval){
                fundoImobiliarioService.apagar(l);
            }
        }if(!(rendasMarkedForRemoval.isEmpty())){
            for(Long l: rendasMarkedForRemoval){
                rendaFixaService.apagar(l);
            }
        }

        acoesMarkedForRemoval.clear();
        fundosMarkedForRemoval.clear();
        rendasMarkedForRemoval.clear();
    }
}

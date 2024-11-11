package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiroApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor
public class Relatorio {
    private String codigoNegociacao;
    private String codigoExibicao;
    private String descricao;
    private String tipo;
    private BigDecimal precoAtual;
    private BigDecimal alteracao;
    private BigDecimal porcentagemAlteracao;
    private BigDecimal maiorPrecoDiario;
    private BigDecimal menorPrecoDiario;
    private BigDecimal precoAbertura;
    private BigDecimal precoFechamentoAnterior;
    private Long timestamp;

    public Relatorio(AtivoApi sobre, AcaoApi desempenho){
        this.codigoNegociacao = sobre.getSymbol();
        this.codigoExibicao = sobre.getDisplaySymbol();
        this.descricao = sobre.getDescription();
        this.tipo = sobre.getType();
        this.precoAtual = desempenho.getPrecoAtual();
        this.alteracao = desempenho.getAlteracao();
        this.porcentagemAlteracao = desempenho.getPorcentagemAlteracao();
        this.maiorPrecoDiario = desempenho.getMaiorPrecoDiario();
        this.menorPrecoDiario = desempenho.getMenorPrecoDiario();
        this.precoAbertura = desempenho.getPrecoAbertura();
        this.precoFechamentoAnterior = desempenho.getPrecoFechamentoAnterior();
        this.timestamp = desempenho.getTimestamp();
    }
}

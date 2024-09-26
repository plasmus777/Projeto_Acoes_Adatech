package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.Acao;

public class AcaoDTO implements DTO<AcaoDTO, Acao>{

@NotNull(message = "O código de negociação não pode ser nulo.")
@Pattern(regexp = "^[A-Z 0-9]{1,5}$", message = "O código de negociação deve conter apenas letras e números, com até 5 caracteres.")
@Size(min=1, max=5, message = "O código de negociação deve ter entre 1 a 5 caracteres.")
private String codigoNegociacao;

@NotNull(message = "A quantidade de ações não pode ser nula.")
@Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
private Integer quantidade;


public AcaoDTO(String codigoNegociacao, Integer quantidade) {
        this.codigoNegociacao = codigoNegociacao;
        this.quantidade = quantidade;
    }

    public AcaoDTO() {
    }

//g&s

    public String getCodigoNegociacao() {
        return codigoNegociacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setCodigoNegociacao(String codigoNegociacao) {
        this.codigoNegociacao = codigoNegociacao;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
//
    @Override
    public Acao toEntity() {
        Acao acao = new Acao();
        acao.setCodigoNegociacao(this.codigoNegociacao);
        acao.setQuantidade(this.quantidade);
        return acao;
    }

    @Override
    public AcaoDTO fromEntity(Acao acao) {
        AcaoDTO dto = new AcaoDTO();
        dto.setCodigoNegociacao(acao.getCodigoNegociacao());
        dto.setQuantidade(acao.getQuantidade());
        return dto;
    }
}


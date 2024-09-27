package plasmus777.github.com.projetoAcoesAdatech.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.FundoImobiliario;

public class FundoImobiliarioDTO {

    @NotBlank(message = "O código do FII não pode ser nulo.")
    @Pattern(regexp = "^[A-Z0-9]{1,11}$", message = "O código do FII deve conter apenas letras e números, com até 11 caracteres.")
    @Size(min = 1, max = 11, message = "O código do FII deve ter entre 1 e 10 caracteres.")
    private String codigoFii;

    @NotNull(message = "O rendimento mensal não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O rendimento mensal deve ser um valor positivo.")
    private BigDecimal rendimentoMensal;

    @NotBlank(message = "O nome não pode ser nulo.")
    @Size(min = 1, max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotNull(message = "O preço atual não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço atual deve ser um valor positivo.")
    private BigDecimal precoAtual;

    @NotNull(message = "O preço de compra não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço de compra deve ser um valor positivo.")
    private BigDecimal precoCompra;

    @NotNull(message = "A data de cadastro não pode ser nula.")
    private LocalDateTime dataCadastro;

    @NotNull(message = "O e-mail do usuário não pode ser nulo.")
    @Size(min = 1, max = 255, message = "O nome deve ter no máximo 100 caracteres.")
    @Email(message = "O e-mail do usuário deve ser válido.")
    private String usuarioEmail;

    @NotNull(message = "O preço mínimo não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço mínimo deve ser um valor positivo.")
    private BigDecimal precoMinimo;

    @NotNull(message = "O preço máximo não pode ser nulo.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço máximo deve ser um valor positivo.")
    private BigDecimal precoMaximo;

    public FundoImobiliarioDTO() {
    }

    public FundoImobiliarioDTO(String codigoFii, BigDecimal rendimentoMensal, String nome, BigDecimal precoAtual, BigDecimal precoCompra, LocalDateTime dataCadastro, String usuarioEmail, BigDecimal precoMinimo, BigDecimal precoMaximo) {
        this.codigoFii = codigoFii;
        this.rendimentoMensal = rendimentoMensal;
        this.nome = nome;
        this.precoAtual = precoAtual;
        this.precoCompra = precoCompra;
        this.dataCadastro = dataCadastro;
        this.usuarioEmail = usuarioEmail;
        this.precoMinimo = precoMinimo;
        this.precoMaximo = precoMaximo;
    }

    public String getCodigoFii() {
        return codigoFii;
    }

    public void setCodigoFii(String codigoFii) {
        this.codigoFii = codigoFii;
    }

    public BigDecimal getRendimentoMensal() {
        return rendimentoMensal;
    }

    public void setRendimentoMensal(BigDecimal rendimentoMensal) {
        this.rendimentoMensal = rendimentoMensal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(BigDecimal precoAtual) {
        this.precoAtual = precoAtual;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public BigDecimal getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(BigDecimal precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public BigDecimal getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(BigDecimal precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public FundoImobiliario toEntity() {
        FundoImobiliario fundoImobiliario = new FundoImobiliario();
        fundoImobiliario.setCodigoFii(this.codigoFii);
        fundoImobiliario.setRendimentoMensal(this.rendimentoMensal);
        fundoImobiliario.setNome(this.nome);
        fundoImobiliario.setPrecoAtual(this.precoAtual);
        fundoImobiliario.setPrecoCompra(this.precoCompra);
        fundoImobiliario.setDataCadastro(this.dataCadastro);
        //fundoImobiliario.setUsuario(this.usuario);
        fundoImobiliario.setPrecoMinimo(this.precoMinimo);
        fundoImobiliario.setPrecoMaximo(this.precoMaximo);
        return fundoImobiliario;
    }

    public static FundoImobiliarioDTO fromEntity(FundoImobiliario fundoImobiliario) {
        if (fundoImobiliario == null) {
            return null;
        }
        FundoImobiliarioDTO dto = new FundoImobiliarioDTO();
        dto.setCodigoFii(fundoImobiliario.getCodigoFii());
        dto.setRendimentoMensal(fundoImobiliario.getRendimentoMensal());
        dto.setNome(fundoImobiliario.getNome());
        dto.setPrecoAtual(fundoImobiliario.getPrecoAtual());
        dto.setPrecoCompra(fundoImobiliario.getPrecoCompra());
        dto.setDataCadastro(fundoImobiliario.getDataCadastro());
        dto.setUsuarioEmail(fundoImobiliario.getUsuario().getEmail());
        dto.setPrecoMinimo(fundoImobiliario.getPrecoMinimo());
        dto.setPrecoMaximo(fundoImobiliario.getPrecoMaximo());
        return dto;
    }
}

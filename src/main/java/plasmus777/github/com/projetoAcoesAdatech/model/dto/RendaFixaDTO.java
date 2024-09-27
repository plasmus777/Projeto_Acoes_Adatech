package plasmus777.github.com.projetoAcoesAdatech.model.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro.RendaFixa;

public class RendaFixaDTO implements DTO<RendaFixaDTO, RendaFixa> {

    @NotNull(message = "O valor da taxa de retorno não pode ser nulo.")
    @DecimalMin(value = "0.01", message = "A taxa de retorno deve ser maior que 0.")
    private BigDecimal taxaRetorno;

    @NotNull(message = "A data de vencimento não pode ser nula.")
    @Future(message = "A data de vencimento deve ser uma data futura.")
    private LocalDateTime dataVencimento;

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

    public RendaFixaDTO() {
    }

    public RendaFixaDTO(BigDecimal taxaRetorno, LocalDateTime dataVencimento, String nome, BigDecimal precoAtual, BigDecimal precoCompra, LocalDateTime dataCadastro, String usuarioEmail, BigDecimal precoMinimo, BigDecimal precoMaximo) {
        this.taxaRetorno = taxaRetorno;
        this.dataVencimento = dataVencimento;
        this.nome = nome;
        this.precoAtual = precoAtual;
        this.precoCompra = precoCompra;
        this.dataCadastro = dataCadastro;
        this.usuarioEmail = usuarioEmail;
        this.precoMinimo = precoMinimo;
        this.precoMaximo = precoMaximo;
    }

    public BigDecimal getTaxaRetorno() {
        return taxaRetorno;
    }

    public void setTaxaRetorno(BigDecimal taxaRetorno) {
        this.taxaRetorno = taxaRetorno;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
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

    @Override
    public RendaFixa toEntity() {
        RendaFixa rendaFixa = new RendaFixa();
        rendaFixa.setTaxaRetorno(this.taxaRetorno);
        rendaFixa.setDataVencimento(this.dataVencimento);
        rendaFixa.setNome(this.nome);
        rendaFixa.setPrecoAtual(this.precoAtual);
        rendaFixa.setPrecoCompra(this.precoCompra);
        rendaFixa.setDataCadastro(this.dataCadastro);
        //rendaFixa.setUsuario(this.usuario);
        rendaFixa.setPrecoMinimo(this.precoMinimo);
        rendaFixa.setPrecoMaximo(this.precoMaximo);
        return rendaFixa;
    }

    @Override
    public RendaFixaDTO fromEntity(RendaFixa rendaFixa) {
        if (rendaFixa == null) {
            return null;
        }
        RendaFixaDTO dto = new RendaFixaDTO();
        dto.setTaxaRetorno(rendaFixa.getTaxaRetorno());
        dto.setDataVencimento(rendaFixa.getDataVencimento());
        dto.setNome(rendaFixa.getNome());
        dto.setPrecoAtual(rendaFixa.getPrecoAtual());
        dto.setPrecoCompra(rendaFixa.getPrecoCompra());
        dto.setDataCadastro(rendaFixa.getDataCadastro());
        dto.setUsuarioEmail(rendaFixa.getUsuario().getEmail());
        dto.setPrecoMinimo(rendaFixa.getPrecoMinimo());
        dto.setPrecoMaximo(rendaFixa.getPrecoMaximo());
        return dto;
    }
}

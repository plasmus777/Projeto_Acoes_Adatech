package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro;

import jakarta.persistence.*;
import lombok.Data;
import plasmus777.github.com.projetoAcoesAdatech.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AtivoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "preco_atual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoAtual;

    @Column(name = "preco_compra", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoCompra;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
    private Usuario usuario;

    @Column(name = "preco_minimo", nullable = false)
    private BigDecimal precoMinimo;

    @Column(name = "preco_maximo", nullable = false)
    private BigDecimal precoMaximo;

    public abstract BigDecimal getPrecoVenda();
}

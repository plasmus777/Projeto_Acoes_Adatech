package plasmus777.github.com.projetoAcoesAdatech.model.ativoFinanceiro;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class AtivoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valorAtual;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Column
    private Boolean favorito = false;

    @Column
    private BigDecimal precoCompra;

    @Column
    private BigDecimal precoVenda;
}

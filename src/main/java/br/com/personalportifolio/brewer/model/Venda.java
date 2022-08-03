package br.com.personalportifolio.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Venda implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusVenda status;
    private String observacao;
    private LocalDate dataEntrega;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "venda")
    private List<ItemVenda> itemVenda;
}

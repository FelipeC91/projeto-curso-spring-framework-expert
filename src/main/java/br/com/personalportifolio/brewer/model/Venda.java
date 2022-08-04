package br.com.personalportifolio.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

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
    @NumberFormat(style = Style.CURRENCY, pattern = "#,###,###,###.##")
    private BigDecimal valorFrete;

    @Column(name = "valor_desconto")
    @NumberFormat(style = Style.CURRENCY, pattern = "#,###,###,###.##")
    private BigDecimal valorDesconto;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusVenda status = StatusVenda.ORCAMENTO;

    private String observacao;

    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @Transient
    private String uuid;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

    @Transient
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horarioEntrega;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "venda")
    private List<ItemVenda> itemVenda;

    public boolean isNova() {
        return this.codigo == null;
    }
}
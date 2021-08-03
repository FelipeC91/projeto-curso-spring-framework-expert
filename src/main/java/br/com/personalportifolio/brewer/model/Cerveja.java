package br.com.personalportifolio.brewer.model;

import br.com.personalportifolio.brewer.validation.SKU;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.coobird.thumbnailator.name.Rename;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cerveja implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @SKU
    @NotBlank(message = "SKU é obrigatório")
    private String sku;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Size(max = 50, message = "Descrição deve ter o tamanho máximo de 50 caractéres")
    private String descricao;

    //private String foto;
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "999999.99",message = "O valor além do suportado")
    private BigDecimal valor;

    @NotNull(message = "O teor alcoólico deve ser informado")
    @DecimalMax(value = "100.00", message = "O valor informado deve ser menor que 100%")
    @DecimalMin(value = "0.00", message = "O valor informado nao pode ser negativo")
    @Column(name = "teor_alcoolico")
    private BigDecimal teorAlcoolico;

    @NotNull(message = "O valor da comissão deve ser informado")
    @DecimalMax(value = "100.00", message = "O valor informado deve ser menor que 100%")
    @DecimalMin(value = "0.00", message = "O valor informado nao pode ser negativo")
    private BigDecimal comissao;

    @Max(value = 9999, message = "A quantidade informada deve ser menor que 9.999")
    @Column(name = "quantidade_estoque")
    private  Integer quantidadeEstoque;

    @NotNull(message = "O sabor deve ser descrito")
    @Enumerated(EnumType.STRING)
    private Sabor sabor;

    @NotNull(message = "A origem deve ser informada")
    @Enumerated(EnumType.STRING)
    private Origem origem;

    @NotNull(message = "O estilo deve ser informado")
    @ManyToOne
    @JoinColumn(name = "codigo_estilo")
    private Estilo estilo;

    @Column(name = "img")
    private String nomeFoto;

    @Column(name = "content_type")
    private String contentType;

    @PrePersist
    @PreUpdate
    private void prePersistAndUpdate() {
        this.sku = this.sku.toUpperCase();
    }

    public String getFotoOrMock() {
        return  nomeFoto != null && !nomeFoto.isEmpty() ? this.nomeFoto : "cerveja-mock.png";
    }
}

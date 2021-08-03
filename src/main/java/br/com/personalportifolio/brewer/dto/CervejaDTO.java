package br.com.personalportifolio.brewer.dto;

import br.com.personalportifolio.brewer.model.Origem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CervejaDTO {

    private Long codigo;
    private String sku;
    private String nome;
    private String origem;
    private BigDecimal valor;
    private String foto;

    public CervejaDTO(Long codigo, String sku, String nome, Origem origem, BigDecimal valor, String foto) {
        this.codigo = codigo;
        this.sku = sku;
        this.nome = nome;
        this.origem = origem.name();
        this.valor = valor;
        this.foto = foto;
    }
}

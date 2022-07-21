package br.com.personalportifolio.brewer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemVenda implements Serializable {

    @EqualsAndHashCode.Include
    private Long codigo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Cerveja cerveja;

    public BigDecimal getValorTotal() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade.longValue()));
    }
}

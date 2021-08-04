package br.com.personalportifolio.brewer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemVenda {

    @EqualsAndHashCode.Include
    private Long codigo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private Cerveja cerveja;

    public BigDecimal getValorTotal() {
        return BigDecimal.valueOf(this.getQuantidade().doubleValue()).multiply(this.valorUnitario);
    }
}

package br.com.personalportifolio.brewer.venda;

import br.com.personalportifolio.brewer.model.ItemVenda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TabelaItensVenda {
    private List<ItemVenda> itens = new ArrayList<>();

    public BigDecimal getValorTotal() {
        return itens.stream().map(ItemVenda::getValorTotal)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO);
    }
}

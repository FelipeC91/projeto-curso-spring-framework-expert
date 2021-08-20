package br.com.personalportifolio.brewer.session;

import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.ItemVenda;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionScope
@Component
public class TabelaItensVenda {
    private List<ItemVenda> itens = new ArrayList<>();

    public BigDecimal getValorTotal() {
        return itens.stream().map(ItemVenda::getValorTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void adicionarItem(Cerveja cerveja, Integer quantidade) {
        var itemVendaOptional = searchItemByCerveja(cerveja);

        ItemVenda itemVenda = null;
        if (itemVendaOptional.isPresent()) {
            itemVenda = itemVendaOptional.get();
            itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade );
        } else {
            var itemNovo = new ItemVenda();
            itemNovo.setCerveja(cerveja);
            itemNovo.setQuantidade(quantidade);
            itemNovo.setValorUnitario(cerveja.getValor());

            itens.add(0, itemNovo);
        }

    }

    public void changeItemQuatity(Cerveja cerveja, Integer quantidade) {
        var itemVenda = searchItemByCerveja(cerveja).get();

        itemVenda.setQuantidade(quantidade);
    }

    private Optional<ItemVenda> searchItemByCerveja(Cerveja cerveja) {
        return itens.stream()
                .filter( i -> i.getCerveja().equals(cerveja) )
                .findAny();
    }

    public int getTotal() {
        return itens.size();
    }

    public List<ItemVenda> getItens() {
        return itens;
    }


}
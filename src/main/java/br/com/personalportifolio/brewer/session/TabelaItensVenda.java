package br.com.personalportifolio.brewer.session;

import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.ItemVenda;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class TabelaItensVenda {

    @EqualsAndHashCode.Include
    private String uuid;
    private List<ItemVenda> itens = new ArrayList<>();

    public TabelaItensVenda(String uuid) {
        this.uuid = uuid;
    }


    public BigDecimal getValorTotal() {
        return itens.stream()
        .map(ItemVenda::getValorTotal)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
    }            

    public void adicionarItem(Cerveja cerveja, Integer quantidade) {

        var itemVendaOptional = searchItemByCerveja(cerveja);

        ItemVenda itemVenda = null;
        if (itemVendaOptional.isPresent()) {
            itemVenda = itemVendaOptional.get();
            itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
            System.out.println("alter itemvenda " + itemVenda.toString() );

        } else {
            itemVenda = new ItemVenda();
            itemVenda.setCerveja(cerveja);
            itemVenda.setQuantidade(quantidade);
            itemVenda.setValorUnitario(cerveja.getValor());
            
            itens.add(0, itemVenda);
        }    
        System.out.println("new itemvenda " + itemVenda.toString());

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

    public void excluirItem(Cerveja cerveja) {
        int index = IntStream.range(0, itens.size())
                .filter(i -> itens.get(i).getCerveja().equals(cerveja))
                        .findAny().getAsInt();
        itens.remove(index);                
    }    

    public int getTotal() {
        return itens.size();
    }    

    public List<ItemVenda> getItens() {
        return itens;
    }    

    public void alterarQtdItens(Cerveja cerveja, int quantidade) {
        var itemVenda = searchItemByCerveja(cerveja).get();
        itemVenda.setQuantidade(quantidade);
    }    



}
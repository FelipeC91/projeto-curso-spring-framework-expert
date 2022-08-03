package br.com.personalportifolio.brewer.session;

import java.util.Set;
import java.util.List;

import java.util.HashSet;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItemsSession {
    private Set<TabelaItensVenda> tabelas = new HashSet<>();

    public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
        var tabelaItens = buscarTabelaPorId(uuid);

        tabelaItens.adicionarItem(cerveja, quantidade);
        tabelas.add(tabelaItens);
    }

    public void alterarQuantidade(String uuid, Cerveja cerveja, Integer quantidade) {
        var tabelaItens = buscarTabelaPorId(uuid);
        tabelaItens.alterarQtdItens(cerveja, quantidade);
    }

    public void excluirItem(String uuid, Cerveja cerveja) {
        var tabelaItens = buscarTabelaPorId(uuid);
        tabelaItens.excluirItem(cerveja);
    }

    public List<ItemVenda> getItens(String uuid) {
        return  buscarTabelaPorId(uuid).getItens();
    }
    
    private TabelaItensVenda buscarTabelaPorId(String uuid) {
        return tabelas.stream()
        .filter(t -> t.getUuid().equals(uuid))
        .findAny()
        .orElse(new TabelaItensVenda(uuid));
    } 

}
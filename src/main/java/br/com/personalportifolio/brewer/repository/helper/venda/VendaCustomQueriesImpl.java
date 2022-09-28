package br.com.personalportifolio.brewer.repository.helper.venda;

import br.com.personalportifolio.brewer.model.Venda;
import br.com.personalportifolio.brewer.repository.filter.VendaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class VendaCustomQueriesImpl implements VendaCustomQueries{
    @Override
    public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable) {
        return null;
    }
}

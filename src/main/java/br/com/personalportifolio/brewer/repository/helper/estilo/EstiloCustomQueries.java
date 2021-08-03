package br.com.personalportifolio.brewer.repository.helper.estilo;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.repository.filter.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstiloCustomQueries {

    public Page<Estilo> doFilter(EstiloFilter estiloFilter, Pageable pageable);


}

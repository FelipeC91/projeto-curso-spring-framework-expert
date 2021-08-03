package br.com.personalportifolio.brewer.repository.helper.cidade;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.repository.filter.CitySearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CidadeCustomQueries {

    Page<Cidade> filtrar(CitySearchFilter citySearchFilter, Pageable pageable);
}

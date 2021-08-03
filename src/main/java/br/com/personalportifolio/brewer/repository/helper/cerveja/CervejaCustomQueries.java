package br.com.personalportifolio.brewer.repository.helper.cerveja;

import br.com.personalportifolio.brewer.dto.CervejaDTO;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.repository.filter.CervejaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CervejaCustomQueries {
    public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable);

}

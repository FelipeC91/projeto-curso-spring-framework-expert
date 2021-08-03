package br.com.personalportifolio.brewer.repository.helper.customer;

import br.com.personalportifolio.brewer.model.Cliente;
import br.com.personalportifolio.brewer.repository.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteCustomQueries {

    Page<Cliente> filtrar(CustomerFilter customerFilter, Pageable pageable);
}

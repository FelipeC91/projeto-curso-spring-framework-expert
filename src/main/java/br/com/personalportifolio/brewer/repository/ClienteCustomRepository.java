package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.model.Cliente;
import br.com.personalportifolio.brewer.repository.helper.customer.ClienteCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteCustomRepository extends JpaRepository<Cliente, Long>, ClienteCustomQueries {

    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    List<Cliente> findByNomeStartingWithIgnoreCase(String name);
}

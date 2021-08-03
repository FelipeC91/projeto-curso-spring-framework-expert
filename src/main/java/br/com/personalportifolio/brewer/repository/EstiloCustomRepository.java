package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.repository.helper.estilo.EstiloCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstiloCustomRepository extends JpaRepository<Estilo, Long>, EstiloCustomQueries {

    Optional<Estilo> findByNomeIgnoreCase(String nome);
}

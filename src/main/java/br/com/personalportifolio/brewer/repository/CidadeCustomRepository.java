package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.model.Estado;
import br.com.personalportifolio.brewer.repository.helper.cidade.CidadeCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CidadeCustomRepository extends JpaRepository<Cidade, Long>, CidadeCustomQueries {

    List<Cidade> findByEstado_Codigo(Long codigoEstado);

    Optional<Cidade> findByNomeAndEstado_Codigo(String nome, Long estadoCodigo);

}

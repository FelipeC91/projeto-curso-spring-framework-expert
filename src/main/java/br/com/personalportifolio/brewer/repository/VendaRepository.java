package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.repository.helper.venda.VendaCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personalportifolio.brewer.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaCustomQueries {

}

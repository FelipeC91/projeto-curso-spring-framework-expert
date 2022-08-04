package br.com.personalportifolio.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personalportifolio.brewer.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}

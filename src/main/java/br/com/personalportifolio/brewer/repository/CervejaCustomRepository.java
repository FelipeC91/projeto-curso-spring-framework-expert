package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.dto.CervejaDTO;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.repository.helper.cerveja.CervejaCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CervejaCustomRepository extends JpaRepository<Cerveja, Long>, CervejaCustomQueries {

    @Query("SELECT new br.com.personalportifolio.brewer.dto.CervejaDTO(c.codigo, c.sku, c.nome, c.origem, c.valor, c.nomeFoto) FROM Cerveja AS c WHERE UPPER(c.sku) LIKE UPPER(:skuOrName) OR UPPER(c.nome) LIKE UPPER(:skuOrName)")
    public List<CervejaDTO> forSKUOrName(String skuOrName);

}

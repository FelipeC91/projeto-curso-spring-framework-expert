package br.com.personalportifolio.brewer.repository.filter;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.model.Origem;
import br.com.personalportifolio.brewer.model.Sabor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CervejaFilter {

    private String sku;

    private String nome;

    private Estilo estilo;

    private Sabor sabor;

    private Origem origem;

    private BigDecimal precoDe;
    private BigDecimal precoAte;
}

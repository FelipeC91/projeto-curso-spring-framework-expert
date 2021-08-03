package br.com.personalportifolio.brewer.repository.filter;


import br.com.personalportifolio.brewer.model.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CitySearchFilter {

    private Estado estado;

    private String nome;
}

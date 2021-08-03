package br.com.personalportifolio.brewer.repository.filter;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerFilter {

    private String nome;

    private String cpfCnpj;
}

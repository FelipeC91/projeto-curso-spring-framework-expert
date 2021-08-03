package br.com.personalportifolio.brewer.repository.filter;

import br.com.personalportifolio.brewer.model.Grupo;
import br.com.personalportifolio.brewer.repository.GrupoRepository;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioFilter {

    private String nome;

    private String email;

    private List<Grupo> grupos;
}

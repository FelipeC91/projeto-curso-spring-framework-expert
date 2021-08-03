package br.com.personalportifolio.brewer.repository.helper.usuario;

import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UsuarioCustomQueries {
    Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);

}

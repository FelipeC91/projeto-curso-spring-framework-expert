package br.com.personalportifolio.brewer.repository;

import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.repository.helper.usuario.UsuarioCustomQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioCustomQueries {

    Optional<Usuario> findByEmail(String email);

    //@Query("SELECT u FROM Usuario AS u WHERE lower(u.email) = lower(:email) AND u.ativo = true")
    Optional<Usuario> findUsuarioByEmailIgnoreCaseAndAtivoTrue(String email);

    @Query("SELECT DISTINCT p.nome FROM Usuario AS u INNER JOIN u.grupos AS g INNER JOIN g.permissoes AS p WHERE u = :usuario" )
    List<String> getGroupPermissions(Usuario usuario);

    void findByCodigoIn(Long[] codigos);

    List<Usuario> findByCodigoIn(List<Long> codigos);
}

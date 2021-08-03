package br.com.personalportifolio.brewer.configuration.security;

import br.com.personalportifolio.brewer.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UsuarioLogado extends User {
    private Usuario usuario;

    public UsuarioLogado(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

}

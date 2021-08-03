package br.com.personalportifolio.brewer.configuration.security;

import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("load user");
        var usuarioOptional = usuarioRepository.findUsuarioByEmailIgnoreCaseAndAtivoTrue(email);

        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Credenciais incorretas"));
        return new UsuarioLogado(usuario, getUserPermissions(usuario));
    }

    private Collection<? extends GrantedAuthority> getUserPermissions(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities = usuarioRepository.getGroupPermissions(usuario).stream().map(String::toUpperCase).peek(System.out::println)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return authorities;
    }
}

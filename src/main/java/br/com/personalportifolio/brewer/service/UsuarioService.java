package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.repository.UsuarioRepository;
import br.com.personalportifolio.brewer.service.exception.EmailAlreadyRegisteredException;
import br.com.personalportifolio.brewer.service.exception.MandatoryPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void save(Usuario usuario) {

        var existentUserOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if (existentUserOptional.isPresent())
            throw new EmailAlreadyRegisteredException("Email já cadastrado");

        if (usuario.isNovo() && usuario.getSenha().isEmpty())
            throw new MandatoryPasswordException("senha é obrigatória");



        if (usuario.isNovo()) {
            usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
            usuario.setConfirmacaoSenha(usuario.getSenha());
        }

        usuarioRepository.save(usuario);


    }

    @Transactional
    public void aterStatus(Long[] codigos, UsuarioStatus status) {
        status.executar(codigos, usuarioRepository);
    }
}

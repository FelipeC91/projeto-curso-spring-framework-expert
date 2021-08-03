package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.repository.UsuarioRepository;

import java.util.Arrays;

public enum UsuarioStatus {
    ATIVAR{
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByCodigoIn(Arrays.asList(codigos)).forEach(u -> u.setAtivo(true));

        }
    },
    DESATIVAR{
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByCodigoIn(Arrays.asList(codigos)).forEach(u -> u.setAtivo(false));

        }
    };

    public abstract void executar(Long[]codigos, UsuarioRepository usuarioRepository);
}

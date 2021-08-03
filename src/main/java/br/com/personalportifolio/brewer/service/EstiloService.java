package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.repository.EstiloCustomRepository;
import br.com.personalportifolio.brewer.service.exception.EstiloJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EstiloService {

    @Autowired
    private EstiloCustomRepository estiloCustomRepository;

    @Transactional
    public Estilo save(Estilo estilo) throws EstiloJaCadastradoException {
        Optional<Estilo> estiloOptional = estiloCustomRepository.findByNomeIgnoreCase(estilo.getNome());

        if (estiloOptional.isPresent())
            throw new EstiloJaCadastradoException(estilo.getNome() + " já está cadastrado");

        return estiloCustomRepository.saveAndFlush(estilo);
    }


}

package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.repository.CidadeCustomRepository;
import br.com.personalportifolio.brewer.service.exception.SameCidadeInEstadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeCustomRepository cidadeCustomRepository;

    @Transactional
    public void save(Cidade cidade) {
        Optional<Cidade> cidadeOptional = cidadeCustomRepository.findByNomeAndEstado_Codigo(cidade.getNome(), cidade.getEstado().getCodigo());
        System.out.println(cidadeOptional.isPresent());
        if (cidadeOptional.isPresent())
            throw new SameCidadeInEstadoException("Já existe uma cidade de mesmo nome associada a este estado");


        cidadeCustomRepository.save(cidade);
    }
}

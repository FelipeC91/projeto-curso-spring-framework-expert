package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.repository.CervejaCustomRepository;
import br.com.personalportifolio.brewer.service.event.cerveja.CervejaSalvaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CervejaService {

    @Autowired
    private CervejaCustomRepository cervejaCustomRepository;

    @Autowired
    private ApplicationEventPublisher publisher;


    @Transactional
    public void save(Cerveja cerveja) {
        cervejaCustomRepository.save(cerveja);

        publisher.publishEvent(new CervejaSalvaEvent(cerveja));
    }
}

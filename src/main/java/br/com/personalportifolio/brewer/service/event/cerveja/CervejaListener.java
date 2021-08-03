package br.com.personalportifolio.brewer.service.event.cerveja;

import br.com.personalportifolio.brewer.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CervejaListener {

    @Autowired
    private FotoStorage fotoStorage;

    @EventListener(condition = "#event.hasFoto()")
    public void cervejaSalva(CervejaSalvaEvent event) {

    }
}

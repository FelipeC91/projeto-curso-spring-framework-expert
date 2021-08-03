package br.com.personalportifolio.brewer.service.event.cerveja;

import br.com.personalportifolio.brewer.model.Cerveja;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CervejaSalvaEvent {

    private Cerveja cerveja;

    public boolean hasFoto() {
        return !cerveja.getNomeFoto().isEmpty();
    }


}

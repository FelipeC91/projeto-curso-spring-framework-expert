package br.com.personalportifolio.brewer.controller.converter;

import br.com.personalportifolio.brewer.model.Estilo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EstiloConverter implements Converter<String, Estilo> {

    @Override
    public Estilo convert(String source) {
        if (!source.isBlank()) {
            var estilo = new Estilo();
            estilo.setCodigo(Long.valueOf(source));
            return estilo;
        }

        return null;
    }
}

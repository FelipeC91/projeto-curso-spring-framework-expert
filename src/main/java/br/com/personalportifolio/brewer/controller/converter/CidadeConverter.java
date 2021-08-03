package br.com.personalportifolio.brewer.controller.converter;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.model.Estilo;
import org.springframework.core.convert.converter.Converter;

public class CidadeConverter implements Converter<String, Cidade> {

    @Override
    public Cidade convert(String source) {
        if (!source.isBlank()) {
            var cidade = new Cidade();
            cidade.setCodigo(Long.valueOf(source));
            return cidade;
        }

        return null;
    }

}

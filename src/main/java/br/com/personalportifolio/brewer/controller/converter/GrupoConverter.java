package br.com.personalportifolio.brewer.controller.converter;

import br.com.personalportifolio.brewer.model.Grupo;
import org.springframework.core.convert.converter.Converter;

public class GrupoConverter implements Converter<String, Grupo> {
    @Override
    public Grupo convert(String codigo) {
        if (!(codigo.isEmpty() || codigo.isBlank())) {
            var grupo = new Grupo();
            grupo.setCodigo(Long.valueOf(codigo));
            return grupo;
        }
        return null;
    }
}

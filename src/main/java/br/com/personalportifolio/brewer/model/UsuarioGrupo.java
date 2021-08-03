package br.com.personalportifolio.brewer.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "usuario_grupo")
public class UsuarioGrupo implements Serializable {

    @EmbeddedId
    private UsuarioGrupoID id;
}

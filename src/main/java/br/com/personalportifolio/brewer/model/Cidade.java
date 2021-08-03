package br.com.personalportifolio.brewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "cidade")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade implements Serializable {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;

    @NotBlank(message = "Nome da cidade é obrigatório")
    private String nome;

    @NotNull(message = "È obrigatório informar o estado")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "codigo_estado")
    @JsonIgnore
    private Estado estado;

    public boolean haveEstado() {
        return this.getEstado() != null;
    }

}

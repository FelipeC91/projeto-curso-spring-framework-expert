package br.com.personalportifolio.brewer.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class Endereco implements Serializable {

    private String logradouro;

    private String numero;

    private String complemento;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "codigo_cidade")
    private Cidade cidade;

    public String getFormattedCidadeAndEstadoUf() {
        return this.cidade != null ? this.cidade.getNome() + " - " + this.cidade.getEstado().getSigla() : null;
    }

}

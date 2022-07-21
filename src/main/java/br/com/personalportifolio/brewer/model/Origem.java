package br.com.personalportifolio.brewer.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public enum  Origem implements Serializable {
    NACIONAL("Nacional"),
    INTERNACIONAL("Internacional");

    String descricao;

    Origem(String descricao) {
        this.descricao = descricao;
    }
}

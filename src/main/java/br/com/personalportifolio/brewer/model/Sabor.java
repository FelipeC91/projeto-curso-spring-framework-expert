package br.com.personalportifolio.brewer.model;

import lombok.Getter;

@Getter
public enum Sabor {
    ADOCICADA("Adocicada"),
    AMARGA("Amarga"),
    FORTE("Suave"),
    FRUTADA("Frutada"),
    SUAVE("Suave");

    private String descricao;

    Sabor(String descricao) {
        this.descricao = descricao;
    }
}

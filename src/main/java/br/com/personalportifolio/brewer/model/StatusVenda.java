package br.com.personalportifolio.brewer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusVenda {
    ORCAMENTO("Orçamento"),
    EMITIDA("Emitida"),
    CANCELADA("Cancelada");

    private String descricao;
}

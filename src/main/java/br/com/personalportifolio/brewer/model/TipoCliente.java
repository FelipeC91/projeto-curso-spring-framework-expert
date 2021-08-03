package br.com.personalportifolio.brewer.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum TipoCliente implements Serializable {
    FISICA("Física", "CPF", "000.000.000-00") {
        @Override
        public String format(String cpfOuCnpj) {
            return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})","$1.$2.$3-");
        }
    },
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00") {
        @Override
        public String format(String cpfOuCnpj) {
            return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{4})","$1.$2.$3/$4-");
        }
    };

    TipoCliente(String description, String document, String mask) {
        this.description = description;
        this.document = document;
        this.mask = mask;
    }

    private String description;

    private String document;

    private String mask;

    public static String removeMask(String cpfCnpj) {
        return cpfCnpj.replaceAll("\\.|-|/", "");
    }

    public abstract String format(String cpfOuCnpj);
}

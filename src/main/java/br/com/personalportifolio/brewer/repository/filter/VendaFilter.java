package br.com.personalportifolio.brewer.repository.filter;

import br.com.personalportifolio.brewer.model.StatusVenda;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VendaFilter {
    private Long codigo;

    private StatusVenda statusVenda;
    private LocalDate desde;
    private LocalDate ate;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private  String nomeCliente;
    private String cpfOuCnpjCliente;

}

package br.com.personalportifolio.brewer.dto;

import br.com.personalportifolio.brewer.model.StatusVenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class VendaDTO {

    private Long codigo;
    private StatusVenda statusVenda;
    private LocalDate desde;
    private LocalDate ate;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private String nomeCliente;
    private String cpfOuCnpj;
}

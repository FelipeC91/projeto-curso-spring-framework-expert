package br.com.personalportifolio.brewer.venda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TabelaItensVendaTest {

    private TabelaItensVenda tabelaItensVenda;

    @BeforeEach
    public void serUp() {
        this.tabelaItensVenda = new TabelaItensVenda();
    }
    @Test
    public void expectCalcularValorTotalSemItens() {
         assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
    }
}

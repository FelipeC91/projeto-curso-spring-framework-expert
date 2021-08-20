package br.com.personalportifolio.brewer.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.Origem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

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

    @Test
    public void  exspectCalcularAdicaoDeItens() {
        var cerveja1 = new Cerveja();
        cerveja1.setCodigo(1L);
        cerveja1.setValor(BigDecimal.valueOf(8.90));


        var cerveja2 = new Cerveja();
        cerveja2.setCodigo(2L);
        cerveja2.setValor(BigDecimal.valueOf(4.99));

        this.tabelaItensVenda.adicionarItem(cerveja1, 1);
        this.tabelaItensVenda.adicionarItem(cerveja2, 2);


        assertEquals(BigDecimal.valueOf(18.880), tabelaItensVenda.getValorTotal().setScale(2));
    }

    @Test
    public void expectSameSizeOfListForSameBeers() {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);

        tabelaItensVenda.adicionarItem(c1,1);
        tabelaItensVenda.adicionarItem(c1,1);

        assertEquals(1, tabelaItensVenda.getTotal());
    }

    @Test
    public  void expextChangeItemQuantity() {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        c1.setValor(BigDecimal.valueOf(4.5));

        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.changeItemQuatity(c1,3);

        assertEquals(BigDecimal.valueOf(13.5).setScale(2), tabelaItensVenda.getValorTotal().setScale(2));

    }
}

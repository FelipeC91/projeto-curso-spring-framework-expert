package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.model.ItemVenda;
import br.com.personalportifolio.brewer.repository.CervejaCustomRepository;
import br.com.personalportifolio.brewer.session.TabelaItensVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private CervejaCustomRepository cervejaCustomRepository;

    @Autowired
    private TabelaItensVenda tabelaItensVenda;

    @GetMapping("/new")
    public ModelAndView getVendas() {
        return new ModelAndView("/sell/sell-new");
    }

    @PostMapping("/item")
    public @ResponseBody
    List<ItemVenda> addItem(Long codigo) {
        var cerveja = cervejaCustomRepository.findById(codigo);
        if (cerveja.isPresent()) {
            tabelaItensVenda.adicionarItem(cerveja.get(), 1);
            System.out.print(tabelaItensVenda.toString());
        } else
        System.out.print("total de itens " + tabelaItensVenda.getTotal() );

        return tabelaItensVenda.getItens();
    }

    @PutMapping("/item/{codigoCerveja}")
    public @ResponseBody List<ItemVenda> changeItemQuantity(@PathVariable Long codigoCerveja, Integer quantidade ) {
        var cerveja = cervejaCustomRepository.findById(codigoCerveja).get();
        tabelaItensVenda.changeItemQuatity(cerveja, quantidade);
        return tabelaItensVenda.getItens();
    }
}

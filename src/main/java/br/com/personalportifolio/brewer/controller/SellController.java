package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.model.ItemVenda;
import br.com.personalportifolio.brewer.repository.CervejaCustomRepository;
import br.com.personalportifolio.brewer.session.TabelaTtensSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private CervejaCustomRepository cervejaCustomRepository;

    @Autowired
    private TabelaTtensSession tabelaTtensSession;

    @GetMapping("/new")
    public ModelAndView getVendas() {
        var modelAndView = new ModelAndView("/sell/sell-new");

        var randomId = UUID.randomUUID().toString();
        modelAndView.addObject("uuid", randomId);

        return modelAndView;
    }

    @PostMapping("/item")
    public @ResponseBody
    List<ItemVenda> addItem(Long codigo, String uuid) {
        var cervejaOptional = cervejaCustomRepository.findById(codigo);
        if (cervejaOptional.isPresent()) {
            tabelaTtensSession.adicionarItem(uuid, cervejaOptional.get(), 1);
        }

        return tabelaTtensSession.getItens(uuid);
    }

    @PutMapping("/item/{codigoCerveja}")
    public @ResponseBody List<ItemVenda> changeItemQuantity(@PathVariable Long codigoCerveja,
                         Integer quantidade, String uuid) {
        var cerveja = cervejaCustomRepository.findById(codigoCerveja).get();
        tabelaTtensSession.alterarQuantidade(uuid, cerveja, quantidade);
        return tabelaTtensSession.getItens(uuid);
    }

    @DeleteMapping("/item/{uuid}/{codigoCerveja}")
    public @ResponseBody List<ItemVenda> excludeItem(@PathVariable String uuid,
                         @PathVariable Long codigoCerveja)  {

        var cerveja = cervejaCustomRepository.findById(codigoCerveja).get();
        tabelaTtensSession.excluirItem(uuid, cerveja);
         return tabelaTtensSession.getItens(uuid);
    }
}
package br.com.personalportifolio.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sell")
public class VendasController {

    @GetMapping("/new")
    public ModelAndView getVendas() {
        return new ModelAndView("/sell/sell-new");
    }
}

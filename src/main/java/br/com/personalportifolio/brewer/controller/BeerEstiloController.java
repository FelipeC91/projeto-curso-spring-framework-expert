package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.repository.EstiloCustomRepository;
import br.com.personalportifolio.brewer.repository.filter.CervejaFilter;
import br.com.personalportifolio.brewer.repository.filter.EstiloFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/estilo")
@Controller
public class BeerEstiloController {

    @Autowired
    private EstiloCustomRepository estiloCustomRepository;


    @GetMapping
    public ModelAndView search(EstiloFilter estiloFilter, BindingResult bindingResult, Pageable pageable,
                               HttpServletRequest httpServletRequest) {
        var modelAndView = new ModelAndView("/beer-style/beer-style-search");
        modelAndView.addObject("estiloFilter", estiloFilter);

        PageWrapper<Estilo> wrapperPage = new PageWrapper<>( estiloCustomRepository.doFilter(estiloFilter, pageable), httpServletRequest );
        modelAndView.addObject("wrapperPage", wrapperPage);
        return modelAndView;
    }
}

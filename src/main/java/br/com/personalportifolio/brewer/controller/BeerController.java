package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.dto.CervejaDTO;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.Origem;
import br.com.personalportifolio.brewer.model.Sabor;
import br.com.personalportifolio.brewer.repository.CervejaCustomRepository;
import br.com.personalportifolio.brewer.repository.EstiloCustomRepository;
import br.com.personalportifolio.brewer.repository.filter.CervejaFilter;
import br.com.personalportifolio.brewer.service.CervejaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/beer")
public class BeerController {

    @Autowired
    private EstiloCustomRepository estiloCustomRepository;

    @Autowired
    private CervejaService cervejaService;

    @Autowired
    private CervejaCustomRepository cervejaRepository;

    @PostMapping("/new")
    public ModelAndView register(@Valid Cerveja cerveja, BindingResult result,
                           RedirectAttributes attributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return newRegister(cerveja);
        }

        cervejaService.save(cerveja);
        attributes.addFlashAttribute("msg", "Cerveja salva com sucesso!");
        return new ModelAndView("redirect:/beer/new");
    }

    @GetMapping("/new")
    public ModelAndView newRegister(Cerveja cerveja) {

        var modelAndView = new ModelAndView("beer/beer-register");
        modelAndView.addObject("sabores", Sabor.values());
        modelAndView.addObject("estilos", estiloCustomRepository.findAll());
        modelAndView.addObject("origens", Origem.values());

        return modelAndView;
    }

    @GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult bindingResult, Pageable pageable,
                                  HttpServletRequest httpServletRequest) {
        var modelAndView = new ModelAndView("beer/beer-search");
        modelAndView.addObject("cervejaFilter", cervejaFilter);
        modelAndView.addObject("estilos", estiloCustomRepository.findAll());
        modelAndView.addObject("sabores", Sabor.values());
        modelAndView.addObject("origens", Origem.values());

        //modelAndView.addObject("cervejas", cervejaRepository.filtrar(cervejaFilter, pageable));

        PageWrapper<Cerveja> wrapperPage = new PageWrapper<>( cervejaRepository.filtrar(cervejaFilter, pageable), httpServletRequest );
        modelAndView.addObject("wrapperPage", wrapperPage);
        return modelAndView;

    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<CervejaDTO> pesquisarPorSkuOuNome(String skuOrName) {
        System.out.println(skuOrName);
        return cervejaRepository.forSKUOrName(skuOrName + "%");
    }
}

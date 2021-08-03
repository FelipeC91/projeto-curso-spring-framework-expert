package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.repository.CidadeCustomRepository;
import br.com.personalportifolio.brewer.repository.EstadoRepository;
import br.com.personalportifolio.brewer.repository.filter.CitySearchFilter;
import br.com.personalportifolio.brewer.service.CidadeService;
import br.com.personalportifolio.brewer.service.exception.SameCidadeInEstadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CidadeCustomRepository cidadeCustomRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeService cidadeService;

    @Cacheable(value = "cidades", key = "#codigoEstado")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> find(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cidadeCustomRepository.findByEstado_Codigo(codigoEstado);
    }

    @GetMapping("/new")
    public ModelAndView newCity(Cidade cidade) {
        var modelAndView = new ModelAndView("/city/city-register");
        modelAndView.addObject("estados", estadoRepository.findAll());
        return modelAndView;
    }

    @CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.haveEstado()")
    @PostMapping("/new")
    public ModelAndView saveCity(@Valid  Cidade cidade, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return newCity(cidade);
        }

        try {
            cidadeService.save(cidade);
        } catch (SameCidadeInEstadoException e) {
            bindingResult.rejectValue("estado", e.getMessage());
            return newCity(cidade);
        }

        redirectAttributes.addFlashAttribute("msg", "Cidade cadastrada com sucesso!");

        return new ModelAndView("redirect:/city/new");
    }

    @GetMapping("/search")
    public ModelAndView search(CitySearchFilter citySearchFilter, Pageable pageable,
                                HttpServletRequest httpServletRequest) {
        var modelAndView = new ModelAndView("/city/city-search");
        modelAndView.addObject("estados", estadoRepository.findAll());
        modelAndView.addObject("citySearchFilter", citySearchFilter);

        PageWrapper<Cidade> cidadePageWrapper = new PageWrapper<>(cidadeCustomRepository.filtrar(citySearchFilter, pageable), httpServletRequest);
        modelAndView.addObject("wrapperPage", cidadePageWrapper);
        return modelAndView;
    }
}

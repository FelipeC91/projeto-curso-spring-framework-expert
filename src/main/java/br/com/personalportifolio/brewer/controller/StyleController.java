package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.service.EstiloService;
import br.com.personalportifolio.brewer.service.exception.EstiloJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/style")
public class StyleController {

    @Autowired
    private EstiloService estiloService;

    @PostMapping("/new")
    public ModelAndView estilo(@Valid Estilo estilo, BindingResult result,
                               RedirectAttributes attributes) {

        if (result.hasErrors())
            return newEstilo(estilo);

        try {

            estiloService.save(estilo);
        } catch (EstiloJaCadastradoException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return newEstilo(estilo);
        }

        attributes.addFlashAttribute("msg", "Estilo salvo com sucesso!");
        return new ModelAndView("redirect:/style/new");
    }

    @GetMapping("/new")
    public ModelAndView newEstilo(Estilo estilo) {
        var modelAndView = new ModelAndView("style/beer-style-register");
        modelAndView.addObject("estilo", estilo);

        return modelAndView;
    }

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> addStyle(@RequestBody @Valid Estilo estilo, BindingResult result) {

        if (result.hasErrors())
            return ResponseEntity.accepted().body(result.getFieldError("nome").getDefaultMessage());

        try {
                estilo = estiloService.save(estilo);
        }catch (EstiloJaCadastradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(estilo);
    }
}

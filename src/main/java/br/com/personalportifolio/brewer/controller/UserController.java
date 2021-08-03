package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.repository.GrupoRepository;
import br.com.personalportifolio.brewer.repository.UsuarioRepository;
import br.com.personalportifolio.brewer.repository.filter.UsuarioFilter;
import br.com.personalportifolio.brewer.service.UsuarioService;
import br.com.personalportifolio.brewer.service.UsuarioStatus;
import br.com.personalportifolio.brewer.service.exception.EmailAlreadyRegisteredException;
import br.com.personalportifolio.brewer.service.exception.MandatoryPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/new")
    public ModelAndView newUser(Usuario usuario) {
        var modelAndView = new ModelAndView("/user/user-register");
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("grupos", grupoRepository.findAll());

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView saveUser(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return newUser(usuario);
        }

        try {
            usuarioService.save(usuario);
        } catch (EmailAlreadyRegisteredException e) {
            bindingResult.rejectValue("email", e.getMessage());
            return newUser(usuario);
        } catch (MandatoryPasswordException e) {
            bindingResult.rejectValue("senha", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("msg", "Cliente salvo com sucesso!");
        return new ModelAndView("redirect:/user/new");

    }

    @GetMapping
    public ModelAndView getSearchPage(UsuarioFilter usuarioFilter, BindingResult bindingResult, Pageable pageable,
                                      HttpServletRequest httpServletRequest) {
        var modelAndView = new ModelAndView("/user/user-search");
        modelAndView.addObject("usuarioFilter", usuarioFilter);
        modelAndView.addObject("grupos", grupoRepository.findAll());
//        modelAndView.addObject("usuarios", usuarioRepository.filtrar(usuarioFilter));
        PageWrapper<Usuario> wrapperPage = new PageWrapper<>( usuarioRepository.filtrar(usuarioFilter, pageable), httpServletRequest );
        modelAndView.addObject("wrapperPage", wrapperPage);
        return modelAndView;
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public  void updateStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam UsuarioStatus status) {
        var codigosList = Arrays.asList(codigos);
        codigosList.forEach(System.out::println);

        usuarioService.aterStatus(codigos, status);
    }
}

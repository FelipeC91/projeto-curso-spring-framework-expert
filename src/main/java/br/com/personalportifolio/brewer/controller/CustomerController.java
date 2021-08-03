package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.model.Cliente;
import br.com.personalportifolio.brewer.model.TipoCliente;
import br.com.personalportifolio.brewer.repository.ClienteCustomRepository;
import br.com.personalportifolio.brewer.repository.EstadoRepository;
import br.com.personalportifolio.brewer.repository.filter.CustomerFilter;
import br.com.personalportifolio.brewer.service.ClienteService;
import br.com.personalportifolio.brewer.service.ExistentClientException;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/customer")
@Controller
public class CustomerController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteCustomRepository clienteCustomRepository;

    @GetMapping("/new")
    public ModelAndView newCustomer(Cliente cliente) {
        var modelAndView = new ModelAndView("customer/customer-register");
        modelAndView.addObject("tiposCliente", TipoCliente.values());
        modelAndView.addObject("estados", estadoRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView createCustumer(@Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(cliente.toString());
            return newCustomer(cliente);
        }

        try {

            clienteService.save(cliente);
        } catch (ExistentClientException e) {
            bindingResult.rejectValue("cpfCnpj", e.getMessage());
            return newCustomer(cliente);
        }

        redirectAttributes.addFlashAttribute("msg", "Cliente salvo com sucesso!");
        return new ModelAndView("redirect:/customer/new");
    }

    @GetMapping("/search")
    public ModelAndView dispatchSearch(CustomerFilter customerFilter, BindingResult bindingResult, Pageable pageable,
                                       HttpServletRequest httpServletRequest) {
        var modelAndView = new ModelAndView("customer/customer-search");
        modelAndView.addObject("customerFilter", customerFilter);

        PageWrapper<Cliente> wrapperPage = new PageWrapper<>(clienteCustomRepository.filtrar(customerFilter, pageable), httpServletRequest);
        modelAndView.addObject("wrapperPage", wrapperPage);
        return modelAndView;
    }

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody List<Cliente> searchCustomers(String name) {
        return clienteCustomRepository.findByNomeStartingWithIgnoreCase(name);
    }
}



package br.com.personalportifolio.brewer.controller;


import br.com.personalportifolio.brewer.configuration.security.UsuarioLogado;
import br.com.personalportifolio.brewer.controller.page.PageWrapper;
import br.com.personalportifolio.brewer.model.ItemVenda;
import br.com.personalportifolio.brewer.model.StatusVenda;
import br.com.personalportifolio.brewer.model.Venda;
import br.com.personalportifolio.brewer.repository.CervejaCustomRepository;
import br.com.personalportifolio.brewer.repository.VendaRepository;
import br.com.personalportifolio.brewer.repository.filter.VendaFilter;
import br.com.personalportifolio.brewer.service.VendaService;
import br.com.personalportifolio.brewer.session.TabelaItemsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/sell")
public class SellController {

    @Autowired
    private CervejaCustomRepository cervejaCustomRepository;

    @Autowired
    private TabelaItemsSession tabelaItensSession;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private SellValidator sellValidator;

    @InitBinder
    private void setUpValidator(WebDataBinder binder) {
        binder.setValidator(sellValidator);
    }

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping("/new")
    public ModelAndView novaVenda(Venda venda) {

        var modelAndView = new ModelAndView("/sell/sell-new");

        if (StringUtils.isEmpty(venda.getUuid())) {
            var randomId = UUID.randomUUID().toString();
            venda.setUuid(randomId);
        }

        modelAndView.addObject("venda", venda);
        modelAndView.addObject("itens", venda.getItensVenda());

        return modelAndView;
    }

    @PostMapping(value = "/new", params = "salvar")
    public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UsuarioLogado usuario) {

        validarVenda(venda, result);
        if (result.hasErrors()) {
            return novaVenda(venda);
        }

        System.out.println(usuario);
        venda.setUsuario(usuario.getUsuario());


        vendaService.save(venda);
        redirectAttributes.addFlashAttribute("msg", "Venda salva com sucesso!");
        return new ModelAndView("redirect:/sell/new");
    }

    @PostMapping(value = "/new", params = "emitir")
    public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UsuarioLogado usuario) {
        validarVenda(venda, result);
        if (result.hasErrors()) {
            return novaVenda(venda);
        }

        venda.setUsuario(usuario.getUsuario());
        vendaService.emitir(venda);
        redirectAttributes.addFlashAttribute("msg", "Venda emitida com sucesso!");
        return new ModelAndView("redirect:/sell/new");

    }

    @PostMapping(value = "/new", params = "enviarEmail")
    public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal UsuarioLogado usuario) {
        validarVenda(venda, result);
        if (result.hasErrors()) {
            return novaVenda(venda);
        }

        venda.setUsuario(usuario.getUsuario());

//        vendaService.enviarEmail(venda);
        redirectAttributes.addFlashAttribute("msg", "Venda salva e email enviado com sucesso!");
        return new ModelAndView("redirect:/sell/new");

    }

    private void validarVenda(Venda venda, BindingResult result) {
        List<ItemVenda> itensVenda = tabelaItensSession.getItens(venda.getUuid());
//        venda.adicionarItens(itensVenda);
        venda.setItensVenda(itensVenda);
        venda.calcValorTotal();

        sellValidator.validate(venda, result); // equivale a @Valid param
    }



    @PostMapping("/item")
    public @ResponseBody List<ItemVenda> addItem(Long codigo, String uuid) {


        var cervejaOptional = cervejaCustomRepository.findById(codigo);
        if (cervejaOptional.isPresent()) {
            tabelaItensSession.adicionarItem(uuid, cervejaOptional.get(), 1);
        }

        return tabelaItensSession.getItens(uuid);
    }

    @PutMapping("/item/{codigoCerveja}")
    public @ResponseBody List<ItemVenda> changeItemQuantity(@PathVariable Long codigoCerveja,
                                                            Integer quantidade, String uuid) {

        var cerveja = cervejaCustomRepository.findById(codigoCerveja).get();
        tabelaItensSession.alterarQuantidade(uuid, cerveja, quantidade);
        return tabelaItensSession.getItens(uuid);
    }

    @DeleteMapping("/item/{uuid}/{codigoCerveja}")
    public @ResponseBody List<ItemVenda> excludeItem(@PathVariable String uuid,
                                                     @PathVariable Long codigoCerveja) {

        var cerveja = cervejaCustomRepository.findById(codigoCerveja).get();
        tabelaItensSession.excluirItem(uuid, cerveja);
        return tabelaItensSession.getItens(uuid);
    }

    @GetMapping
    public ModelAndView pesquisar(VendaFilter vendaFilter, BindingResult bindingResult, Pageable pageable, HttpServletRequest httpServletRequest)  {
        var modelAndView = new ModelAndView("sell/sell-search");
        modelAndView.addObject(vendaFilter);
        modelAndView.addObject("statusVendaValues", StatusVenda.values());

        PageWrapper<Venda> wrapperPage = new PageWrapper<>( vendaRepository.filtrar(vendaFilter, pageable), httpServletRequest );
        modelAndView.addObject("wrapperPage", wrapperPage);
        return modelAndView;

    }
}
package br.com.personalportifolio.brewer.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.personalportifolio.brewer.model.Venda;

import java.math.BigDecimal;

@Component
public class SellValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Venda.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");

        var venda = (Venda) target;

        validarSeSubmeteuApenasHorarioEntrega(venda, errors);
        validarSeHaItensNaVenda(venda, errors);
        validarSevalorTotalnegativo(errors, venda);

    }

    private static void validarSevalorTotalnegativo(Errors errors, Venda venda) {
        if (venda.calcValorTotal().compareTo(BigDecimal.ZERO) < 0){
            errors.rejectValue("", "valor total não pode ser negativo");
        }
    }

    private void validarSeSubmeteuApenasHorarioEntrega(Venda venda, Errors errors) {

        if (venda.getHorarioEntrega() != null && venda.getDataEntrega() == null) {
            errors.rejectValue("dataEntrega", "", "Informe uma data  váçida");
        }
    }

    private void validarSeHaItensNaVenda(Venda venda, Errors errors) {

        if (venda.getItensVenda().isEmpty())
            errors.reject("", "Insira ao menos uma cerveja para esta venda");
    }

}

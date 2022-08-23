package br.com.personalportifolio.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.personalportifolio.brewer.model.ItemVenda;
import br.com.personalportifolio.brewer.model.Venda;
import br.com.personalportifolio.brewer.repository.VendaRepository;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Transactional
    public void save(Venda venda) {

        if (venda.isNova()) {
            venda.setDataCriacao(LocalDateTime.now());
        }

        var valorTotal = venda.calcValorTotal();

        venda.setValorTotal(valorTotal);

        if (venda.getDataEntrega() != null) {
                venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(),
                        venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
        }

        vendaRepository.save(venda);

    }


}
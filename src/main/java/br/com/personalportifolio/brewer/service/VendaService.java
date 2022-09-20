package br.com.personalportifolio.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.personalportifolio.brewer.model.StatusVenda;
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


        if (venda.getDataEntrega() != null) {
                venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(),
                        venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
        }
        System.out.println("======================================================================");
        venda.getItensVenda().stream().forEach(itemVenda -> itemVenda.setVenda(venda));
        vendaRepository.save(venda);

    }

    @Transactional
    public void emitir(Venda venda) {
        venda.setStatus(StatusVenda.EMITIDA);

        save(venda);
    }
}
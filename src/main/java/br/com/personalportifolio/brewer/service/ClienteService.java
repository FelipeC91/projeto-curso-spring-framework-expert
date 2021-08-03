package br.com.personalportifolio.brewer.service;

import br.com.personalportifolio.brewer.model.Cliente;
import br.com.personalportifolio.brewer.repository.ClienteCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteCustomRepository clienteCustomRepository;

    @Transactional
    public void save(Cliente cliente) {
        Optional<Cliente> exsistentClient = clienteCustomRepository.findByCpfCnpj(cliente.getNoFormatedCpfCnpj() );

        if (exsistentClient.isPresent()) {
            throw new ExistentClientException("CPF/CNPJ já cadastrado");
        }
        clienteCustomRepository.save(cliente);
    }
}

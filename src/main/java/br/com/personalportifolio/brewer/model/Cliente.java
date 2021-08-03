package br.com.personalportifolio.brewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "O nome do cliente é requerido obrigatoriamente")
    private String nome;

    @NotNull(message = "A definição do tipo de cliente é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private TipoCliente tipoCliente;

    @NotBlank(message = "O nome do CPF/CNPJ é requerido obrigatoriamente")
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    private String telefone;

    @Email(message = "Este email não foi considerado válido")
    private String email;


    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @PreUpdate
    @PrePersist
    private void prePersistOrUpdate() {
        this.cpfCnpj = TipoCliente.removeMask(this.getCpfCnpj());
    }

    @PostLoad
    public void getFormattedCpfCnpj() {
        if (this.tipoCliente.equals(TipoCliente.FISICA))
            this.cpfCnpj = TipoCliente.FISICA.format(cpfCnpj);
        else
            this.cpfCnpj = TipoCliente.JURIDICA.format(cpfCnpj);
    }

    public String getNoFormatedCpfCnpj() {
        return TipoCliente.removeMask(cpfCnpj);
    }


}

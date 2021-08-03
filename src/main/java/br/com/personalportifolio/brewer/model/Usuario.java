package br.com.personalportifolio.brewer.model;

import br.com.personalportifolio.brewer.validation.PasswordComparator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@PasswordComparator(comparable = "senha", otherComparable = "confirmacaoSenha", message = "Comparação da senha não confere")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
public class Usuario implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "O campo nome é " +
            "obrigatório")
    private String nome;

    @NotBlank(message = "O campo e-mail é obrigatório")
    @Email(message = "Insira um padrao de e-mail válido")
    private String email;

    private String senha;

    @Transient
    private String confirmacaoSenha;

    private boolean ativo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_nascimento")
    //@NotNull(message = "O campo data de nascimento é obrigatório")
    private LocalDate dataNascimento;

    @Size(min = 1, message = "Pelo menos um grupo deve ser selecionado")
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
    private List<Grupo> grupos;

    public boolean isNovo() {
        return this.codigo == null;
    }

    public List<String> getGroupNames() {
        return this.grupos.stream().map(Grupo::getNome).collect(Collectors.toList());
    }

    @PreUpdate
    public void validatePasswordConstraint() {
        this.confirmacaoSenha = this.senha;
    }
}

package com.rs.mb.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "USUARIO")
@NoArgsConstructor
public class Usuario {

    @Id
    @NotNull
    @Column(name = "NOME")
    private String nome;


    @Generated(GenerationTime.INSERT)
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "DT_CADASTRO")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Mensagem> mensagens;

    public Usuario(String nome) {
        this.nome = nome;
    }
}

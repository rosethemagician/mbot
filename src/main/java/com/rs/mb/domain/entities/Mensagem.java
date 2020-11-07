package com.rs.mb.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "MENSAGEM")
@NoArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENSAGEM_SEQ")
    @SequenceGenerator(name = "MENSAGEM_SEQ", sequenceName = "MENSAGEM_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "CONTEUDO")
    private String conteudo;

    @NotNull
    @Column(name = "DT_ENVIO")
    private LocalDateTime dataEnvio;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "NOME_USUARIO")
    private Usuario usuario;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "NOME_CANAL")
    private Canal canal;

    public Mensagem(String conteudo, LocalDateTime dataEnvio, Usuario usuario, Canal canal) {
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.usuario = usuario;
        this.canal = canal;
    }
}

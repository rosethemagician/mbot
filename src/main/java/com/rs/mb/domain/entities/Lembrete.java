package com.rs.mb.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "LEMBRETE")
@NoArgsConstructor
public class Lembrete {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEMBRETE_SEQ")
    @SequenceGenerator(name = "LEMBRETE_SEQ", sequenceName = "LEMBRETE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "CONTEUDO")
    private String conteudo;

    @NotNull
    @Column(name = "DT_CRIACAO")
    private LocalDateTime dataCriacao;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATARIO")
    private Usuario destinatario;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "REMETENTE")
    private Usuario remetente;
}

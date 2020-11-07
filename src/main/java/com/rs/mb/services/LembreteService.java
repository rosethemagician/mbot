package com.rs.mb.services;

import com.rs.mb.domain.entities.Lembrete;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.repositories.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LembreteService {

    private final LembreteRepository lembreteRepository;


    public List<Lembrete> findAll() {
        return this.lembreteRepository.findAll();
    }

    public void deleteAll(List<Lembrete> lembretesDoUsuario) {
        this.lembreteRepository.deleteAll(lembretesDoUsuario);
    }

    public void save(Lembrete lembrete) {
        this.lembreteRepository.save(lembrete);
    }

    public Long countAllByUser(Usuario user) {
        return this.lembreteRepository.countAllByDestinatario(user);
    }
}

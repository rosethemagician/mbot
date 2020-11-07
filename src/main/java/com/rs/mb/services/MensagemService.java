package com.rs.mb.services;

import com.rs.mb.domain.entities.Canal;
import com.rs.mb.domain.entities.Mensagem;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.repositories.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    private final CanalService canalService;

    @Autowired
    private List<Canal> canais;

    private List<Mensagem> messagesToSave;

    @Async
    public void prepareToSave(String channel, Usuario user, String message) {
        initializeThreadSafeList();

        Optional<Canal> canal = canais.stream().filter(c -> c.getNome().equalsIgnoreCase(channel)).findFirst();

        if (canal.isPresent()) {

            Mensagem mensagem = new Mensagem(message, LocalDateTime.now(), user, canal.get());
            this.messagesToSave.add(mensagem);
        } else {

            Canal newChannel = new Canal(channel, LocalDateTime.now());
            Canal savedChannel = this.canalService.save(newChannel);
            this.canais.add(savedChannel);
        }
    }

    @Scheduled(fixedDelay=10000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void save() {
        initializeThreadSafeList();
        List<Mensagem> messagesCopy = new ArrayList<>(messagesToSave);
        this.messagesToSave.clear();
        this.mensagemRepository.saveAll(messagesCopy);
    }

    private void initializeThreadSafeList() {
        if (messagesToSave == null) {
            messagesToSave = Collections.synchronizedList(new ArrayList<>());
        }
    }

    public Optional<Mensagem> fetchLastSeen(String target) {
        return this.mensagemRepository.fetchLastSeen(target);
    }

    public List<Mensagem> findAllByChannel(String sourceId) {
        return this.mensagemRepository.findAllByCanalNome(sourceId);
    }

    public List<Mensagem> findAllByUserChannelNome(Usuario user, String sourceId) {
        return this.mensagemRepository.findAllByUsuarioAndCanalNome(user, sourceId);
    }
}

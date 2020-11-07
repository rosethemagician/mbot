package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.rs.mb.domain.entities.Lembrete;
import com.rs.mb.domain.entities.Mensagem;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.LembreteService;
import com.rs.mb.services.MensagemService;
import com.rs.mb.utils.MbUtilsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Remind extends CommandEvent {

    private ChannelMessageEvent event;

    @Autowired
    private LembreteService lembreteService;

    @Autowired
    private List<Lembrete> lembretes;

    @Autowired
    private ConcurrentHashMap<String, String> usuariosPendentes;

    @Override
    public void initialize(CommandSource source, String sourceId, Usuario user, String commandPrefix, String command, Set<CommandPermission> permissions, TwitchChat twitchChat, ChannelMessageEvent event) {
        this.setSource(source);
        this.setSourceId(sourceId);
        this.setUser(user);
        this.setCommandPrefix(commandPrefix);
        this.setCommand(command);
        this.setPermissions(permissions);
        this.setTwitchChat(twitchChat);
        this.event = event;

        remind();
    }

    public void remind() {
        String[] tokens = this.getCommand().split(" ", 3);

        if (tokens.length > 2) {

            String nomeDestinatario = tokens[1].toLowerCase();

            Lembrete lembrete = new Lembrete();
            Usuario destinatario = new Usuario();
            destinatario.setNome(nomeDestinatario);

            Long qtdLembretes = this.lembreteService.countAllByUser(destinatario);

            if (qtdLembretes > 3) {
                this.respondToUser("that users has already too many reminders :/");
                return;
            }

            lembrete.setDestinatario(destinatario);
            lembrete.setRemetente(this.getUser());
            lembrete.setConteudo(tokens[2]);
            lembrete.setDataCriacao(LocalDateTime.now());

            this.lembreteService.save(lembrete);

            usuariosPendentes.put(destinatario.getNome(), destinatario.getNome());
            lembretes.add(lembrete);

            this.respondToUser("reminder created with success :)");

        }

    }
}

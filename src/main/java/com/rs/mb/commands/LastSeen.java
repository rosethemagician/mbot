package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.rs.mb.MagicBotProperties;
import com.rs.mb.domain.entities.Mensagem;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.MensagemService;
import com.rs.mb.utils.MbUtilsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class LastSeen extends CommandEvent {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private MagicBotProperties magicBotProperties;

    private ChannelMessageEvent event;

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

        if (magicBotProperties.getOwner().equalsIgnoreCase(user.getNome())) {
            lastSeen();
        }
    }

    public void lastSeen() {
        String[] tokens = this.getCommand().split(" ");

        if (tokens == null || tokens.length == 1) {
            this.respondToUser("missing parameter {target}");
            return;
        }

        String username = tokens[1].toLowerCase();

        Optional<Mensagem> optionalMensagem = this.mensagemService.fetchLastSeen(username);
        if (!optionalMensagem.isPresent()) {
            this.respondToUser("user not found");
            return;
        }

        Mensagem mensagem = optionalMensagem.get();

        String lastSeenDate = MbUtilsData.generateLastSeenDate(mensagem.getDataEnvio());

        //
        this.respondToUser(username + " was last seen " + lastSeenDate + " in " + mensagem.getCanal().getNome() + " chat: " + mensagem.getConteudo());
    }
}


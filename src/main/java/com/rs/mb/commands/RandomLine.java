package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.rs.mb.domain.entities.Mensagem;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.MensagemService;
import com.rs.mb.utils.MbUtilsData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class RandomLine extends CommandEvent {

    private ChannelMessageEvent event;

    @Autowired
    private MensagemService mensagemService;

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

        randomLine();
    }

    public void randomLine() {
        List<Mensagem> mensagens = this.mensagemService.findAllByChannel(this.getSourceId());

        Mensagem randomMsg = mensagens.get(new Random().nextInt(mensagens.size()));

        String lastSeenString = MbUtilsData.generateLastSeenDate(randomMsg.getDataEnvio());

        this.respondToUser("[ " + lastSeenString + " ] " + randomMsg.getUsuario().getNome() + ": " + StringUtils.abbreviate(randomMsg.getConteudo(), 170));

    }
}

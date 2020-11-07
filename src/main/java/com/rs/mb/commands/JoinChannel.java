package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.rs.mb.domain.entities.Canal;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.CanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class JoinChannel extends CommandEvent {

    private ChannelMessageEvent event;

    @Autowired
    private CanalService canalService;

    @Value("${magicbot.owner}")
    private String owner;

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

        if (user.getNome().equalsIgnoreCase(owner)) {
            joinChannel();
        }
    }

    public void joinChannel() {
        String tokens[] = this.getCommand().split(" ");

        if (tokens == null || tokens.length == 1) {
            this.respondToUser("missing parameter {channel}");
            return;
        }

        String nomeCanal = tokens[1].toLowerCase();

        Canal canal = new Canal(nomeCanal, LocalDateTime.now());

        this.canalService.save(canal);

        this.getTwitchChat().joinChannel(nomeCanal);
    }
}

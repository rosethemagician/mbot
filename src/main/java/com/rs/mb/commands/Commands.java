package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.rs.mb.domain.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Commands extends CommandEvent {

    private ChannelMessageEvent event;

    @Override
    public void initialize(CommandSource source, String sourceId, Usuario user, String commandPrefix, String command, Set<CommandPermission> permissions, TwitchChat twitchChat, ChannelMessageEvent event) {
        this.setSource(CommandSource.PRIVATE_MESSAGE);
        this.setSourceId(sourceId);
        this.setUser(user);
        this.setCommandPrefix(commandPrefix);
        this.setCommand(command);
        this.setPermissions(permissions);
        this.setTwitchChat(twitchChat);
        this.event = event;

        commands();
    }

    public void commands() {
        this.getTwitchChat().sendMessage(this.getSourceId(), "check whispers");
        this.getTwitchChat().sendMessage(this.getSourceId(), "/w " + this.getUser().getNome() + " ping, userid, lastseen, join, rl, rq, notify/remind, help");
    }
}

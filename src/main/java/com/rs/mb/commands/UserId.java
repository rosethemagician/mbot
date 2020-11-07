package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.github.twitch4j.common.events.domain.EventUser;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class UserId extends CommandEvent {

    private ChannelMessageEvent event;

    @Autowired
    private UsuarioService usuarioService;

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

        userId();
    }

    public void userId() {
        Optional<Usuario> usuario = this.usuarioService.findByNome(this.getUser().getNome());

        if (usuario.isPresent()) {
            this.respondToUser(this.getUser().getNome() +  ", your internal ID is : " + usuario.get().getId());
            return;
        }

        this.respondToUser(this.getUser().getNome() +  ", could not find your ID, this probably should not have happened :(");
    }
}

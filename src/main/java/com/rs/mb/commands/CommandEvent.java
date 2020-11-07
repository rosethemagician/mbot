package com.rs.mb.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.github.twitch4j.common.events.TwitchEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import com.rs.mb.dispatchers.MessageDispatcher;
import com.rs.mb.domain.entities.Usuario;

import java.util.Set;

public abstract class CommandEvent extends TwitchEvent {
    private CommandSource source;
    private String sourceId;
    private Usuario user;
    private String commandPrefix;
    private String command;
    private Set<CommandPermission> permissions;
    private TwitchChat twitchChat;

    public CommandEvent() {

    }

    public abstract void initialize(CommandSource source, String sourceId, Usuario user, String commandPrefix, String command, Set<CommandPermission> permissions, TwitchChat twitchChat, ChannelMessageEvent event);

    public void respondToUser(String message) {
        if (this.source.equals(CommandSource.CHANNEL)) {
            this.getTwitchChat().sendMessage(this.sourceId, message);
        } else if (this.source.equals(CommandSource.PRIVATE_MESSAGE)) {
            this.getTwitchChat().sendMessage(this.sourceId, "/w " + this.sourceId + " " + message);
        }

    }

    public String toString() {
        return "CommandEvent(source=" + this.getSource() + ", sourceId=" + this.getSourceId() + ", user=" + this.getUser() + ", commandPrefix=" + this.getCommandPrefix() + ", command=" + this.getCommand() + ", permissions=" + this.getPermissions() + ")";
    }

    public CommandSource getSource() {
        return this.source;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public Usuario getUser() {
        return this.user;
    }

    public String getCommandPrefix() {
        return this.commandPrefix;
    }

    public String getCommand() {
        return this.command;
    }

    public Set<CommandPermission> getPermissions() {
        return this.permissions;
    }

    public TwitchChat getTwitchChat() {
        return this.twitchChat;
    }

    public void setSource(CommandSource source) {
        this.source = source;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setPermissions(Set<CommandPermission> permissions) {
        this.permissions = permissions;
    }

    public void setTwitchChat(TwitchChat twitchChat) {
        this.twitchChat = twitchChat;
    }
}

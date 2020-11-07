package com.rs.mb.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.enums.CommandSource;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.user.PrivateMessageEvent;
import com.rs.mb.MagicBotProperties;
import com.rs.mb.commands.*;
import com.rs.mb.dispatchers.MessageDispatcher;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.message.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageHandler {

    @Autowired
    private MagicBotProperties magicBotProperties;

    @Autowired
    private TwitchClient twitchClient;

    @Autowired
    private GenericMessage genericMessage;

    @Autowired
    private Ping ping;

    @Autowired
    private UserId userId;

    @Autowired
    private LastSeen lastSeen;

    @Autowired
    private JoinChannel joinChannel;

    @Autowired
    private RandomLine randomLine;

    @Autowired
    private RandomQuote randomQuote;

    @Autowired
    private Remind remind;

    @Autowired
    private Help help;

    @Autowired
    private Commands commands;


    public MessageHandler(SimpleEventHandler eventHandler) {
        try {
            eventHandler.onEvent(ChannelMessageEvent.class, event -> onChannelMessage(event));
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public void onChannelMessage(ChannelMessageEvent event) {

        twitchClient.getEventManager().onEvent(PrivateMessageEvent.class, tst -> {

        });

        Usuario usuario = new Usuario(event.getUser().getName().toLowerCase());

        this.genericMessage.handleGenericMessage(event.getChannel().getName(), usuario, event.getMessage());



        // is command?
        if (event.getMessage().startsWith(magicBotProperties.getCommandPrefix())) {
            if (MessageDispatcher.validateTimeOut(event.getUser().getName())) {
                return;
            }

            String messageWithoutPrefix = event.getMessage().substring(magicBotProperties.getCommandPrefix().length());

            String command = messageWithoutPrefix.split(" ")[0];

            switch (command) {
                case "ping":
                    ping.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "userid":
                    userId.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "lastseen":
                    lastSeen.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "join":
                    joinChannel.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "rl":
                    randomLine.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "rq":
                    randomQuote.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "notify":
                case "remind":
                    remind.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "help":
                    help.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;

                case "commands":
                    commands.initialize(CommandSource.CHANNEL, event.getChannel().getName(), usuario, magicBotProperties.getCommandPrefix(), messageWithoutPrefix, event.getPermissions(), twitchClient.getChat(),event);
                    break;
            }
        }
    }

}

package com.rs.mb.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.FollowEvent;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;

public class ChannelNotificationOnFollow {

    @Value("${magicbot.owner}")
    private String owner;

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ChannelNotificationOnFollow(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(FollowEvent.class, event -> onFollow(event));
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onFollow(FollowEvent event) {

        if (!event.getChannel().getName().equalsIgnoreCase(owner)) {
            return;
        }
        String message = String.format(
                "%s is now following the stream :) thanks for supporting",
                event.getUser().getName()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}

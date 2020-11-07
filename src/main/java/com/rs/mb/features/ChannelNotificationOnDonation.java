package com.rs.mb.features;


import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.DonationEvent;
import org.springframework.beans.factory.annotation.Value;

public class ChannelNotificationOnDonation {

    @Value("${magicbot.owner}")
    private String owner;


    public ChannelNotificationOnDonation(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(DonationEvent.class, event -> onDonation(event));
    }

    public void onDonation(DonationEvent event) {

        if (!event.getChannel().getName().equalsIgnoreCase(owner)) {
            return;
        }

        String message = String.format(
                "%s just donated %s using %s!",
                event.getUser().getName(),
                event.getAmount(),
                event.getSource()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}

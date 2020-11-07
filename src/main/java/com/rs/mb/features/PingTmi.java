package com.rs.mb.features;

import com.github.twitch4j.TwitchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PingTmi {

    private final TwitchClient twitchClient;

    @Scheduled(fixedRate = 10000)
    public void pingTmi() {
        twitchClient.getChat().sendRaw("PONG :tmi.twitch.tv");
    }
}

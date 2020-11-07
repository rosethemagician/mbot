package com.rs.mb.configuration;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.rs.mb.CredentialProperties;
import com.rs.mb.MagicBotProperties;
import com.rs.mb.domain.entities.Canal;
import com.rs.mb.domain.entities.Lembrete;
import com.rs.mb.features.ChannelNotificationOnDonation;
import com.rs.mb.features.ChannelNotificationOnFollow;
import com.rs.mb.features.ChannelNotificationOnSubscription;
import com.rs.mb.features.MessageHandler;
import com.rs.mb.services.CanalService;
import com.rs.mb.services.LembreteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Configuration
@Slf4j
public class Bot {

    @Autowired
    private MagicBotProperties magicBotProperties;

    @Autowired
    private CredentialProperties credentialProperties;

    @Autowired
    private CanalService canalService;

    @Autowired
    private LembreteService lembreteService;


    @Bean
    public TwitchClient twitchClient() {

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        OAuth2Credential credential = new OAuth2Credential(
                "twitch",
                magicBotProperties.getIrc()
        );

        TwitchClient twitchClient = clientBuilder
                .withClientId(credentialProperties.getId())
                .withClientSecret(credentialProperties.getSecret())
                .withEnableHelix(true)
                .withCommandTrigger("&")
                .withChatAccount(credential)
                .withEnableChat(true)
                .withEnableKraken(true)
                .withEnablePubSub(true)
                .build();

        twitchClient.getClientHelper().enableFollowEventListener(magicBotProperties.getOwner());

        joinChannels(twitchClient);

        return twitchClient;
    }

    @Bean
    public ChannelNotificationOnDonation channelNotificationOnDonation(TwitchClient twitchClient) {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
        return new ChannelNotificationOnDonation(eventHandler);
    }

    @Bean
    public ChannelNotificationOnFollow channelNotificationOnFollow(TwitchClient twitchClient) {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
        return new ChannelNotificationOnFollow(eventHandler);
    }

    @Bean
    public ChannelNotificationOnSubscription channelNotificationOnSubscription(TwitchClient twitchClient) {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
        return new ChannelNotificationOnSubscription(eventHandler);
    }

    @Bean
    public MessageHandler messageHandler(TwitchClient twitchClient) {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);
        return new MessageHandler(eventHandler);
    }

    @Bean
    public List<Canal> canais() {
        return this.canalService.findAll();
    }

    @Bean
    public List<Lembrete> lembretes() {
        return this.lembreteService.findAll();
    }


    @Bean
    public ConcurrentHashMap<String, String> usuariosPendentes(List<Lembrete> lembretes) {
        ConcurrentHashMap<String, String> usuariosPendentes = new ConcurrentHashMap();

        if (!CollectionUtils.isEmpty(lembretes)) {
            Map tst =  lembretes.stream().collect(Collectors.toMap(l -> l.getDestinatario().getNome(), l -> l.getDestinatario().getNome(), (l1, l2) -> l1));
            usuariosPendentes = new ConcurrentHashMap(tst);
        }

        return usuariosPendentes;
    }

    public void joinChannels(TwitchClient twitchClient) {
        List<Canal> canals = this.canalService.findAll();
        canals.stream().forEach(c -> {
            twitchClient.getChat().joinChannel(c.getNome());
        });
    }

}

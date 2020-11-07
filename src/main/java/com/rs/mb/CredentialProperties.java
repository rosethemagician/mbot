package com.rs.mb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("magicbot.twitch.client")
public class CredentialProperties {

    private String id;

    private String secret;


}



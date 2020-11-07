package com.rs.mb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("magicbot.twitter")
public class TwitterCredentialProperties {

    private String oauth1;

    private String oauth2;

    private String token;

    private String secret;


}



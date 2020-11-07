package com.rs.mb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("magicbot")
public class MagicBotProperties {

    private String owner;

    private String irc;

    private String commandPrefix;


}



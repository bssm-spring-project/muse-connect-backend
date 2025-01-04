package com.group.museconnect.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("spotify")
public class SpotifyProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}

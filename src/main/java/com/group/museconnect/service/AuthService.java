package com.group.museconnect.service;

import com.group.museconnect.feign.SpotifyClient;
import com.group.museconnect.global.config.properties.SpotifyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SpotifyClient spotifyClient;
    private final SpotifyProperties spotifyProperties;

    public void authWithSpotify(String code) {
        String credentials = spotifyProperties.getClientId() + ":" + spotifyProperties.getClientSecret();
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        Map<String, String> form = new HashMap<>();
        form.put("grant_type", "authorization_code");
        form.put("code", code);
        form.put("redirect_uri", spotifyProperties.getRedirectUri());

        String accessToken = spotifyClient.getAccessToken(
                "Basic " + base64Credentials,
                form
        ).getAccessToken();
        System.out.println(accessToken);
    }
}

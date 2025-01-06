package com.group.museconnect.service;

import com.group.museconnect.domain.user.User;
import com.group.museconnect.feign.SpotifyAccountClient;
import com.group.museconnect.feign.SpotifyApiClient;
import com.group.museconnect.global.config.properties.SpotifyProperties;
import com.group.museconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SpotifyAccountClient spotifyAccountClient;
    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyProperties spotifyProperties;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String authWithSpotify(String code) {
        String credentials = spotifyProperties.getClientId() + ":" + spotifyProperties.getClientSecret();
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        Map<String, String> form = new HashMap<>();
        form.put("grant_type", "authorization_code");
        form.put("code", code);
        form.put("redirect_uri", spotifyProperties.getRedirectUri());

        String accessToken = spotifyAccountClient.getAccessToken(
                "Basic " + base64Credentials,
                form
        ).getAccessToken();
        System.out.println(accessToken);

        Map<String, Object> response = spotifyApiClient.getCurrentUsersProfile("Bearer " + accessToken);
        String email = (String) response.get("email");

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            userRepository.save(
                    new User(email, (String) response.get("display_name"))
            );
        }

        return tokenService.generateAccessToken(email);
    }
}

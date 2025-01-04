package com.group.museconnect.feign;

import com.group.museconnect.feign.dto.response.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "SpotifyClient", url = "https://accounts.spotify.com")
public interface SpotifyClient {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SpotifyTokenResponse getAccessToken(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, ?> form
    );
}

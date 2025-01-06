package com.group.museconnect.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "SpotifyApiClient", url = "https://api.spotify.com")
public interface SpotifyApiClient {

    @GetMapping(value = "/v1/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> getCurrentUsersProfile(
            @RequestHeader("Authorization") String authorization
    );
}

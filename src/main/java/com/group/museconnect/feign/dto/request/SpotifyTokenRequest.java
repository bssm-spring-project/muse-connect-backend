package com.group.museconnect.feign.dto.request;

public record SpotifyTokenRequest(
        String code,
        String redirectUri,
        String grantType
) {
}
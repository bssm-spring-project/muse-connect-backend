package com.group.museconnect.feign.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpotifyTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}

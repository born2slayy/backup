package com.rendi.RendiBackend.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.rendi.RendiBackend.auth.exception.AuthErrorCode;
import com.rendi.RendiBackend.auth.exception.AuthException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Provider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    @JsonValue
    private final String title;

    public static Provider toProvider(String str) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.title.equals(str))
                .findAny()
                .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_PROVIDER_MISS_MATCH));
    }

}

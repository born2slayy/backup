package com.rendi.RendiBackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonJoinRequest {
    private String principal;
    private String credential;
}

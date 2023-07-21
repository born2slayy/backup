package com.rendi.RendiBackend.member.dto;

import com.rendi.RendiBackend.member.domain.Member;
import com.rendi.RendiBackend.member.domain.Provider;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialJoinRequest {

    @NotBlank
    private String provider;
    private ProfileSaveRequest profile;
    @NotBlank
    private String emailAgreeYn;

    @NotBlank
    private String phoneAgreeYn;
    public static Member toEntity(SocialJoinRequest request, String pw) {
        return Member.builder()
                .username(request.getProvider()+request.profile.getEmail())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(pw))
                .email(request.getProfile().getEmail())
                .provider(Provider.toProvider(request.provider))
                .emailAgreeYn(request.emailAgreeYn)
                .phoneAgreeYn(request.phoneAgreeYn)
                .build();
    }
}


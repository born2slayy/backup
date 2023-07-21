package com.rendi.RendiBackend.member.service;

import com.rendi.RendiBackend.auth.domain.MemberRefreshToken;
import com.rendi.RendiBackend.auth.dto.TokenRequestDto;
import com.rendi.RendiBackend.auth.exception.AuthErrorCode;
import com.rendi.RendiBackend.auth.exception.AuthException;
import com.rendi.RendiBackend.auth.repository.RefreshTokenRepository;
import com.rendi.RendiBackend.member.domain.Interest;
import com.rendi.RendiBackend.member.domain.Member;
import com.rendi.RendiBackend.member.domain.Profile;
import com.rendi.RendiBackend.member.dto.ProfileSaveRequest;
import com.rendi.RendiBackend.member.exception.MemberErrorCode;
import com.rendi.RendiBackend.member.exception.MemberException;
import com.rendi.RendiBackend.member.repository.InterestRepository;
import com.rendi.RendiBackend.member.repository.MemberRepository;
import com.rendi.RendiBackend.member.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final InterestRepository interestRepository;
    private final RefreshTokenRepository refreshTokenRepository;
//    private final S3Service s3Service;

    @Transactional
    public Long saveProfile(ProfileSaveRequest request, Member member) {
        Profile profile = request.toProfile();
        String url = null;
        saveInterests(request.getInterests(), profile);
        profile.setMember(member);
        return profileRepository.save(profile).getId();
    }

//    @Transactional(readOnly = true)
//    public ProfilePageResponse showProfilePage() {
//        Member member = findCurrentMember();
//        return ProfilePageResponse.of(profileRepository.findById(member.getId()).get());
//    }
//
//    @Transactional(readOnly = true)
//    public ProfileHomeResponse showProfileHome() {
//        Member member = findCurrentMember();
//        return ProfileHomeResponse.toDto(profileRepository.findById(member.getId()).get());
//    }

    @Transactional
    public void updateProfile(ProfileSaveRequest request) {
        Member member = findCurrentMember();
        Profile profile = profileRepository.findById(member.getId()).get();
        request.updateProfile(profile);

        // 관심분야 리스트 수정 - 기존 Interest 모두 삭제하고 다시 저장
        interestRepository.deleteAll(profile.getInterests());
        profile.getInterests().clear();
        saveInterests(request.getInterests(), profile);
    }

//    @Transactional
//    public void updateProfileImage(MultipartFile img) {
//        Member member = findCurrentMember();
//        Profile profile = profileRepository.findById(member.getId()).get();
//
//        String imgUrl = profile.getImgUrl();
//
//        if (img==null)
//            throw new MemberException(MemberErrorCode.MEMBER_PROFILE_BAD_REQUEST);
//
//        // 입력이 있으면 업로드
//        if (! img.isEmpty()) {
//            //기존 이미지 있으면 delete
//            if (imgUrl != null)
//                s3Service.deleteProfile(imgUrl);
//            String url = s3Service.uploadProfile(member.getId(), img);
//            profile.setImgUrl(url);
//        } else { // 입력 없으면 기존 값 삭제
//            if (imgUrl != null) {
//                s3Service.deleteProfile(imgUrl);
//                profile.setImgUrl(null);
//            }
//        }
//    }

//    @Transactional
//    public List<ProfileHomeResponse> searchProfilesByKeyword(String keyword) {
//        List<Profile> profilesByInterest = interestRepository.findAllByFieldContaining(keyword).stream()
//                .map(Interest::getProfile).distinct().collect(Collectors.toList());
//        List<Profile> profilesByActivity = findProfilesById(activityRepository.findAllByNameContaining(keyword).stream()
//                .map(Activity::getMember).map(Member::getId).distinct().collect(Collectors.toList()));
//
//        List<Profile> list = Stream.of(profilesByInterest, profilesByActivity)
//                .flatMap(Collection::stream).distinct().toList();
//
//        List<ProfileHomeResponse> result = new ArrayList<>();
//        for (Profile profile : list)
//            result.add(ProfileHomeResponse.toDto(profile));
//        Collections.sort(result);
//        return result;
//    }

//    private List<Profile> findProfilesById(List<Long> idList) {
//        ArrayList<Profile> profiles = new ArrayList<>();
//        for (Long id: idList) {
//            Profile profile = profileRepository.findById(id)
//                    .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
//            profiles.add(profile);
//        }
//        return profiles;
//    }

    private void saveInterests(List<String> requestList, Profile profile) {
        for (String interest : requestList) {
            Interest select = interestRepository.save(new Interest(profile, interest));
            profile.getInterests().add(select);
        }
    }

    private Member findCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member user = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return user;
    }

    @Transactional
    public void deleteRefreshToken(TokenRequestDto tokenRequestDto) {
        String memberId = findCurrentMember().getUsername();
        MemberRefreshToken memberRefreshToken = refreshTokenRepository.findByUsernameAndRefreshToken(memberId, tokenRequestDto.getRefreshToken());
        if (memberRefreshToken == null) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN, "가입되지 않은 회원이거나 유효하지 않은 리프레시 토큰입니다.");
        }
        refreshTokenRepository.deleteById(memberRefreshToken.getRefreshTokenId());
    }

}

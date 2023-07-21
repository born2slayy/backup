package com.rendi.RendiBackend.wish;

import com.rendi.RendiBackend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    boolean existsByMemberAndProduct(Long memberId, Long productId);
    void deleteByMemberAndProduct(Long memberId, Long productId);
    List<Wish> findAllByMember(Member member);
}

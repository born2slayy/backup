package com.rendi.RendiBackend.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PopularRepository extends JpaRepository<Popular, Long> {
    Optional<Popular> findByKeyword(String keyword);
}

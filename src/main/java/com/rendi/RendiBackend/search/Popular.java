package com.rendi.RendiBackend.search;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Popular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popular_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String keyword;

    @Setter
    @Column(nullable = false)
    private Long searchCount;
}

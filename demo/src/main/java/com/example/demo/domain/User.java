package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ✅ JPA 기본 생성자
@AllArgsConstructor // (선택) 테스트나 필요 시 사용
@Builder // ✅ 빌더 자동 생성
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String oauthId; // ✅ 카멜케이스 추천

    @Column(nullable = false)
    private String email;

}

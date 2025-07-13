package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="User")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String oauth_id;

    @Column(nullable = false)
    private String email;

}

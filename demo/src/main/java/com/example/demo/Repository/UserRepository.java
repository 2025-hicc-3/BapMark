package com.example.demo.Repository;

import com.example.demo.domain.Bookmark;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}

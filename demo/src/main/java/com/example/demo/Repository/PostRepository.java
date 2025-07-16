package com.example.demo.Repository;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> { // 게시판에서 검색 기능
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
}

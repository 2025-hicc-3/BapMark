package com.example.demo.Controller;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.ResponseDto.PostResponseDto;
import com.example.demo.Service.PostService;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/allPosts") // 게시판에 올라온 모든 게시글 조회
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/search") // 검색 기능
    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
        return postService.searchPosts(keyword).stream()
                .map(PostResponseDto::new)
                .toList();
    }
}





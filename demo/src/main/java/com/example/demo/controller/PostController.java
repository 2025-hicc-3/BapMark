package com.example.demo.controller;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.jwt.UserDetailsImpl;
import com.example.demo.responseDto.PostRequestDto;
import com.example.demo.responseDto.PostResponseDto;
import com.example.demo.service.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Tag(name = "게시글 API", description = "게시글 등록,조회,검색 관련 API입니다")
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

    // 본인이 작성했던 모든 글 불러오기
    @GetMapping("/me")
    public ResponseEntity<List<Post>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser(); // JWT에서 가져온 로그인 사용자
        List<Post> posts = postService.getPostsByUser(user);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto,
                                        @AuthenticationPrincipal UserDetailsImpl user) {
        postService.createPost(dto, user.getUser()); // 또는 user.getId()
        return ResponseEntity.ok("게시글 작성 완료");
    }


    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl user) throws AccessDeniedException {

        postService.updatePost(postId, dto, user.getUser());
        return ResponseEntity.ok("게시글 수정 완료");
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl user) throws AccessDeniedException {

        postService.deletePost(postId, user.getUser());
        return ResponseEntity.ok("게시글 삭제 완료");
    }


}





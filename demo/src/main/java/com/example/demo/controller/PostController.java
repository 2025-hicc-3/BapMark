package com.example.demo.controller;

import com.example.demo.responseDto.PostRequestDto;
import com.example.demo.responseDto.PostResponseDto;
import com.example.demo.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 등록,조회,검색 관련 API입니다")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "전체 게시글 목록 조회")
    @GetMapping("/allPosts") // 게시판에 올라온 모든 게시글 조회
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "키워드로 게시글 검색")
    @GetMapping("/search") // 검색 기능
    public List<PostResponseDto> searchPosts(@RequestParam("keyword") String keyword) {
        return postService.searchPosts(keyword).stream()
                .map(PostResponseDto::new)
                .toList();
    }

    @Operation(summary = "게시글 등록")
    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto) {
        postService.createPost(dto);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        postService.updatePost(postId, dto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}
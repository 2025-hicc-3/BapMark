package com.example.demo.Service;

import com.example.demo.Repository.PostRepository;
import com.example.demo.ResponseDto.PostRequestDto;
import com.example.demo.ResponseDto.PostResponseDto;
import com.example.demo.domain.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    // 게시글 작성
    public void createPost(PostRequestDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAddress(dto.getAddress());
        post.setLatitude(dto.getLatitude());
        post.setLongitude(dto.getLongitude());
        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAddress(dto.getAddress());
        post.setLatitude(dto.getLatitude());
        post.setLongitude(dto.getLongitude());

        postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        postRepository.deleteById(postId);
    }
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public List<Post> searchPosts(String keyword) { // 검색 기능
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }



}

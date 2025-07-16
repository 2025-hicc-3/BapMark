package com.example.demo.Service;

import com.example.demo.Repository.PostRepository;
import com.example.demo.ResponseDto.PostResponseDto;
import com.example.demo.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .toList();
    }

        public List<Post> searchPosts(String keyword) { // 검색 기능
            return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        }


}

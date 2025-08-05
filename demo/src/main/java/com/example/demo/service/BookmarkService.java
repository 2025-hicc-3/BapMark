package com.example.demo.service;

import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.responseDto.BookmarkResponseDto;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    // 특정 유저의 북마크 목록 가져오기
    public List<BookmarkResponseDto> getBookmarksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookmarkRepository.findByUser(user).stream() // 한 사용자의 북마크 리스트 반환
                .map(BookmarkResponseDto::new)
                .toList();
    }

    // 유저와 방문 여부에 따라 북마크 가져오기 (방문 여부 토글 기능에 사용)
    public List<BookmarkResponseDto> getBookmarksByUserAndVisited(Long userId, Boolean visited) {

        List<Bookmark> bookmarks = bookmarkRepository.findByUserIdAndVisited(userId, visited);
        return bookmarks.stream()
                .map(BookmarkResponseDto::new)
                .toList();
    }

    // 북마크 추가
    @Transactional
    public void addBookmark(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 이미 북마크된 경우 중복 방지
        bookmarkRepository.findByUserAndPost(user, post).ifPresentOrElse(
                b -> { throw new RuntimeException("Already bookmarked"); },
                () -> bookmarkRepository.save(new Bookmark(user, post))
        );
    }



    // 북마크 취소
    @Transactional
    public void removeBookmark(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        bookmarkRepository.deleteByUserAndPost(user, post);
    }


    //검색으로 북마크 저장
    @Transactional
    public void addBookmarkBySearch(Long userId, String placeName, String address, Double lat, Double lng) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 이미 게시글 기반으로 저장된 경우: 검색 기반 저장 금지
        Optional<Bookmark> existing = bookmarkRepository.findByUserAndPlaceNameAndLatitudeAndLongitude(user, placeName, lat, lng);
        if (existing.isPresent() && existing.get().getPost() != null) {
            throw new RuntimeException("이미 게시글을 통해 저장된 장소입니다.");
        }

        // 기존 검색 기반 북마크도 없으면 저장
        bookmarkRepository.save(new Bookmark(user, placeName, address, lat, lng));
    }



}


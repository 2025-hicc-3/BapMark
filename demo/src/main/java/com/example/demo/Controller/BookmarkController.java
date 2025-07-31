package com.example.demo.Controller;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.ResponseDto.StampBoardDto;
import com.example.demo.Service.BookmarkService;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;


@Tag(name = "북마크 API", description = "사용자별 북마크 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/{userId}/bookmarks") // 아이디 보고 북마크 가져오기
    public List<BookmarkResponseDto> getBookmarksByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) Boolean visited
    ) {
        if (visited == null) {
            return bookmarkService.getBookmarksByUser(userId); // 전체
        } else {
            return bookmarkService.getBookmarksByUserAndVisited(userId, visited); // 필터링
        }
    }


    // 게시글로 북마크 추가
    @PostMapping("/{postId}")
    public ResponseEntity<?> bookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.addBookmark(userId, postId);
        return ResponseEntity.ok("북마크 완료");
    }

    // 북마크 취소
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unbookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.removeBookmark(userId, postId);
        return ResponseEntity.ok("북마크 취소됨");
    }

    @PostMapping("/search") // 검색으로 북마크 추가
    public ResponseEntity<String> addBookmarkBySearch(
            @RequestParam Long userId,
            @RequestParam String placeName,
            @RequestParam String address,
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        bookmarkService.addBookmarkBySearch(userId, placeName, address, latitude, longitude);
        return ResponseEntity.ok("Bookmark added by search");
    }

    // 이 북마크가 어디 스탬프판에 속하는지 불러옴
    @GetMapping("/{bookmarkId}/stampboards")
    public ResponseEntity<List<StampBoardDto>> getBoardsForBookmark(@PathVariable Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        List<StampBoardDto> result = bookmark.getStampBoard().stream()
                .map(StampBoardDto::fromEntity)
                .toList();

        return ResponseEntity.ok(result);
    }

}

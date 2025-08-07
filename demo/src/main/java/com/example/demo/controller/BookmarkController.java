package com.example.demo.controller;

import com.example.demo.repository.BookmarkRepository;
import com.example.demo.responseDto.BookmarkResponseDto;
import com.example.demo.responseDto.StampBoardDto;
import com.example.demo.service.BookmarkService;
import com.example.demo.domain.Bookmark;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "사용자의 북마크 전체 또는 방문 여부 필터링 조회")
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
    @Operation(summary = "게시글 ID로 북마크 추가")
    @PostMapping("/{postId}")
    public ResponseEntity<?> bookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.addBookmark(userId, postId);
        return ResponseEntity.ok("북마크 완료");
    }

    // 북마크 취소
    @Operation(summary = "게시글 ID로 북마크 취소")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unbookmark(@PathVariable Long postId, @RequestParam Long userId) {
        bookmarkService.removeBookmark(userId, postId);
        return ResponseEntity.ok("북마크 취소됨");
    }

    @Operation(summary = "장소 정보로 북마크 추가 (검색 기반)")
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
    @Operation(summary = "특정 북마크가 속한 스탬프판 리스트 조회")
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

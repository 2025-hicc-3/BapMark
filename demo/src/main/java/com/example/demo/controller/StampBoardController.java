package com.example.demo.controller;

import com.example.demo.jwt.UserDetailsImpl;
import com.example.demo.service.StampBoardService;
import com.example.demo.domain.StampBoard;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스탬프보드 API", description = "스탬프보드 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stampboards")
public class StampBoardController {

    private final StampBoardService stampBoardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<StampBoard> createBoard(@RequestParam Long userId,
                                                  @RequestParam String title, @RequestParam String color) {
        StampBoard board = stampBoardService.createStampBoard(userId, title, color);
        return ResponseEntity.ok(board);
    }

    // 보드 이름 수정
    @PatchMapping("/{boardId}/title")
    public ResponseEntity<?> updateBoardTitle(@PathVariable Long boardId, @RequestParam String title) {
        stampBoardService.updateBoardTitle(boardId, title);
        return ResponseEntity.ok("보드 이름이 수정되었습니다.");
    }

    // 보드 컬러 수정
    @PatchMapping("/{boardId}/color")
    public ResponseEntity<?> updateBoardColor(@PathVariable Long boardId, @RequestParam String color) {
        stampBoardService.updateBoardColor(boardId, color);
        return ResponseEntity.ok("보드 컬러가 수정되었습니다.");
    }

    // 유저의 보드 목록 조회
    @GetMapping("/me/boards")
    public ResponseEntity<List<StampBoard>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl user) {
        Long userId = user.getId();
        return ResponseEntity.ok(stampBoardService.getStampBoardsByUser(userId));
    }


    // 특정 보드 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<StampBoard> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(stampBoardService.getStampBoard(id));
    }

    // 보드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        stampBoardService.deleteStampBoard(id);
        return ResponseEntity.ok("삭제 완료");
    }

    // 스탬프보드에 북마크 추가
    @PostMapping("/{boardId}/bookmark")
    public ResponseEntity<?> addBookmark(@PathVariable Long boardId,
                                         @RequestBody Long bookmarkId) {
        try {
            stampBoardService.addBookmarkToBoard(boardId, bookmarkId);
            return ResponseEntity.ok("북마크 추가 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request : 10개 북마크 초과
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }

    // 스탬프보드에서 북마크 삭제
    @DeleteMapping("/{boardId}/bookmark")
    public ResponseEntity<?> removeBookmark(@PathVariable Long boardId,
                                            @RequestBody Long bookmarkId) {
        try {
            stampBoardService.removeBookmarkFromBoard(boardId, bookmarkId);
            return ResponseEntity.ok("북마크 삭제 완료");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }


}


package com.example.demo.Controller;

import com.example.demo.Service.StampBoardService;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.StampBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stampboards")
public class StampBoardController {

    private final StampBoardService stampBoardService;

    // 생성
    @PostMapping
    public ResponseEntity<StampBoard> createBoard(@RequestParam Long userId,
                                                  @RequestParam String title) {
        StampBoard board = stampBoardService.createStampBoard(userId, title);
        return ResponseEntity.ok(board);
    }

    // 유저의 보드 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StampBoard>> getBoardsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(stampBoardService.getStampBoardsByUser(userId));
    }

    // 특정 보드 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<StampBoard> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(stampBoardService.getStampBoard(id));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        stampBoardService.deleteStampBoard(id);
        return ResponseEntity.ok("삭제 완료");
    }

    // 북마크 추가
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

}


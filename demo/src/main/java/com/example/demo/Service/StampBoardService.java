package com.example.demo.Service;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.StampBoardRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.StampBoard;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StampBoardService {

    private final StampBoardRepository stampBoardRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    // 스탬프보드 생성
    public StampBoard createStampBoard(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
        StampBoard board = new StampBoard(user, title);
        return stampBoardRepository.save(board);
    }

    // 특정 유저의 스탬프보드 목록 조회
    public List<StampBoard> getStampBoardsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
        return stampBoardRepository.findByUser(user);
    }

    // 특정 스탬프보드 하나 조회
    public StampBoard getStampBoard(Long boardId) {
        return stampBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("스탬프보드를 찾을 수 없습니다."));
    }

    // 스탬프보드 삭제
    public void deleteStampBoard(Long boardId) {
        stampBoardRepository.deleteById(boardId);
    }

    // 북마크 추가
    public void addBookmarkToBoard(Long stampBoardId, Long bookmarkId) {
        StampBoard board = stampBoardRepository.findById(stampBoardId)
                .orElseThrow(() -> new RuntimeException("StampBoard not found"));

        if (board.getBookmarks().size() >= 10) {
            throw new IllegalStateException("최대 10개의 장소만 추가할 수 있습니다.");
        }

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        board.getBookmarks().add(bookmark);
        stampBoardRepository.save(board);
    }

}


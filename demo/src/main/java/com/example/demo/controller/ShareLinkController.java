package com.example.demo.controller;

import com.example.demo.jwt.UserDetailsImpl;
import com.example.demo.service.ShareLinkService;
import com.example.demo.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
// 여기 수정할 차례 8/6
@Tag(name = "공유링크 API", description = "공유링크 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share")
public class ShareLinkController {

    private final ShareLinkService shareLinkService;

    @PostMapping("/{stampBoardId}")
    public ResponseEntity<String> createShareLink(@PathVariable Long stampBoardId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();

        String url = shareLinkService.createShareLink(stampBoardId, userId); // userId 전달
        return ResponseEntity.ok(url);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<String> importSharedBoard(@PathVariable String uuid,
                                                    @AuthenticationPrincipal User user) {
        shareLinkService.importSharedBoard(uuid, user);
        return ResponseEntity.ok("복사 완료!");
    }
}


package com.example.demo.Controller;

import com.example.demo.Service.ShareLinkService;
import com.example.demo.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "공유링크 API", description = "공유링크 관련 API입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share")
public class ShareLinkController {

    private final ShareLinkService shareLinkService;

    @PostMapping("/{stampBoardId}")
    public ResponseEntity<String> createShareLink(@PathVariable Long stampBoardId) {
        String url = shareLinkService.createShareLink(stampBoardId);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<String> importSharedBoard(@PathVariable String uuid,
                                                    @AuthenticationPrincipal User user) {
        shareLinkService.importSharedBoard(uuid, user);
        return ResponseEntity.ok("복사 완료!");
    }
}


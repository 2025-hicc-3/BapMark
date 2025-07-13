package com.example.demo.Controller;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.Service.BookmarkService;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/{userId}/bookmarks")
    public List<BookmarkResponseDto> getBookmarksByUser(@PathVariable Long userId) {
        return bookmarkService.getBookmarksByUser(userId);
    }
}

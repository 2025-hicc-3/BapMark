package com.example.demo.Service;

import com.example.demo.Repository.BookmarkRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ResponseDto.BookmarkResponseDto;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    public List<BookmarkResponseDto> getBookmarksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookmarkRepository.findByUser(user).stream()
                .map(BookmarkResponseDto::new)
                .toList();
    }

}


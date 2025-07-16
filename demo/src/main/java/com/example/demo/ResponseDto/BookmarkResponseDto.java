package com.example.demo.ResponseDto;

import com.example.demo.domain.Bookmark;
import com.example.demo.domain.Post;

public class BookmarkResponseDto {
    private Long postId;
    private String title;
    private String address;
    private double latitude;
    private double longitude;
    private boolean visited;


    public BookmarkResponseDto(Bookmark bookmark) {
        Post post = bookmark.getPost();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.address = post.getAddress();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.visited = bookmark.getVisited();
    }
}


package com.example.demo.responseDto;

import com.example.demo.domain.Post;

public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String address;
    private double latitude;
    private double longitude;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.address = post.getAddress();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
    }
}


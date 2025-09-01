package com.example.mainproject.api.post.dto;

import com.example.mainproject.domain.post.Post;

import java.time.LocalDateTime;

public class PostResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final String slug;
    private final boolean published;
    private final int likeCount;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Post p) {
        this.id = p.getId();
        this.userId = p.getUserId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.slug = p.getSlug();
        this.published = p.isPublished();
        this.likeCount = p.getLikeCount();
        this.commentCount = p.getCommentCount();
        this.createdAt = p.getCreatedAt();
        this.updatedAt = p.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSlug() { return slug; }
    public boolean isPublished() { return published; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

package com.example.mainproject.api.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostCreateRequest {

    @NotNull
    private Long userId;          // 추후 JWT 붙이면 토큰에서 추출

    @NotBlank @Size(max = 200)
    private String title;

    @NotBlank
    private String content;

    @Size(max = 220)
    private String slug;          // 선택

    private Boolean published = true;

    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSlug() { return slug; }
    public Boolean getPublished() { return published; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setPublished(Boolean published) { this.published = published; }
}

package com.example.mainproject.api.post.dto;

import jakarta.validation.constraints.Size;

public class PostUpdateRequest {

    @Size(max = 200)
    private String title;
    private String content;
    @Size(max = 220)
    private String slug;
    private Boolean published;

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSlug() { return slug; }
    public Boolean getPublished() { return published; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setPublished(Boolean published) { this.published = published; }
}

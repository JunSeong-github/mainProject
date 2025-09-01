package com.example.mainproject.domain.post;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_posts_user_created", columnList = "userId,createdAt"),
        @Index(name = "idx_posts_title", columnList = "title")
})
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)          // 인증 붙이기 전까지는 숫자 userId 사용
    private Long userId;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(unique = true, length = 220)
    private String slug;               // 선택(SEO용)

    @Column(nullable = false)
    private boolean published = true;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int commentCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Post() {}

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // == 팩토리/도메인 메서드 ==
    public static Post create(Long userId, String title, String content, String slug, boolean published) {
        Post p = new Post();
        p.userId = userId;
        p.title = title;
        p.content = content;
        p.slug = slug;
        p.published = published;
        return p;
    }

    public void update(String title, String content, String slug, Boolean published) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (slug != null) this.slug = slug;
        if (published != null) this.published = published;
    }

    // == getters ==
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

    // 집계 보조
    public void increaseLike() { this.likeCount++; }
    public void decreaseLike() { this.likeCount = Math.max(0, this.likeCount - 1); }
}

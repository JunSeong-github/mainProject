package com.example.mainproject.domain.post;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_posts_user_created", columnList = "user_id,created_at"),
        @Index(name = "idx_posts_title",        columnList = "title")
})
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)               // ← LONGTEXT 사용 선언
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(unique = true, length = 220)
    private String slug;

    @Column(nullable = false)
    private boolean published = true;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 팩토리/도메인 메서드
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
}

package com.example.mainproject.application.post;

import com.example.mainproject.api.post.dto.PostCreateRequest;
import com.example.mainproject.api.post.dto.PostUpdateRequest;
import com.example.mainproject.domain.post.Post;
import com.example.mainproject.domain.post.PostRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostCreateRequest req) {
        if (StringUtils.hasText(req.getSlug()) && postRepository.existsBySlug(req.getSlug())) {
            throw new IllegalArgumentException("이미 사용 중인 슬러그입니다.");
        }
        Post p = Post.create(
                req.getUserId(),
                req.getTitle(),
                req.getContent(),
                req.getSlug(),
                req.getPublished() != null ? req.getPublished() : true
        );
        return postRepository.save(p);
    }

    @Transactional(readOnly = true)
    public Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
    }

    @Transactional(readOnly = true)
    public Post getBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + slug));
    }

    public Post update(Long id, PostUpdateRequest req) {
        Post p = getById(id);
        if (StringUtils.hasText(req.getSlug())
                && !req.getSlug().equals(p.getSlug())
                && postRepository.existsBySlug(req.getSlug())) {
            throw new IllegalArgumentException("이미 사용 중인 슬러그입니다.");
        }
        p.update(req.getTitle(), req.getContent(), req.getSlug(), req.getPublished());
        return p;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Post> search(String q, Boolean published, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(
                Math.max(0, page),
                Math.min(100, Math.max(1, size)),
                Sort.by(Sort.Direction.DESC, "createdAt", "id")
        );

        Specification<Post> spec = (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (StringUtils.hasText(q)) {
                String like = "%" + q.trim() + "%";
                predicate.getExpressions().add(
                        cb.or(
                                cb.like(root.get("title"), like),
                                cb.like(root.get("content"), like)
                        )
                );
            }
            if (published != null) {
                predicate.getExpressions().add(cb.equal(root.get("published"), published));
            }
            if (userId != null) {
                predicate.getExpressions().add(cb.equal(root.get("userId"), userId));
            }
            return predicate;
        };

        return postRepository.findAll(spec, pageable);
    }
}

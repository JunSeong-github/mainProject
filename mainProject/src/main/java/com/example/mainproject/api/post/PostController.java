package com.example.mainproject.api.post;

import com.example.mainproject.api.post.dto.*;
import com.example.mainproject.application.post.PostService;
import com.example.mainproject.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@RequestBody @Validated PostCreateRequest req) {
        Post p = postService.create(req);
        return new PostResponse(p);
    }

    // 단건 조회(id)
    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id) {
        return new PostResponse(postService.getById(id));
    }

    // 슬러그 조회
    @GetMapping("/slug/{slug}")
    public PostResponse getBySlug(@PathVariable String slug) {
        return new PostResponse(postService.getBySlug(slug));
    }

    // 검색/페이지
    @GetMapping
    public Page<PostResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        return postService.search(q, published, userId, page, size).map(PostResponse::new);
    }

    // 수정
    @PatchMapping("/{id}")
    public PostResponse update(@PathVariable Long id,
                               @RequestBody @Validated PostUpdateRequest req) {
        return new PostResponse(postService.update(id, req));
    }

    // 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}

package org.jojoldu.book.springbootaws.web;


import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.web.dto.PostsListResponseDto;
import org.jojoldu.book.springbootaws.web.dto.PostsResponseDto;
import org.jojoldu.book.springbootaws.web.dto.PostsSaveRequestDto;
import org.jojoldu.book.springbootaws.web.dto.PostsUpdateRequestDto;
import org.jojoldu.book.springbootaws.web.service.posts.PostsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id") Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable("id") Long id){
        postsService.delete(id);
        return id;
    }

}

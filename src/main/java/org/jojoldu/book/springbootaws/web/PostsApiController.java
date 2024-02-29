package org.jojoldu.book.springbootaws.web;


import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.web.dto.PostsSaveRequestDto;
import org.jojoldu.book.springbootaws.web.service.posts.PostsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

}

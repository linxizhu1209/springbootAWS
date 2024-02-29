package org.jojoldu.book.springbootaws.web.service.posts;

import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.domain.posts.PostsRepository;
import org.jojoldu.book.springbootaws.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}

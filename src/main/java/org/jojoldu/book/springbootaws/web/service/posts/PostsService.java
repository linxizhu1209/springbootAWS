package org.jojoldu.book.springbootaws.web.service.posts;

import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.domain.posts.Posts;
import org.jojoldu.book.springbootaws.domain.posts.PostsRepository;
import org.jojoldu.book.springbootaws.web.dto.PostsListResponseDto;
import org.jojoldu.book.springbootaws.web.dto.PostsResponseDto;
import org.jojoldu.book.springbootaws.web.dto.PostsSaveRequestDto;
import org.jojoldu.book.springbootaws.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }


    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회기능만 남겨두어 조회속도가 개선. 등록, 수정, 삭제 기능이 전혀없는 메소드에서 사용
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}

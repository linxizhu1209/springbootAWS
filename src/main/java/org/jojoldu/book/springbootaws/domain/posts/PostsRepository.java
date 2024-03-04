package org.jojoldu.book.springbootaws.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {

    @Query("SELECT p from Posts p ORDER BY p.id DESC ")
    List<Posts> findAllDesc();  //jpa에서 제공하지 않는 메서드는 쿼리로 작성

}

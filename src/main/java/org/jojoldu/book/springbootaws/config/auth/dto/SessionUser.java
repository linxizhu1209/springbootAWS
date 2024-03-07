package org.jojoldu.book.springbootaws.config.auth.dto;

import lombok.Getter;
import org.hibernate.Session;
import org.jojoldu.book.springbootaws.domain.user.User;

import java.io.Serializable;

/**
 * SessionUser의 경우 인증된 사용자 정보만 필요하다.
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

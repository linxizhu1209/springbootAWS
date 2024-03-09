package org.jojoldu.book.springbootaws.config.auth.dto;


import lombok.Builder;
import lombok.Getter;
import org.jojoldu.book.springbootaws.domain.user.Role;
import org.jojoldu.book.springbootaws.domain.user.User;

import java.util.Collections;
import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     * of 메서드를 두는 이유는, OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에, 값 하나하나를 다 변환해야하기때문
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String,Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
     }

     private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
//        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
         Map<String,Object> response = attributes.containsKey("response") ? (Map<String, Object>) attributes.get("response") : Collections.emptyMap();
        return OAuthAttributes.builder().name((String) response.get("name")).email((String) response.get("email"))
                .picture((String) response.get("profile_image")).attributes(response)
                .nameAttributeKey(userNameAttributeName).build();
     }


    /**
     * OauthAttributes에서 엔티티를 생성하는 시점은 처음 가입할때이고, 이때 기본권한을 게스트로 준다.
     *
     */
     public User toEntity(){
        return User.builder().name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER).build();
     }

}

package org.jojoldu.book.springbootaws.config.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.config.auth.dto.OAuthAttributes;
import org.jojoldu.book.springbootaws.config.auth.dto.SessionUser;
import org.jojoldu.book.springbootaws.domain.user.User;
import org.jojoldu.book.springbootaws.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         *  1. registrationId -> 현재 로그인 진행중인 서비스를 구분하는 코드(구글이냐 네이버냐)
         *  2. UserNameAttributeName -> OAuth2 로그인 진행시 키가 되는 필드값
         *  **/

         String registrationId = userRequest.getClientRegistration().getRegistrationId();

         String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                 .getUserInfoEndpoint().getUserNameAttributeName();

        /**
         * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /**
         *  SessionUser? 세션에 사용자 정보를 저장하기 위한 dto클래스
         */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

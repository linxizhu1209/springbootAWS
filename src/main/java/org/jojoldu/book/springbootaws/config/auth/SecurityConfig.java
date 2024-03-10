package org.jojoldu.book.springbootaws.config.auth;

import lombok.RequiredArgsConstructor;
import org.jojoldu.book.springbootaws.domain.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // 접근권한 설정
        http.csrf((csrf) -> csrf.disable())
                .headers((headers)->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth-> auth.requestMatchers("/","/css/**","/images/**","/js/**","/h2-console/**",
                        "/profile").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .logout((logout)->logout.logoutSuccessUrl("/"))
                .oauth2Login((oauth2)->oauth2.userInfoEndpoint(userInfoEndPoint->
                        userInfoEndPoint.userService(customOAuth2UserService)).defaultSuccessUrl("/",true).permitAll());
// 여기서 userService는 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록하는 것임
        // 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
                return http.build();
    }

}

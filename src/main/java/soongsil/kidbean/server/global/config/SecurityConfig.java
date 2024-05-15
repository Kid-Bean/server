package soongsil.kidbean.server.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import soongsil.kidbean.server.auth.application.CustomOAuth2UserService;
import soongsil.kidbean.server.auth.filter.JwtFilter;
import soongsil.kidbean.server.auth.filter.JwtAccessDeniedHandler;
import soongsil.kidbean.server.auth.filter.JwtAuthenticationEntryPoint;

@Slf4j
@Profile("!test")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;

    private static final String MEMBER = "MEMBER";
    private static final String ADMIN = "ADMIN";

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers(
                        "/error",
                        "/token/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/*",
                        "/auth/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증을 비활성화
                .cors(Customizer.withDefaults()) // CORS 활성화 - corsConfigurationSource 이름의 빈 사용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth // 요청에 대한 인증 설정
                        .requestMatchers("/logout", "/auth/refresh-token").hasRole(MEMBER)
                        .requestMatchers("/programs/edit/**").hasRole(ADMIN) //수정
                        .anyRequest().authenticated())  //이외의 요청은 전부 인증 필요
                .oauth2Login(oauth2 -> {
                    log.info("oauth2 configure");
                    oauth2
                            .userInfoEndpoint(  //OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                                    userInfoEndpointConfig -> userInfoEndpointConfig.userService(
                                            customOAuth2UserService));
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
                            .accessDeniedHandler(jwtAccessDeniedHandler); //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
package yeoun.auth;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import yeoun.common.WebConfig;
import yeoun.exception.CustomAuthenticationEntryPoint;
import yeoun.auth.filter.JwtAccessTokenFilter;
import yeoun.auth.filter.JwtAnonymousTokenFilter;
import yeoun.auth.filter.JwtRefreshTokenFilter;
import yeoun.auth.filter.UserHistoryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final WebConfig webConfig;
    private final JwtAccessTokenFilter jwtAccessTokenFilter;
    private final JwtRefreshTokenFilter jwtRefreshTokenFilter;
    private final JwtAnonymousTokenFilter jwtAnonymousTokenFilter;
    private final UserHistoryFilter userHistoryFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                // security 자체 로그인 기능 disable
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/api/**").hasRole("USER")
//                        .requestMatchers("").hasRole("ADMIN") //TODO 추후 필요시 경로 추가
//                        .requestMatchers("").hasRole("USER") //TODO 추후 필요시 경로 추가
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtAccessTokenFilter, LogoutFilter.class) // 1번쨰
                .addFilterAfter(jwtRefreshTokenFilter, JwtAccessTokenFilter.class) // 2번째
                .addFilterAfter(jwtAnonymousTokenFilter, JwtRefreshTokenFilter.class) //3번째
                .addFilterAfter(userHistoryFilter, JwtAnonymousTokenFilter.class) //3번째

                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(customAuthenticationEntryPoint))

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

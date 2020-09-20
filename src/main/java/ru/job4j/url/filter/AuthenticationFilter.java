package ru.job4j.url.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.job4j.url.model.Account;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 864_000_000;
    private AuthenticationManager auth;

    public AuthenticationFilter(AuthenticationManager auth) {
        this.auth = auth;
    }

    /**
     * Метод осуществляет проверку логина и пароля
     * @param request - запрос
     * @param response - ответ
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account credentials = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            return auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getLogin(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод генерирует токен пользователя для аутентификации
     * @param request - запрос
     * @param response - ответ
     * @param chain - представление о цепочке вызовов фильтров
     * @param auth - данные аутентифицированного пользователя
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}


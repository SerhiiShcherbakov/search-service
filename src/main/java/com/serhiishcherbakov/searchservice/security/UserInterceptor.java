package com.serhiishcherbakov.searchservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.serhiishcherbakov.searchservice.exception.UnauthorizedUserException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    public static final String USER_DETAILS_ATTRIBUTE = "userDetails";
    private static final String USER_AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var jwtToken = extractJwtToken(request);

        if (StringUtils.isBlank(jwtToken)) {
            throw new UnauthorizedUserException();
        }

        request.setAttribute(USER_DETAILS_ATTRIBUTE, extractUserDetails(jwtToken));
        return true;
    }

    private String extractJwtToken(HttpServletRequest request) {
        var authHeader = request.getHeader(USER_AUTHORIZATION_HEADER);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replaceFirst("Bearer ", "");
    }

    private UserDetails extractUserDetails(String jwtToken) {
        try {
            var jwt = JWT.decode(jwtToken);

            return UserDetails.builder()
                    .id(jwt.getClaim("sub").asString())
                    .fullName(jwt.getClaim("name").asString())
                    .email(jwt.getClaim("email").asString())
                    .build();
        } catch (JWTDecodeException e) {
            log.error("Failed to decode JWT token: {}", e.getMessage());
            throw new UnauthorizedUserException();
        }
    }
}

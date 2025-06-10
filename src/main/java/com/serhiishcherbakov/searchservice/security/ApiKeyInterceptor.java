package com.serhiishcherbakov.searchservice.security;

import com.serhiishcherbakov.searchservice.config.SecurityProperties;
import com.serhiishcherbakov.searchservice.exception.UnauthorizedClientException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {
    private static final String API_KEY_HEADER = "X-Api-Key";

    private final SecurityProperties securityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var apiKey = request.getHeader(API_KEY_HEADER);

        securityProperties.getClients().stream()
                .filter(c -> c.getKey().equalsIgnoreCase(apiKey))
                .findAny()
                .orElseThrow(UnauthorizedClientException::new);

        return true;
    }
}

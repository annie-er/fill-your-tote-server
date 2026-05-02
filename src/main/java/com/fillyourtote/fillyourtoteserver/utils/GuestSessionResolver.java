package com.fillyourtote.fillyourtoteserver.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class GuestSessionResolver {

    private static final String GUEST_SESSION_COOKIE = "guestSessionId";

    // Create a guest session cookie if one already doesn't exist
    public String resolve(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> GUEST_SESSION_COOKIE.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseGet(() -> createGuestSessionCookie(response));
        }
        return createGuestSessionCookie(response);
    }

    private String createGuestSessionCookie(HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(GUEST_SESSION_COOKIE, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(cookie);
        return sessionId;
    }
}
package com.fillyourtote.fillyourtoteserver.security;

import com.fillyourtote.fillyourtoteserver.dao.UserRepository;
import com.fillyourtote.fillyourtoteserver.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContextHelper {

    private final UserRepository userRepository;

    public SecurityContextHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return Optional.empty();
        }
        return userRepository.findByEmail(auth.getName());
    }

    public boolean isAuthenticated() {
        return getCurrentUser().isPresent();
    }
}

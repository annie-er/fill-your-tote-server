package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.dao.UserRepository;
import com.fillyourtote.fillyourtoteserver.dto.AuthResponseDTO;
import com.fillyourtote.fillyourtoteserver.dto.LoginRequestDTO;
import com.fillyourtote.fillyourtoteserver.dto.RegisterRequestDTO;
import com.fillyourtote.fillyourtoteserver.entities.User;
import com.fillyourtote.fillyourtoteserver.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager,
                       CartService cartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.cartService = cartService;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public AuthResponseDTO login(LoginRequestDTO request, String guestSessionId) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        // Merge guest cart into user's cart if a guest session exists
        if (guestSessionId != null) {
            cartService.mergeGuestCart(guestSessionId, user);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getFirstName(), user.getLastName(), user.getEmail());
    }
}

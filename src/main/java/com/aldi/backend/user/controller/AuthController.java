package com.aldi.backend.user.controller;

import com.aldi.backend.common.jwt.dto.TokenResponse;
import com.aldi.backend.common.jwt.provider.JwtTokenProvider;
import com.aldi.backend.common.response.BaseResponse;
import com.aldi.backend.user.Enum.UserRole;
import com.aldi.backend.user.dto.SignRequestDto;
import com.aldi.backend.user.entity.Users;
import com.aldi.backend.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH", description = "사용자 관련 API")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody SignRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtTokenProvider.createToken(request, roles);

        return BaseResponse.success(new TokenResponse(token));
    }

    @PostMapping("/signup")
    public BaseResponse<?> signup(@RequestBody SignRequestDto signRequestDto) {
        if (userService.existsByEmail(signRequestDto.getEmail())) {
            return BaseResponse.failure("400", "이미 존재하는 이메일입니다");
        }

        String encodedPassword = passwordEncoder.encode(signRequestDto.getPassword());

        Users user = new Users();
        user.setEmail(signRequestDto.getEmail());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.ROLE_USER);

        userService.save(user);

        return BaseResponse.success("회원가입 성공");
    }

}

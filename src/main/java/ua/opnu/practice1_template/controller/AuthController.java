package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.security.JwtUtil;
import ua.opnu.practice1_template.security.entity.AppUser;
import ua.opnu.practice1_template.security.repository.AppUserRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AppUserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @PostMapping("/register")
  public String register(@RequestBody AppUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");
    userRepository.save(user);
    return "Користувач зареєстрований";
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AppUser loginData) {
    AppUser user = userRepository.findByUsername(loginData.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
      String token = jwtUtil.generateToken(user.getUsername());
      return ResponseEntity.ok().body("Bearer " + token);
    }

    return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Невірний логін або пароль");
  }
}
package ua.opnu.practice1_template.security;

import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.opnu.practice1_template.security.entity.AppUser;
import ua.opnu.practice1_template.security.repository.AppUserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final AppUserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    Object principal = authentication.getPrincipal();

    if (principal instanceof OAuth2User oAuth2User) {
      String email = oAuth2User.getAttribute("email");

      AppUser user = userRepository.findByUsername(email).orElseThrow();

      String token = jwtUtil.generateToken(user.getUsername());

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"message\": \"Успішний вхід\", \"token\": \"Bearer " + token + "\"}");
    } else {
      response.sendRedirect("/oauth2/failure");
    }
  }
}
package ua.opnu.practice1_template.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.security.entity.AppUser;
import ua.opnu.practice1_template.security.repository.AppUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final AppUserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String email = oAuth2User.getAttribute("email");

    AppUser user = userRepository.findByUsername(email)
        .orElseGet(() -> {
          AppUser newUser = new AppUser();
          newUser.setUsername(email);
          newUser.setPassword("");
          newUser.setRole("USER");
          return userRepository.save(newUser);
        });

    return new DefaultOAuth2User(
        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())),
        oAuth2User.getAttributes(),
        "email"
    );
  }
}
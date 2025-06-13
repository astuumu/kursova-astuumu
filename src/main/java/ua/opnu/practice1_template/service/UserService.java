package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.User;
import ua.opnu.practice1_template.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User create(User user) {
    return userRepository.save(user);
  }

  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User update(Long id, User updatedUser) {
    User existing = userRepository.findById(id).orElseThrow();
    existing.setName(updatedUser.getName());
    existing.setEmail(updatedUser.getEmail());
    return userRepository.save(existing);
  }

  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
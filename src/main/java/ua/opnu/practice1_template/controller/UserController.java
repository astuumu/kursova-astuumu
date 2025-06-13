
// controller/UserController.java
package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.User;
import ua.opnu.practice1_template.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping                 // 1. Зареєструвати користувача
  public User create(@RequestBody User user) {
    return userService.create(user);
  }

  @GetMapping("/{id}")        // 2. Отримати профіль
  public User get(@PathVariable Long id) {
    return userService.getById(id).orElse(null);
  }

  @PutMapping("/{id}")        // 3. Оновити профіль
  public User update(@PathVariable Long id, @RequestBody User user) {
    return userService.update(id, user);
  }

  @DeleteMapping("/{id}")     // 4. Видалити користувача
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }
}

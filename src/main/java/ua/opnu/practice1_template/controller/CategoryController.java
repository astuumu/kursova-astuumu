package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Category;
import ua.opnu.practice1_template.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping                 // 9. Додати категорію
  public Category create(@RequestBody Category category) {
    return categoryService.create(category);
  }

  @GetMapping                  // 10. Отримати категорії
  public List<Category> getAll() {
    return categoryService.getAll();
  }

  @PutMapping("/{id}")        // 11. Оновити категорію
  public Category update(@PathVariable Long id, @RequestBody Category category) {
    return categoryService.update(id, category);
  }

  @DeleteMapping("/{id}")     // 12. Видалити категорію
  public void delete(@PathVariable Long id) {
    categoryService.delete(id);
  }
}
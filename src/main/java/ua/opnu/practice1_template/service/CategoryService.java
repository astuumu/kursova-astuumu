package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Category;
import ua.opnu.practice1_template.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  public Category update(Long id, Category updated) {
    Category existing = categoryRepository.findById(id).orElseThrow();
    existing.setName(updated.getName());
    existing.setType(updated.getType());
    return categoryRepository.save(existing);
  }

  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }
}
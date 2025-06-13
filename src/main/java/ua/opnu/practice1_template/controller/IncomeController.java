package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Income;
import ua.opnu.practice1_template.service.IncomeService;

import java.util.List;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

  private final IncomeService incomeService;

  @PostMapping                  // 13. Додати запис доходу
  public Income create(@RequestBody Income income) {
    return incomeService.create(income);
  }

  @GetMapping("/user/{userId}") // 14. Отримати доходи користувача
  public List<Income> getAll(@PathVariable Long userId) {
    return incomeService.getByUserId(userId);
  }

  @DeleteMapping("/{id}")       // 15. Видалити дохід
  public void delete(@PathVariable Long id) {
    incomeService.delete(id);
  }
}
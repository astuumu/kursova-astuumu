package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Budget;
import ua.opnu.practice1_template.service.BudgetService;
import ua.opnu.practice1_template.service.ExpenseService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

  private final BudgetService budgetService;
  private final ExpenseService expenseService;

  @PostMapping                 // 16. Додати місячний бюджет
  public Budget create(@RequestBody Budget budget) {
    return budgetService.create(budget);
  }

  @GetMapping("/user/{userId}") // 17. Отримати бюджети користувача
  public List<Budget> getAll(@PathVariable Long userId) {
    return budgetService.getByUserId(userId);
  }

  @PutMapping("/{id}")         // 18. Оновити бюджет
  public Budget update(@PathVariable Long id, @RequestBody Budget budget) {
    return budgetService.update(id, budget);
  }

  @GetMapping("/exceeded/{userId}/{month}") // 19. Отримати перевищення бюджету
  public BigDecimal getExceed(@PathVariable Long userId, @PathVariable String month) {
    Budget budget = budgetService.getByUserAndMonth(userId, month);
    if (budget == null) return BigDecimal.ZERO;
    BigDecimal total = expenseService.getByMonth(userId, month)
        .stream()
        .map(e -> e.getAmount())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return total.subtract(budget.getLimit()).max(BigDecimal.ZERO);
  }

  @GetMapping("/report/{userId}/{month}") // 20. Звіт по категоріях (тільки назви + сума)
  public List<String> reportByCategory(@PathVariable Long userId, @PathVariable String month) {
    return expenseService.getByMonth(userId, month).stream()
        .collect(java.util.stream.Collectors.groupingBy(
            e -> e.getCategory().getName(),
            java.util.stream.Collectors.reducing(
                BigDecimal.ZERO,
                e -> e.getAmount(),
                BigDecimal::add
            )
        ))
        .entrySet()
        .stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .toList();
  }

  @GetMapping("/left/{userId}/{month}") // 24. Залишок бюджету
  public BigDecimal getLeft(@PathVariable Long userId, @PathVariable String month) {
    Budget budget = budgetService.getByUserAndMonth(userId, month);
    if (budget == null) return BigDecimal.ZERO;
    BigDecimal total = expenseService.getByMonth(userId, month)
        .stream()
        .map(e -> e.getAmount())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return budget.getLimit().subtract(total).max(BigDecimal.ZERO);
  }
}
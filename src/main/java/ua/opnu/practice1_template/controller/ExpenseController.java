package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Expense;
import ua.opnu.practice1_template.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

  private final ExpenseService expenseService;

  @PostMapping                // 5. Додати витрату
  public Expense create(@RequestBody Expense expense) {
    return expenseService.create(expense);
  }

  @GetMapping("/user/{userId}") // 6. Отримати список витрат користувача
  public List<Expense> getAll(@PathVariable Long userId) {
    return expenseService.getByUserId(userId);
  }

  @PutMapping("/{id}")       // 7. Оновити запис витрати
  public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
    return expenseService.update(id, expense);
  }

  @DeleteMapping("/{id}")    // 8. Видалити витрату
  public void delete(@PathVariable Long id) {
    expenseService.delete(id);
  }

  @GetMapping("/user/{userId}/month/{month}")  // 21. Сума витрат за місяць
  public BigDecimal getTotalForMonth(@PathVariable Long userId, @PathVariable String month) {
    return expenseService.getByMonth(userId, month)
        .stream()
        .map(Expense::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @GetMapping("/user/{userId}/month/{month}/average")  // 22. Середня витрата на день
  public BigDecimal getAveragePerDay(@PathVariable Long userId, @PathVariable String month) {
    List<Expense> expenses = expenseService.getByMonth(userId, month);
    long uniqueDays = expenses.stream().map(Expense::getDate).distinct().count();
    BigDecimal total = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    return uniqueDays > 0 ? total.divide(BigDecimal.valueOf(uniqueDays), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
  }

  @GetMapping("/user/{userId}/month/{month}/max")  // 23. Найбільші витрати місяця
  public Expense getMaxExpense(@PathVariable Long userId, @PathVariable String month) {
    return expenseService.getByMonth(userId, month)
        .stream()
        .max((a, b) -> a.getAmount().compareTo(b.getAmount()))
        .orElse(null);
  }

  @GetMapping("/user/{userId}/date/{date}")  // 25. Отримати витрати по даті
  public List<Expense> getByDate(@PathVariable Long userId, @PathVariable String date) {
    return expenseService.getByDate(userId, LocalDate.parse(date));
  }
}
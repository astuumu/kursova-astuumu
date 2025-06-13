package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Category;
import ua.opnu.practice1_template.model.Expense;
import ua.opnu.practice1_template.model.User;
import ua.opnu.practice1_template.repository.CategoryRepository;
import ua.opnu.practice1_template.repository.ExpenseRepository;
import ua.opnu.practice1_template.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public Expense create(Expense expense) {
    // Перевірка, чи існує user/category (у разі потреби)
    User user = userRepository.findById(expense.getUser().getId()).orElseThrow();
    Category category = categoryRepository.findById(expense.getCategory().getId()).orElseThrow();
    expense.setUser(user);
    expense.setCategory(category);
    return expenseRepository.save(expense);
  }

  public List<Expense> getByUserId(Long userId) {
    return expenseRepository.findByUserId(userId);
  }

  public Expense update(Long id, Expense updatedExpense) {
    Expense existing = expenseRepository.findById(id).orElseThrow();
    existing.setAmount(updatedExpense.getAmount());
    existing.setDate(updatedExpense.getDate());
    existing.setDescription(updatedExpense.getDescription());

    if (updatedExpense.getCategory() != null) {
      Category category = categoryRepository.findById(updatedExpense.getCategory().getId()).orElseThrow();
      existing.setCategory(category);
    }

    return expenseRepository.save(existing);
  }

  public void delete(Long id) {
    expenseRepository.deleteById(id);
  }

  public List<Expense> getByDate(Long userId, LocalDate date) {
    return expenseRepository.findByUserIdAndDateBetween(userId, date, date);
  }

  public List<Expense> getByMonth(Long userId, String yearMonth) {
    LocalDate start = LocalDate.parse(yearMonth + "-01");
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
    return expenseRepository.findByUserIdAndDateBetween(userId, start, end);
  }
}
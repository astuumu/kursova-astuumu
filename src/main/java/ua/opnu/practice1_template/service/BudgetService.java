package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Budget;
import ua.opnu.practice1_template.repository.BudgetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

  private final BudgetRepository budgetRepository;

  public Budget create(Budget budget) {
    return budgetRepository.save(budget);
  }

  public List<Budget> getByUserId(Long userId) {
    return budgetRepository.findAll()
        .stream()
        .filter(b -> b.getUser().getId().equals(userId))
        .toList();
  }

  public Budget update(Long id, Budget updated) {
    Budget budget = budgetRepository.findById(id).orElseThrow();
    budget.setLimit(updated.getLimit());
    return budgetRepository.save(budget);
  }

  public Budget getByUserAndMonth(Long userId, String month) {
    return budgetRepository.findByUserIdAndMonth(userId, month).orElse(null);
  }
}
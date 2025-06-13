package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.practice1_template.model.Budget;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
  Optional<Budget> findByUserIdAndMonth(Long userId, String month);
}
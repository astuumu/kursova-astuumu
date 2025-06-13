
package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.practice1_template.model.Income;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
  List<Income> findByUserId(Long userId);
  List<Income> findByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to);
}

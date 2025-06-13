package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Income;
import ua.opnu.practice1_template.repository.IncomeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

  private final IncomeRepository incomeRepository;

  public Income create(Income income) {
    return incomeRepository.save(income);
  }

  public List<Income> getByUserId(Long userId) {
    return incomeRepository.findByUserId(userId);
  }

  public void delete(Long id) {
    incomeRepository.deleteById(id);
  }
}
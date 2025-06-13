
package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "month"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Budget {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private User user;

  /**
   * Рядок формату YYYY-MM (наприклад, "2025-06").
   * Можна замінити на java.time.YearMonth у DTO рівні.
   */
  private String month;

  @Column(name = "budget_limit", nullable = false, precision = 12, scale = 2)
  private BigDecimal limit;
}

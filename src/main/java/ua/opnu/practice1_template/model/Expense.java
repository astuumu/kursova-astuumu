package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Expense {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)              // багато витрат → один користувач
  private User user;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  private LocalDate date;

  @ManyToOne                               // багато витрат → одна категорія
  private Category category;

  private String description;
}
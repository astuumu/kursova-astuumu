package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Income {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private User user;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  private LocalDate date;

  private String source;     // зарплата, бонус тощо
}
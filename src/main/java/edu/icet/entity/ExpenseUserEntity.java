package edu.icet.entity;

import edu.icet.util.ExpenseUserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "expense_users")
public class ExpenseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private UserEntity user;
    private Double amount;
    @ManyToOne
    private ExpenseEntity expense;
    @Enumerated(value = EnumType.ORDINAL)
    private ExpenseUserType expenseUserType;
}

package edu.icet.repository;

import edu.icet.entity.ExpenseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseUserRepository extends JpaRepository<ExpenseUserEntity,Integer> {
}

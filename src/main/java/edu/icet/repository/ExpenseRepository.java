package edu.icet.repository;

import edu.icet.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Integer> {
    //List<ExpenseEntity> findByGroupIdAndUserId(Integer groupId, Integer userId);

    List<ExpenseEntity> findByGroupId(Integer groupId);
}

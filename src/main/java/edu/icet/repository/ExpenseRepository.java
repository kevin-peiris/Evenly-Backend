package edu.icet.repository;

import edu.icet.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Integer> {
    List<ExpenseEntity> findByGroupId(Integer groupId);

//    @Modifying
//    @Query("UPDATE ExpenseUser eu SET eu.amount = 0.0 WHERE eu.user.id = :userId AND eu.expense.groupId = :groupId")
//    void settleUpUserExpenses(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

}

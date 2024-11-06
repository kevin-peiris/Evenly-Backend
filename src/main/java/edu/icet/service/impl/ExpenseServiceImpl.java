package edu.icet.service.impl;

import edu.icet.dto.Expense;
import edu.icet.dto.Group;
import edu.icet.entity.ExpenseEntity;
import edu.icet.entity.ExpenseUserEntity;
import edu.icet.entity.GroupEntity;
import edu.icet.entity.UserEntity;
import edu.icet.repository.ExpenseRepository;
import edu.icet.service.ExpenseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    final ExpenseRepository repository;

    @Override
    public boolean addExpense(Expense expense) {
        try {
            ExpenseEntity entity = mapToEntity(expense);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ExpenseEntity mapToEntity(Expense expense) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setDescription(expense.getDescription());
        expenseEntity.setAmount(expense.getAmount());
        expenseEntity.setGroup(new ModelMapper().map(expense.getGroup(), GroupEntity.class));
        expenseEntity.setCreatedById(new ModelMapper().map(expense.getCreatedById(), UserEntity.class));

        List<ExpenseUserEntity> expenseUserEntities = expense.getExpenseUsers().stream().map(expenseUser -> {
            ExpenseUserEntity entity = new ExpenseUserEntity();
            entity.setUser(new ModelMapper().map(expenseUser.getUser(), UserEntity.class));
            entity.setAmount(expenseUser.getAmount());
            entity.setExpenseUserType(expenseUser.getExpenseUserType());
            return entity;
        }).collect(Collectors.toList());

        expenseEntity.setExpenseUsers(expenseUserEntities);
        return expenseEntity;
    }


    @Override
    public List<Expense> getExpense() {
        List<ExpenseEntity> entityList = repository.findAll();
        List<Expense> expenseList = new ArrayList<>();

        for (ExpenseEntity entity : entityList) {
            expenseList.add(new ModelMapper().map(entity, Expense.class));
        }
        return expenseList;
    }

    @Override
    public boolean deleteExpense(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateExpense(Expense expense) {
        try {
            ExpenseEntity entity = new ModelMapper().map(expense, ExpenseEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean settleUpExpenses(Integer groupId, Integer userId) {
//        try {
//            List<ExpenseEntity> expenses = repository.findByGroupIdAndUserId(groupId, userId);
//            for (ExpenseEntity expense : expenses) {
//                expense.getExpenseUsers().forEach(expenseUser -> {
//                    if (expenseUser.getUser().getId().equals(userId)) {
//                        expenseUser.setAmount(0.0);
//                    }
//                });
//            }
//            repository.saveAll(expenses);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
        return false;
    }

    @Override
    public boolean settleUpExpense(Integer expenseId) {
        try {
            Optional<ExpenseEntity> optionalExpense = repository.findById(expenseId);
            if (optionalExpense.isPresent()) {
                ExpenseEntity expense = optionalExpense.get();
                expense.getExpenseUsers().forEach(expenseUser -> expenseUser.setAmount(0.0));
                repository.save(expense);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Expense> getExpensesByGroup(Integer groupId) {
        List<ExpenseEntity> entityList = repository.findByGroupId(groupId);
        List<Expense> expenseList = new ArrayList<>();

        for (ExpenseEntity entity : entityList) {
            expenseList.add(new ModelMapper().map(entity, Expense.class));
        }
        return expenseList;
    }
}

package edu.icet.service.impl;

import edu.icet.dto.Expense;
import edu.icet.dto.ExpenseResponse;
import edu.icet.dto.ExpenseUser;
import edu.icet.dto.User;
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
import java.util.Objects;
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
    public boolean settleUpExpenseByUserId(Integer expenseId, Integer userId) {
        try {
            Optional<ExpenseEntity> optionalExpense = repository.findById(expenseId);
            if (optionalExpense.isPresent()) {
                ExpenseEntity expense = optionalExpense.get();
                for (ExpenseUserEntity expenseUser : expense.getExpenseUsers()) {
                    if (Objects.equals(expenseUser.getUser().getId(), userId)){
                        expenseUser.setAmount(0.0);
                    }
                }
                repository.save(expense);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean settleUpExpense(Integer expenseId) {
        return false;
    }

    @Override
    public List<ExpenseResponse> getExpensesByGroup(Integer groupId) {
        List<ExpenseEntity> entityList = repository.findByGroupId(groupId);
        List<ExpenseResponse> expenseList = new ArrayList<>();

        for (ExpenseEntity entity : entityList) {
            ExpenseResponse expenseResponse = new ExpenseResponse();
            expenseResponse.setId(entity.getId());
            expenseResponse.setDescription(entity.getDescription());
            expenseResponse.setAmount(entity.getAmount());
            expenseResponse.setGroupId(entity.getGroup().getId()); // Extract only group ID
            expenseResponse.setCreatedById(entity.getCreatedById().getId()); // Extract only user ID

            // Map ExpenseUsers to avoid nested Expense reference
            List<ExpenseUser> expenseUsers = new ArrayList<>();
            for (ExpenseUserEntity user : entity.getExpenseUsers()) {
                ExpenseUser expenseUser = new ExpenseUser();
                expenseUser.setId(user.getId());
                expenseUser.setAmount(user.getAmount());
                expenseUser.setUser(new ModelMapper().map(user.getUser(), User.class));
                expenseUser.setExpenseUserType(user.getExpenseUserType());
                expenseUsers.add(expenseUser);
            }
            expenseResponse.setExpenseUsers(expenseUsers);

            expenseList.add(expenseResponse);
        }

        return expenseList;
    }
}

package edu.icet.service.impl;

import edu.icet.dto.Expense;
import edu.icet.dto.ExpenseUser;
import edu.icet.entity.ExpenseEntity;
import edu.icet.entity.ExpenseUserEntity;
import edu.icet.repository.ExpenseRepository;
import edu.icet.repository.ExpenseUserRepository;
import edu.icet.service.ExpenseUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseUserServiceImpl implements ExpenseUserService {

    final ExpenseUserRepository repository;

    @Override
    public boolean addExpenseUser(ExpenseUser expenseUser) {
        try {
            ExpenseUserEntity entity = new ModelMapper().map(expenseUser, ExpenseUserEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ExpenseUser> getExpenseUser() {
        List<ExpenseUserEntity> entityList = repository.findAll();
        List<ExpenseUser> expenseUserList = new ArrayList<>();

        for (ExpenseUserEntity entity : entityList) {
            expenseUserList.add(new ModelMapper().map(entity, ExpenseUser.class));
        }
        return expenseUserList;
    }

    @Override
    public boolean deleteExpenseUser(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateExpenseUser(ExpenseUser expenseUser) {
        try {
            ExpenseUserEntity entity = new ModelMapper().map(expenseUser, ExpenseUserEntity.class);
            repository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

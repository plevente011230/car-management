package com.levi.carmanagement.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = Expense.GET_ALL_EXPENSE_FOR_CAR,
        query = "select e from Expense e where e.car.id = :carId and e.car.owner.username = :loggedInUser")
@NamedQuery(name = Expense.SUM_EXPENSES_FOR_CAR,
        query = "select sum(e.amount), e.currency from Expense e where e.car.id = :carId and e.car.owner.username = :loggedInUser group by e.currency")
public class Expense extends AbstractEntity {

    public static final String GET_ALL_EXPENSE_FOR_CAR = "Expense.getAll";
    public static final String SUM_EXPENSES_FOR_CAR = "Expense.sum";

    @PositiveOrZero
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "CAR_ID")
    private Car car;

    public Expense() {
    }

    public Expense(Long id, LocalDateTime created, LocalDateTime lastUpdated, BigDecimal amount, Currency currency, ExpenseCategory expenseCategory, Car car) {
        super(id, created, lastUpdated);
        this.amount = amount;
        this.currency = currency;
        this.expenseCategory = expenseCategory;
        this.car = car;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

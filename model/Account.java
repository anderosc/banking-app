package model;

import java.math.BigDecimal;

abstract class Account {

    private BigDecimal balance = new BigDecimal(0);

    public BigDecimal getBalance() {
        return balance;
    }

    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public abstract void deposit(BigDecimal amount);

    public abstract void withdraw(BigDecimal amount);

    public abstract void deleteBalance(BigDecimal amount);

}
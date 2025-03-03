package model;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    
    @Override
    public void deposit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        int result;
        BigDecimal balance = getBalance();
        BigDecimal input = amount;
        result = balance.compareTo(input);
        if(result == 1 || result == 0) {
            setBalance(getBalance().subtract(amount));
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed: Insufficient funds.");
        }
    }

    @Override
    public void deleteBalance(BigDecimal amount) {
        setBalance(getBalance().subtract(amount));
    }

    public void addInterest() {
        setBalance(getBalance().multiply(new BigDecimal(1.01)));
    }

}

package model;

import java.math.BigDecimal;

public class InvestmentAccount extends Account {

    private BigDecimal LOW_RISK = new BigDecimal(0);
    private BigDecimal MEDIUM_RISK = new BigDecimal(0);
    private BigDecimal HIGH_RISK = new BigDecimal(0);

    public BigDecimal getLOWBalance() {
        return LOW_RISK;
    }

    protected void setLOWBalance(BigDecimal amount) {
        this.LOW_RISK = amount;
    }

    public BigDecimal getMEDBalance() {
        return MEDIUM_RISK;
    }

    protected void setMEDBalance(BigDecimal amount) {
        this.MEDIUM_RISK = amount;
    }

    public BigDecimal getHIGHBalance() {
        return HIGH_RISK;
    }

    protected void setHIGHBalance(BigDecimal amount) {
        this.HIGH_RISK = amount;
    }

    public void addLOWBalance(BigDecimal amount) {
        setLOWBalance(getLOWBalance().add(amount));
    }

    public void addMEDBalance(BigDecimal amount) {
        setMEDBalance(getMEDBalance().add(amount));
    }

    public void addHIGHBalance(BigDecimal amount) {
        setHIGHBalance(getHIGHBalance().add(amount));
    }

    public void addInterest() {
        if(getLOWBalance().compareTo(BigDecimal.ZERO) > 0) {
            setLOWBalance(getLOWBalance().multiply(new BigDecimal(1.02)));
        }
        if(getMEDBalance().compareTo(BigDecimal.ZERO) > 0) {
            setMEDBalance(getMEDBalance().multiply(new BigDecimal(1.05)));
        }
        if(getHIGHBalance().compareTo(BigDecimal.ZERO) > 0) {
            setHIGHBalance(getHIGHBalance().multiply(new BigDecimal(1.10)));
        }
    }

    public void WithdrawAllInvestments() {
        if(getLOWBalance().compareTo(BigDecimal.ZERO) > 0) {
            deposit(getLOWBalance());
            setLOWBalance(new BigDecimal(0));
        }
        if(getMEDBalance().compareTo(BigDecimal.ZERO) > 0) {
            deposit(getMEDBalance());
            setMEDBalance(new BigDecimal(0));
        }
        if(getHIGHBalance().compareTo(BigDecimal.ZERO) > 0) {
            deposit(getHIGHBalance());
            setHIGHBalance(new BigDecimal(0));
        }
    }

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
            System.out.println("Balance is Higher Than Requested Amount");
            setBalance(getBalance().subtract(amount));
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Insufficient cash on hand.");
        }
    }

    @Override
    public void deleteBalance(BigDecimal amount) {
        setBalance(getBalance().subtract(amount));
    }
    
}
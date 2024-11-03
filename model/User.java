package model;

public class User {

    private String _userName;
    private Double _cashBalance;

    public User(String name, Double cash) {
        this._userName = name;
        this._cashBalance = cash;
    }

    public String getUserName() {
        return _userName;
    }
 
    public void setuserName(String username) {
        this._userName = username;
    }

    public Double getUserCashBalance() {
        return _cashBalance;
    }
 
    public void setuserCashBalance(Double cash) {
        this._cashBalance = cash;
    }

}
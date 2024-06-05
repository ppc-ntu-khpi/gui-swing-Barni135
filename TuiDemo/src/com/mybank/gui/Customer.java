package com.mybank.gui;

import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

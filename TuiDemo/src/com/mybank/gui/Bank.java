package com.mybank.gui;
import com.mybank.gui.Customer;

import java.util.List;
import java.util.ArrayList;

class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            return customers.get(index);
        }
        return null;
    }
}




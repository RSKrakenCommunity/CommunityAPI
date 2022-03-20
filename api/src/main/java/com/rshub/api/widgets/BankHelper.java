package com.rshub.api.widgets;

import com.rshub.api.banking.Bank;

public class BankHelper {
    private BankHelper() {
    }

    private static final Bank BANK = new Bank();

    public static boolean isOpen() {
        return BANK.isOpen();
    }

    public static Bank getBank() {
        return BANK;
    }
}

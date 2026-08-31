package com.geraj.assignment;

import com.geraj.assignment.model.Account;

public class AccountSession {
    private static AccountSession instance;
    private final Account account;

    private AccountSession(Account account) {
        this.account = account;
    }

    public static void startSession(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        instance = new AccountSession(account);
    }

    public static AccountSession getInstance() {
        return instance;
    }

    public Account getAccount() {
        return account;
    }

    public static void clear() {
        instance = null;
    }
}
package com.geraj.assignment.dao;
import com.geraj.assignment.model.Account;

public interface IAccountDAO {
    void createAccount(Account account);

    Account getAccount(int id);
}

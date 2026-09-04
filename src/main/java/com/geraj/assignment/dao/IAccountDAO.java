package com.geraj.assignment.dao;
import com.geraj.assignment.model.Account;

public interface IAccountDAO {
    void createAccount(String username, String hash);

    Account getAccount(int id);
}

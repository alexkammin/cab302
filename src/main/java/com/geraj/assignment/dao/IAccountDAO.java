package com.geraj.assignment.dao;

import com.geraj.assignment.dto.RegistrationDTO;

public interface IAccountDAO {
    void createAccount(RegistrationDTO registration);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.service;

import edu.mum.cs490.project.mock.transaction.api.entity.Account;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tuvshuu
 */
public interface TransactionService {

    String doTransaction(String requestStr);
    Account refreshAccount(Account account);

}

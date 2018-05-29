/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.dao;

import edu.mum.cs490.project.mock.transaction.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tuvshuu
 */
public interface AccountDAO extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE "
            + "a.cardNo = ?1 and "
            + "a.name = ?2 and "
            + "a.zipCode = ?3 and "
            + "a.CVV = ?4 and "
            + "a.expirationDate = ?5 and "
            + "a.cardType = ?6")
    Account findByCardNoAndNameAndZipCodeAndCVVAndExpirationDateAndCardType(String cardNo, String name, String zipCode, String CVV, String expirationDate, String cardType);

    Account findByCardNo(String cardNo);

}

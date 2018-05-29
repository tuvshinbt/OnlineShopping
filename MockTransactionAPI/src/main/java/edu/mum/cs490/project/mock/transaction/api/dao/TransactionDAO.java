/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.dao;

import edu.mum.cs490.project.mock.transaction.api.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tuvshuu
 */
public interface TransactionDAO extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE (t.dstCardNo = ?1 AND t.payCash = true) or (t.srcCardNo = ?1 AND t.result = 1) ORDER BY t.id DESC")
    List<Transaction> getLastActiveTransaction(String srcCardNo);

    static <T> T getSingleResultOrNull(List<T> list) {
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author tuvshuu
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}

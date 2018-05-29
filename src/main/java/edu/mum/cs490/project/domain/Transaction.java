/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.domain;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author tuvshuu
 */
@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sourceCardNo;
    private String sourceCardType;
    private String destinationCardNo;
    @Column(nullable = false)
    private double transactionAmount;
    private String result;
    private String transactionId;
    @Enumerated (value = EnumType.STRING)
    private TransactionType transactionType;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Transaction() {
    }

    public Transaction(String sourceCardNo, String sourceCardType, String destinationCardNo, double transactionAmount, String result, String transactionId, TransactionType transactionType) {
        this.sourceCardNo = sourceCardNo;
        this.sourceCardType = sourceCardType;
        this.destinationCardNo = destinationCardNo;
        this.transactionAmount = transactionAmount;
        this.result = result;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceCardNo() {
        return sourceCardNo;
    }

    public void setSourceCardNo(String sourceCardNo) {
        this.sourceCardNo = sourceCardNo;
    }

    public String getSourceCardType() {
        return sourceCardType;
    }

    public void setSourceCardType(String sourceCardType) {
        this.sourceCardType = sourceCardType;
    }

    public String getDestinationCardNo() {
        return destinationCardNo;
    }

    public void setDestinationCardNo(String destinationCardNo) {
        this.destinationCardNo = destinationCardNo;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

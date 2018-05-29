/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tuvshuu
 */
@Entity
@Table(name = "MOCK_Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
    @Column(name = "source_card_no")
    private String srcCardNo;
    @Column(name = "source_card_type")
    private String srcCardType;
    @Column(name = "destination_card_no")
    private String dstCardNo;
    @Column(name = "transaction_amount", nullable = false)
    private double transactionAmount;
    @Column(name = "available_amount", nullable = false)
    private double availableAmount;
    @Column(name = "used_amount", nullable = false)
    private double usedAmount;
    @Column(name = "response_key")
    private Integer result;
    private Boolean payCash;
    private String transactionId;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
//    private Account srcAccount;
//    private Account dstAccount;

    public Transaction() {
    }

    public Transaction(String srcCardNo, String srcCardType, String dstCardNo, Double transactionAmount, Double availableAmount, Double usedAmount, Integer result, String transactionId) {
        this.srcCardNo = srcCardNo;
        this.srcCardType = srcCardType;
        this.dstCardNo = dstCardNo;
        this.transactionAmount = transactionAmount;
        this.availableAmount = availableAmount;
        this.usedAmount = usedAmount;
        this.result = result;
        this.transactionId = transactionId;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrcCardNo() {
        return srcCardNo;
    }

    public void setSrcCardNo(String srcCardNo) {
        this.srcCardNo = srcCardNo;
    }

    public String getSrcCardType() {
        return srcCardType;
    }

    public void setSrcCardType(String srcCardType) {
        this.srcCardType = srcCardType;
    }

    public String getDstCardNo() {
        return dstCardNo;
    }

    public void setDstCardNo(String dstCardNo) {
        this.dstCardNo = dstCardNo;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Boolean getPayCash() {
        return payCash;
    }

    public void setPayCash(Boolean payCash) {
        this.payCash = payCash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", srcCardNo=" + srcCardNo + ", srcCardType=" + srcCardType + ", dstCardNo=" + dstCardNo + ", transactionAmount=" + transactionAmount + ", availableAmount=" + availableAmount + ", usedAmount=" + usedAmount + ", result=" + result + ", payCash=" + payCash + ", transactionId=" + transactionId + ", createdAt=" + createdAt + '}';
    }

}

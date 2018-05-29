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
@Table(name = "MOCK_Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cardNo;
//    @Column(nullable = false)
    private String expirationDate;
    @Column(name = "name_on_card")
    private String name;
//    @Column(nullable = false)
    private String CVV;
//    @Column(nullable = false)
    private String zipCode;
    private String cardType;
//    @Column(nullable = false)
    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Account() {
    }

    public Account(String cardNo, String expirationDate, String name, String CVV, String zipCode, String cardType) {
        this.cardNo = cardNo;
        this.expirationDate = expirationDate;
        this.name = name;
        this.CVV = CVV;
        this.zipCode = zipCode;
        this.cardType = cardType;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", cardNo=" + cardNo + ", expirationDate=" + expirationDate + ", name=" + name + ", CVV=" + CVV + ", zipCode=" + zipCode + ", cardType=" + cardType + ", amount=" + amount + ", createdAt=" + createdAt + '}';
    }

}

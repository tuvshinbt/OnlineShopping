/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.model;

// {"txnId":"00011","srcCardNo":"01234567890123456","expirationDate":"04/2018","nameOnCard":"TEST","zipCode":"52557","amount":100,"dstCardNo":"0123456789012399","cvv":"CVV"}
// T924bkQSm4LtVlUvYbA3/VKsfBom6bxEIK8t613bNwHIVzuWYzf04u2GV8VW/29Pj2OWiMCd4nPDyPHsLA+p0EYp8HsvirhTDmSw0NNnK9OROunlgBxqxVPAfr8HxU6b+s6W3x1aqWgMTDy7H+cCZjBUWUoC91B+JbCrHuyj0Bv5B9PVnpeCiYVJj0bxuo9g

import java.security.InvalidParameterException;

/**
 * @author tuvshuu
 */
public class TransactionRequest {

    private String txnId;
    private String srcCardNo;
    private String expirationDate;
    private String nameOnCard;
    private String CVV;
    private String zipCode;
    private String cardType;
    private Double amount;
    private String dstCardNo;

    public TransactionRequest() {
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getSrcCardNo() {
        return srcCardNo;
    }

    public void setSrcCardNo(String srcCardNo) {
        this.srcCardNo = srcCardNo;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
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

    public String getDstCardNo() {
        return dstCardNo;
    }

    public void setDstCardNo(String dstCardNo) {
        this.dstCardNo = dstCardNo;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "txnId='" + txnId + '\'' +
                ", srcCardNo='" + srcCardNo + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", CVV='" + CVV + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", cardType='" + cardType + '\'' +
                ", amount=" + amount +
                ", dstCardNo='" + dstCardNo + '\'' +
                '}';
    }

    public void validate() throws InvalidParameterException {

        if (this.txnId == null || this.txnId.isEmpty()) {
            throw new InvalidParameterException("txdId is null!");
        }
        if (this.srcCardNo == null || this.srcCardNo.isEmpty()) {
            throw new InvalidParameterException("srcCardNo is null!");
        } else if (!srcCardNo.matches("\\d{16}")) {
            throw new InvalidParameterException("srcCardNo must be 16 digit!");
        }
        if (this.expirationDate == null || this.expirationDate.isEmpty()) {
            throw new InvalidParameterException("expirationDate is null!");
        }
        if (this.nameOnCard == null || this.nameOnCard.isEmpty()) {
            throw new InvalidParameterException("nameOnCard is null!");
        }
        if (this.CVV == null || this.CVV.isEmpty()) {
            throw new InvalidParameterException("CVV is null!");
        } else if (!CVV.matches("\\d{3}")) {
            throw new InvalidParameterException("CVV must be 3 digit!");
        }
        if (this.zipCode == null || this.zipCode.isEmpty()) {
            throw new InvalidParameterException("zipCode is null!");
        } else if (!zipCode.matches("\\d{5}")) {
            throw new InvalidParameterException("zipCode must be 3 digit!");
        }
        if (this.cardType == null || this.cardType.isEmpty()) {
            throw new InvalidParameterException("cardType is null!");
        }
        if (this.amount == null) {
            throw new InvalidParameterException("amount is null!");
        }
        if (this.dstCardNo == null || this.dstCardNo.isEmpty()) {
            throw new InvalidParameterException("dstCardNo is null!");
        } else if (!dstCardNo.matches("\\d{16}")) {
            throw new InvalidParameterException("dstCardNo must be 16 digit!");
        }
    }

    public static void main(String[] args) {
        System.out.println("".matches("\\d{16}"));
        System.out.println("1234123412341234".matches("\\d{16}"));
        System.out.println("a234123412341234".matches("\\d{16}"));
        System.out.println("a".matches("\\d{16}"));
    }
}

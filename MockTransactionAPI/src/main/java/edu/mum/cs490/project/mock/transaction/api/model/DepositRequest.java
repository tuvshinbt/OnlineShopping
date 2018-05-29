/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.model;

// {"txnId":"00011","dstCardNo":"01234567890123456","expirationDate":"04/2018","nameOnCard":"TEST","zipCode":"52557","amount":200,"cvv":"CVV"}
// T924bkQSm4LtVlUvYbA3/aCOD+bR4Wx2OYubUzEIGJwWjEAGpT5sVP0lY1b4tR9lQRRaWx2D0NKiPSnRRWBT+adBeM9ITcJaQ5U/OV/9fjRdawic6lWGY965HCR4qwr3FX47gNFO8yesBND24omfdjgw5EgH23OH2SHVqz7LkkntSFy9ppKNYDzoaltOOMNK
/**
 *
 * @author tuvshuu
 */
public class DepositRequest {

    private String txnId;
    private String expirationDate;
    private String nameOnCard;
    private String CVV;
    private String zipCode;
    private String cardType;
    private Double amount;
    private String dstCardNo;

    public DepositRequest() {
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
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
        return "DepositRequest{" + "txnId=" + txnId + '}';
    }

}

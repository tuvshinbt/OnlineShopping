package edu.mum.cs490.project.model.payment.mock;

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

    public TransactionRequest(String txnId, String srcCardNo, String expirationDate, String nameOnCard, String CVV, String zipCode, String cardType, Double amount, String dstCardNo) {
        this.txnId = txnId;
        this.srcCardNo = srcCardNo;
        this.expirationDate = expirationDate;
        this.nameOnCard = nameOnCard;
        this.CVV = CVV;
        this.zipCode = zipCode;
        this.cardType = cardType;
        this.amount = amount;
        this.dstCardNo = dstCardNo;
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
}


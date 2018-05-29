package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.TransactionType;

import javax.management.BadAttributeValueExpException;

public interface PaymentService {
    /**
     *
     * @param txnId
     * @param srcCardNo
     * @param expirationDate
     * @param nameOnCard
     * @param CVV
     * @param zipCode
     * @param cardType
     * @param amount
     * @param dstCardNo
     * @param transactionType
     * @return 1 - success, 2 - not found error, 3 - not enough money, 500 - external system error, 999 - connection error
     */
    Integer doTransaction(String txnId, String srcCardNo, String expirationDate, String nameOnCard, String CVV, String zipCode, String cardType, Double amount, String dstCardNo, TransactionType transactionType);
}

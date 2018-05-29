/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.aop;

import edu.mum.cs490.project.mock.transaction.api.entity.Account;
import edu.mum.cs490.project.mock.transaction.api.entity.Transaction;
import edu.mum.cs490.project.mock.transaction.api.util.AES;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author tuvshuu
 */
@Aspect
@Component
public class TransactionAOPService {

    private final Logger logger = LogManager.getLogger(TransactionAOPService.class);

    @Autowired
    @Qualifier("apiAES")
    private AES apiAES;

    /**
     * Decryption method is called between External system and
     * Transaction/DepositService
     *
     * @param pjp
     * @param requestStr
     * @return
     * @throws Throwable
     */
    @Around("execution(* edu.mum.cs490.project.mock.transaction.api.service.*.*(..))&& args(requestStr)")
    public Object aopDecryptService(ProceedingJoinPoint pjp, String requestStr) throws Throwable {
        logger.info("# AOP BEFORE (5) #  is called on " + pjp.getSignature().toShortString() + " " + requestStr);
        String decrytedData;
        try {
            decrytedData = apiAES.decrypt(requestStr);
        } catch (Exception e) {
            decrytedData = e.getMessage();
        }
        logger.info("decrypting data - " + decrytedData);
        Object retVal = pjp.proceed(new Object[]{decrytedData});
        logger.info("# AOP AFTER (5) #  is called on " + pjp.getSignature().toShortString() + " returnValue - " + (retVal != null ? retVal.toString() : null));
        retVal = apiAES.encrypt(retVal != null ? retVal.toString() : "null");
        logger.info("encrypted result - " + retVal);
        return retVal;
    }

    /// AccountDAO.*()
    @Autowired
    @Qualifier("AES")
    private AES aes;

//    @Around("execution(* edu.mum.cs490.project.mock.transaction.api.dao.AccountDAO.findByCardNoAndNameAndZipCodeAndCVVAndExpirationDateAndCardType(..))&& args(cardNo, name, zipCode, CVV, expirationDate, cardType)")
    public Object aopAccountDAOfindByCardNoAndNameAndZipCodeAndCVVAndExpirationDateAndCardType(ProceedingJoinPoint pjp, String cardNo, String name, String zipCode, String CVV, String expirationDate, String cardType) throws Throwable {
        cardNo = aes.encrypt(cardNo);
        name = aes.encrypt(name);
        zipCode = aes.encrypt(zipCode);
        CVV = aes.encrypt(CVV);
        expirationDate = aes.encrypt(expirationDate);
        cardType = aes.encrypt(cardType);
        Object retVal = pjp.proceed(new Object[]{cardNo, name, zipCode, CVV, expirationDate, cardType});
        if (retVal != null) {
            Account account = decryptValueOfAccount((Account) retVal);
            return account;
        } else {
            return null;
        }
    }

//    @Around("execution(* edu.mum.cs490.project.mock.transaction.api.dao.AccountDAO.findByCardNo(..))&& args(cardNo)")
    public Object aopAccountDAOfindByCardNo(ProceedingJoinPoint pjp, String cardNo) throws Throwable {
        cardNo = aes.encrypt(cardNo);
        Object retVal = pjp.proceed(new Object[]{cardNo});
        if (retVal != null) {
            Account account = decryptValueOfAccount((Account) retVal);
            return account;
        } else {
            return null;
        }
    }

    //@Around("execution(* edu.mum.cs490.project.mock.transaction.api.dao.AccountDAO.save(..))&& args(account)")
//    @Around("execution(* org.springframework.data.repository.CrudRepository+.save(..))&& args(account)")
    public void aopAccountDAOSave(ProceedingJoinPoint pjp, Account account) throws Throwable {
        account = encryptValueOfAccount(account);
        pjp.proceed(new Object[]{account});
    }

    public Account decryptValueOfAccount(Account account) {
        account.setCardNo(aes.decrypt(account.getCardNo()));
        if (account.getName() != null) {
            account.setName(aes.decrypt(account.getName()));
        }
        if (account.getZipCode() != null) {
            account.setZipCode(aes.decrypt(account.getZipCode()));
        }
        if (account.getCVV() != null) {
            account.setCVV(aes.decrypt(account.getCVV()));
        }
        if (account.getExpirationDate() != null) {
            account.setExpirationDate(aes.decrypt(account.getExpirationDate()));
        }
        if (account.getCardType() != null) {
            account.setCardType(aes.decrypt(account.getCardType()));
        }
        return account;
    }

    public Account encryptValueOfAccount(Account account) {
        account.setCardNo(aes.encrypt(account.getCardNo()));
        if (account.getName() != null) {
            account.setName(aes.encrypt(account.getName()));
        }
        if (account.getZipCode() != null) {
            account.setZipCode(aes.encrypt(account.getZipCode()));
        }
        if (account.getCVV() != null) {
            account.setCVV(aes.encrypt(account.getCVV()));
        }
        if (account.getExpirationDate() != null) {
            account.setExpirationDate(aes.encrypt(account.getExpirationDate()));
        }
        if (account.getCardType() != null) {
            account.setCardType(aes.encrypt(account.getCardType()));
        }
        return account;
    }


//    @Around("execution(* edu.mum.cs490.project.mock.transaction.api.service.TransactionService.refreshAccount(..))&& args(account)")
//    public Object aopRefreshAccount(ProceedingJoinPoint pjp, Account account) throws Throwable {
//        System.out.println("refreshAccount" + account.toString());
//        account = encryptValueOfAccount(account);
//        System.out.println("refreshAccount" + account.toString());
//        return pjp.proceed(new Object[]{account});
//    }

    // TransactionDAO
    //edu.mum.cs490.project.mock.transaction.api.dao.TransactionDAO.getLastActiveTransaction(String srcCardNo)
//    @Around("execution(* edu.mum.cs490.project.mock.transaction.api.dao.TransactionDAO.getLastActiveTransaction(..))&& args(srcCardNo)")
    public Object aopTransactionDAOgetLastActiveTransaction(ProceedingJoinPoint pjp, String srcCardNo) throws Throwable {
        srcCardNo = aes.encrypt(srcCardNo);
        Object retVal = pjp.proceed(new Object[]{srcCardNo});
        if (retVal != null) {
            List<Transaction> transactions = (List<Transaction>) retVal;
            List<Transaction> newTransactions = new ArrayList<>();
            transactions.stream().forEach((txn) -> {
                newTransactions.add(decryptValueOfTransaction(txn));
            });
            return newTransactions;
        } else {
            return null;
        }
    }

//    @Around("execution(* org.springframework.data.repository.CrudRepository+.save(..))&& args(transaction)")
    public void aopTransactionDAOSave(ProceedingJoinPoint pjp, Transaction transaction) throws Throwable {
        transaction = encryptValueOfTransaction(transaction);
        pjp.proceed(new Object[]{transaction});
    }

    public Transaction decryptValueOfTransaction(Transaction transaction) {
        if (transaction.getSrcCardNo() != null) {
            transaction.setSrcCardNo(aes.decrypt(transaction.getSrcCardNo()));
        }
        if (transaction.getSrcCardType() != null) {
            transaction.setSrcCardType(aes.decrypt(transaction.getSrcCardType()));
        }
        if (transaction.getDstCardNo() != null) {
            transaction.setDstCardNo(aes.decrypt(transaction.getDstCardNo()));
        }
        return transaction;
    }

    public Transaction encryptValueOfTransaction(Transaction transaction) {
        if (transaction.getSrcCardNo() != null) {
            transaction.setSrcCardNo(aes.encrypt(transaction.getSrcCardNo()));
        }
        if (transaction.getSrcCardType() != null) {
            transaction.setSrcCardType(aes.encrypt(transaction.getSrcCardType()));
        }
        if (transaction.getDstCardNo() != null) {
            transaction.setDstCardNo(aes.encrypt(transaction.getDstCardNo()));
        }
        return transaction;
    }

}

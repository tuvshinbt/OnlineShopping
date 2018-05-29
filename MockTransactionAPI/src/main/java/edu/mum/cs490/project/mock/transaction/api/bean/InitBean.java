/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.bean;

import edu.mum.cs490.project.mock.transaction.api.aop.TransactionAOPService;
import edu.mum.cs490.project.mock.transaction.api.dao.AccountDAO;
import edu.mum.cs490.project.mock.transaction.api.entity.Account;
import edu.mum.cs490.project.mock.transaction.api.util.AES;
import edu.mum.cs490.project.mock.transaction.api.util.impl.AESImpl;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author tuvshuu
 */
@Component
public class InitBean implements ApplicationRunner {

    @Value("${name}")
    private String author;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("<<< MOCK TRANSACTION API has been successfully started >>>");
        System.out.printf("<<< Author - %s >>>\n", author);
        insertData();
    }

    @Autowired
    AccountDAO accountDAO;
    @Autowired
    TransactionAOPService transactionAOPService;

    public void insertData() {
        Account accountOSS = new Account();
        accountOSS.setCardNo("4000300020001000");
        accountOSS.setCVV("100");
        accountOSS.setAmount(10000.0);
        accountOSS.setExpirationDate("05/2020");
        accountOSS.setName("OSS");
        accountOSS.setZipCode("52557");
        accountOSS.setCardType("VISA");
        accountOSS.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountOSS);
        accountDAO.save(accountOSS);

        Account accountTAX = new Account();
        accountTAX.setCardNo("4000300020002000");
        accountTAX.setCVV("200");
        accountTAX.setAmount(2.0);
        accountTAX.setExpirationDate("05/2020");
        accountTAX.setName("TAX");
        accountTAX.setZipCode("10000");
        accountTAX.setCardType("VISA");
        accountTAX.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountTAX);
        accountDAO.save(accountTAX);

        Account accountV1 = new Account();
        accountV1.setCardNo("4000300020003001");
        accountV1.setCVV("301");
        accountV1.setAmount(3.0);
        accountV1.setExpirationDate("05/2020");
        accountV1.setName("V1");
        accountV1.setZipCode("52557");
        accountV1.setCardType("VISA");
        accountV1.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountV1);
        accountDAO.save(accountV1);

        Account accountV2 = new Account();
        accountV2.setCardNo("4000300020003002");
        accountV2.setCVV("302");
        accountV2.setAmount(3.0);
        accountV2.setExpirationDate("05/2020");
        accountV2.setName("V2");
        accountV2.setZipCode("52557");
        accountV2.setCardType("VISA");
        accountV2.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountV2);
        accountDAO.save(accountV2);

        Account accountV3 = new Account();
        accountV3.setCardNo("4000300020003003");
        accountV3.setCVV("303");
        accountV3.setAmount(3.0);
        accountV3.setExpirationDate("05/2020");
        accountV3.setName("V3");
        accountV3.setZipCode("52557");
        accountV3.setCardType("VISA");
        accountV3.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountV3);
        accountDAO.save(accountV3);

        Account accountV4 = new Account();
        accountV4.setCardNo("4000300020003004");
        accountV4.setCVV("304");
        accountV4.setAmount(3.0);
        accountV4.setExpirationDate("05/2020");
        accountV4.setName("V4");
        accountV4.setZipCode("52557");
        accountV4.setCardType("VISA");
        accountV4.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountV4);
        accountDAO.save(accountV4);

        Account accountV5 = new Account();
        accountV5.setCardNo("4000300020003005");
        accountV5.setCVV("305");
        accountV5.setAmount(3.0);
        accountV5.setExpirationDate("05/2020");
        accountV5.setName("V5");
        accountV5.setZipCode("52557");
        accountV5.setCardType("VISA");
        accountV5.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(accountV5);
        accountDAO.save(accountV5);

        Account account = new Account();
        account.setCardNo("4929127657563699");
        account.setCVV("123");
        account.setAmount(20000.0);
        account.setExpirationDate("05/2018");
        account.setName("YEE RICK");
        account.setZipCode("52557");
        account.setCardType("VISA");
        account.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account);
        accountDAO.save(account);

        Account account1 = new Account();
        account1.setCardNo("5380326353336488");
        account1.setCVV("123");
        account1.setAmount(50000.0);
        account1.setExpirationDate("01/2020");
        account1.setName("BAY");
        account1.setZipCode("52557");
        account1.setCardType("MASTERCARD");
        account1.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account1);
        accountDAO.save(account1);

        Account account2 = new Account();
        account2.setCardNo("5241600877212477");
        account2.setCVV("123");
        account2.setAmount(10000.0);
        account2.setExpirationDate("01/2020");
        account2.setName("BATT");
        account2.setZipCode("52557");
        account2.setCardType("MASTERCARD");
        account2.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account2);
        accountDAO.save(account2);

        Account account3 = new Account();
        account3.setCardNo("4716959161424639");
        account3.setCVV("123");
        account3.setAmount(10000.0);
        account3.setExpirationDate("01/2020");
        account3.setName("TAMIR");
        account3.setZipCode("52557");
        account3.setCardType("VISA");
        account3.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account3);
        accountDAO.save(account3);

        Account account4= new Account();
        account4.setCardNo("4556894992584983");
        account4.setCVV("111");
        account4.setAmount(10000.0);
        account4.setExpirationDate("01/2020");
        account4.setName("PAGMA");
        account4.setZipCode("52557");
        account4.setCardType("VISA");
        account4.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account4);
        accountDAO.save(account4);

        Account account5= new Account();
        account5.setCardNo("5571828785520125");
        account5.setCVV("222");
        account5.setAmount(10000.0);
        account5.setExpirationDate("01/2020");
        account5.setName("SETH");
        account5.setZipCode("52557");
        account5.setCardType("MASTERCARD");
        account5.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account5);
        accountDAO.save(account5);

        Account account6= new Account();
        account6.setCardNo("5596101658869487");
        account6.setCVV("333");
        account6.setAmount(10000.0);
        account6.setExpirationDate("01/2020");
        account6.setName("HONG");
        account6.setZipCode("52557");
        account6.setCardType("MASTERCARD");
        account6.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account6);
        accountDAO.save(account6);

        Account account7= new Account();
        account7.setCardNo("4916519227635608");
        account7.setCVV("123");
        account7.setAmount(50000.0);
        account7.setExpirationDate("01/2020");
        account7.setName("SOMESH");
        account7.setZipCode("52557");
        account7.setCardType("VISA");
        account7.setCreatedAt(new Date());
        transactionAOPService.encryptValueOfAccount(account7);
        accountDAO.save(account7);
    }

    @Value("${api.secret.key.word}")
    private String apiSecredKeyWord;

    @Bean(name = "apiAES")
    public AES getApiAES() {
        System.out.println("apiSecredKeyWord - " + apiSecredKeyWord);
        return new AESImpl(apiSecredKeyWord);
    }

    @Value("${secret.key.word}")
    private String secredKeyWord;

    @Bean(name = "AES")
    public AES getAES() {
        System.out.println("secredKeyWord - " + secredKeyWord);
        return new AESImpl(secredKeyWord);
    }
}

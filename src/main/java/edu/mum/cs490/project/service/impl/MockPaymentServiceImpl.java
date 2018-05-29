package edu.mum.cs490.project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.mum.cs490.project.domain.TransactionType;
import edu.mum.cs490.project.model.payment.mock.TransactionRequest;
import edu.mum.cs490.project.service.PaymentService;
import edu.mum.cs490.project.utils.AESConverter;
import edu.mum.cs490.project.utils.DoubleRoundUp;
import edu.mum.cs490.project.utils.HttpSender;
import edu.mum.cs490.project.utils.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidParameterException;

@Service
public class MockPaymentServiceImpl implements PaymentService {

    @Autowired
    private HttpSender httpSender;

    @Autowired
    private AESConverter aesConverter;



    @Override
    public Integer doTransaction(String txnId, String srcCardNo, String expirationDate, String nameOnCard, String CVV, String zipCode, String cardType, Double amount, String dstCardNo, TransactionType transactionType) {

        System.out.println("PaymentService template method is called.");

        TransactionRequest transactionRequest = new TransactionRequest(txnId, srcCardNo, expirationDate, nameOnCard.toUpperCase(), CVV, zipCode, cardType.toUpperCase(), DoubleRoundUp.roundUp(amount), dstCardNo);

        System.out.println("Request data : " + transactionRequest.toString());

        String requestData;
        try {
            requestData = JsonConverter.<TransactionRequest>objectToJson(transactionRequest);
        } catch (JsonProcessingException e) {
            throw new InvalidParameterException("Invalid parameter!");
        }
        Integer responseCode;
        try {
            String responseData = httpSender.doPostTransactionToApi(requestData);
            try {
                responseCode = Integer.parseInt(responseData);
            } catch (NumberFormatException e) {
                System.err.println("External system error! " + responseData);
                responseCode = 500;
            }
        } catch (IOException e) {
            System.err.println("Connection error! " + e.getMessage());
            responseCode = 999;
        }

        System.out.println("Result of template method is " + responseCode);
        return responseCode;
    }
}

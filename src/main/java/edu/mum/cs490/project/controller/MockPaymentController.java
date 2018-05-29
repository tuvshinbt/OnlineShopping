package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.TransactionType;
import edu.mum.cs490.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mock")
public class MockPaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * localhost:8080/mock/1/01234567890123456/2018/TEST/52557/500/1/CVV
     * @param txnId
     * @param srcCardNo
     * @param expirationDate
     * @param nameOnCard
     * @param zipCode
     * @param amount
     * @param dstCardNo
     * @param cvv
     * @return
     */
    @GetMapping("/{txnId}/{srcCardNo}/{expirationMonth}/{expirationDate}/{nameOnCard}/{zipCode}/{cardType}/{amount}/{dstCardNo}/{cvv}")
    public String getAllProduct(@PathVariable(value="txnId") String txnId,
                                @PathVariable(value="srcCardNo") String srcCardNo,
                                @PathVariable(value="expirationMonth") String expirationMonth,
                                @PathVariable(value="expirationDate") String expirationDate,
                                @PathVariable(value="nameOnCard") String nameOnCard,
                                @PathVariable(value="zipCode") String zipCode,
                                @PathVariable(value="cardType") String cardType,
                                @PathVariable(value="amount") String amount,
                                @PathVariable(value="dstCardNo") String dstCardNo,
                                @PathVariable(value="cvv") String cvv){
        System.out.println(txnId);
        System.out.println("Result of payment is " + paymentService.doTransaction(txnId,srcCardNo,expirationMonth + "/" + expirationDate,nameOnCard, cvv, zipCode, cardType, Double.parseDouble(amount),dstCardNo, TransactionType.PURCHASE));
        return "mock";
    }
}

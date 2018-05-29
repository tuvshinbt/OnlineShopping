package edu.mum.cs490.project.framework.template.impl;

import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.domain.TransactionType;
import edu.mum.cs490.project.framework.observer.NotifierSubject;
import edu.mum.cs490.project.framework.observer.TransferSubject;
import edu.mum.cs490.project.framework.template.TransactionTemplate;
import edu.mum.cs490.project.service.PaymentService;

public class PurchaseTemplateImpl extends TransactionTemplate {

    private final Order order;

    private final CardDetail OSSCardDetail;

    private final NotifierSubject notifierSubject;

    private final TransferSubject transferSubject;

    private final PaymentService paymentService;

    public PurchaseTemplateImpl(Order order, CardDetail OSSCardDetail, NotifierSubject notifierSubject, TransferSubject transferSubject, PaymentService paymentService) {
        this.order = order;
        this.OSSCardDetail = OSSCardDetail;
        this.notifierSubject = notifierSubject;
        this.transferSubject = transferSubject;
        this.paymentService = paymentService;
    }


    @Override
    protected Integer doTransaction() {
        return paymentService.doTransaction("" + System.currentTimeMillis(),
                order.getCard().getCardNumber(),
                order.getCard().getCardExpirationDate(),
                order.getCard().getCardHolderName(),
                order.getCard().getCvv(),
                order.getCard().getZipcode(),
                order.getCard().getCardType(),
                order.getTotalPriceWithTax(),
                OSSCardDetail.getCardNumber(),
                TransactionType.PURCHASE);
    }

    @Override
    protected void notifyPurchase() {
        try {
            notifierSubject.doNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void transfer() {
        try {
            transferSubject.doNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

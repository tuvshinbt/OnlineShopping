package edu.mum.cs490.project.framework.template.impl;

import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.TransactionType;
import edu.mum.cs490.project.framework.observer.NotifierSubject;
import edu.mum.cs490.project.framework.observer.TransferSubject;
import edu.mum.cs490.project.framework.template.TransactionTemplate;
import edu.mum.cs490.project.service.PaymentService;

public class VendorRegistrationTemplateImpl extends TransactionTemplate {

    private final CardDetail vendorCardDetail;

    private final CardDetail OSSCardDetail;

    private final NotifierSubject notifierSubject;

    private final TransferSubject transferSubject;

    private final PaymentService paymentService;

    private final double registrationFee;

    public VendorRegistrationTemplateImpl(CardDetail vendorCardDetail, CardDetail OSSCardDetail, NotifierSubject notifierSubject, TransferSubject transferSubject, PaymentService paymentService, double registrationFee) {
        this.vendorCardDetail = vendorCardDetail;
        this.OSSCardDetail = OSSCardDetail;
        this.notifierSubject = notifierSubject;
        this.transferSubject = transferSubject;
        this.paymentService = paymentService;
        this.registrationFee = registrationFee;
    }


    @Override
    protected Integer doTransaction() {
        return paymentService.doTransaction("" + System.currentTimeMillis(),
                vendorCardDetail.getCardNumber(),
                vendorCardDetail.getCardExpirationDate(),
                vendorCardDetail.getCardHolderName(),
                vendorCardDetail.getCvv(),
                vendorCardDetail.getZipcode(),
                vendorCardDetail.getCardType(),
                registrationFee,
                OSSCardDetail.getCardNumber(),
                TransactionType.VENDOR_REGISTRATION);
    }

    @Override
    protected void notifyPurchase() {
    }

    @Override
    protected void transfer() {
    }

    @Override
    protected void additionalAction() {
        try {
            notifierSubject.doNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

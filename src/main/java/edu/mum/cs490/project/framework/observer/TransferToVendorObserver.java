package edu.mum.cs490.project.framework.observer;

import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.domain.TransactionType;
import edu.mum.cs490.project.repository.CardDetailRepository;
import edu.mum.cs490.project.service.PaymentService;

import java.util.Observable;
import java.util.Observer;

public class TransferToVendorObserver implements Observer {

    private final Order order;

    private final CardDetail OSSCardDetail;

    private final PaymentService paymentService;
    private final CardDetailRepository cardDetailRepository;

    public TransferToVendorObserver(Order order, CardDetail OSSCardDetail, PaymentService paymentService, CardDetailRepository cardDetailRepository) {
        this.order = order;
        this.OSSCardDetail = OSSCardDetail;
        this.paymentService = paymentService;
        this.cardDetailRepository = cardDetailRepository;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Transferring to VENDOR - " + order.getVendorEarning());

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            System.out.println("\tVENDOR ID - " + orderDetail.getProduct().getVendor().getId() + " Amount - " + orderDetail.getVendorEarning());
            CardDetail vendorCardDetail = cardDetailRepository.findByOwner(orderDetail.getProduct().getVendor());
            if (vendorCardDetail != null) {
                paymentService.doTransaction("" + System.currentTimeMillis(),
                        OSSCardDetail.getCardNumber(),
                        OSSCardDetail.getCardExpirationDate(),
                        OSSCardDetail.getCardHolderName(),
                        OSSCardDetail.getCvv(),
                        OSSCardDetail.getZipcode(),
                        OSSCardDetail.getCardType(),
                        orderDetail.getVendorEarning(),
                        vendorCardDetail.getCardNumber(),
                        TransactionType.TRANSFER_VENDOR);
            }
        }
    }
}

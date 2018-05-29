package edu.mum.cs490.project.framework.observer;

import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.service.MailService;

import java.util.Observable;
import java.util.Observer;

public class MailObserver implements Observer {

    private final Order order;
    private final MailService mailService;

    public MailObserver(Order order, MailService mailService) {
        this.order = order;
        this.mailService = mailService;
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("Send an email to the receivers");

        mailService.sendEmailToCustomerAndVendor(order);
    }
}

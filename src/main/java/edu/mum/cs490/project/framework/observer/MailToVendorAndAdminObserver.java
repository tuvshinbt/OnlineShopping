package edu.mum.cs490.project.framework.observer;

import edu.mum.cs490.project.domain.Admin;
import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.Vendor;
import edu.mum.cs490.project.service.MailService;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MailToVendorAndAdminObserver implements Observer {

    private final Vendor vendor;
    private final MailService mailService;

    public MailToVendorAndAdminObserver(Vendor vendor, MailService mailService) {
        this.vendor = vendor;
        this.mailService = mailService;
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("Send an email to the vendor and the admins");

        mailService.sendEmailToVendorAndAdmin(vendor.getEmail(), vendor.getCompanyName());
    }
}

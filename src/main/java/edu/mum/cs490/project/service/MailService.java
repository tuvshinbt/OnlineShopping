package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.Order;

import java.util.List;

/**
 * Created by Tamir Ganbat 05/02/2018
 */
public interface MailService {
    boolean sendEmailToCustomer(String toEmail, String userName);
    boolean sendEmailToVendorAndAdmin(String toEmail, String userName);
    boolean sendEmailToCustomerAndVendor(Order order);
    boolean sendReportToVendor(String toEmail, byte[] report, String nameOfAttachment);
    boolean sendErrorEmailToAdmin(String errorMessage);
}

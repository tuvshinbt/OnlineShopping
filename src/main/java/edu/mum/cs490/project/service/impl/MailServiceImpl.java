package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.service.AdminService;
import edu.mum.cs490.project.service.MailService;
import edu.mum.cs490.project.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tamir Ganbat 05/02/2018
 */
@Service
public class MailServiceImpl implements MailService {

    private final EmailUtil emailUtil;
    private final AdminService adminService;
    private final static DecimalFormat df = new DecimalFormat("###.##");
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public MailServiceImpl(EmailUtil emailUtil, AdminService adminService){
        this.adminService=adminService;
        this.emailUtil = emailUtil;
    }
 
    @Override
    public boolean sendEmailToVendorAndAdmin(String toEmail, String userName) {
        try{
            emailUtil.sendEmail(toEmail, "Verification", prepareTemplateForVendor(userName));
            //send mail to admin for notification about vendor joined
            List<Admin> lstAdmin = adminService.find(null, null, null, Status.ENABLED);
            for(Admin admin : lstAdmin) {
                emailUtil.sendEmail(admin.getEmail(), "Notification", prepareTemplateForAdmin(userName));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            sendErrorEmailToAdmin(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendEmailToCustomer(String toEmail, String userName) {
        try{
            emailUtil.sendEmail(toEmail, "Verification", prepareTemplateForCustomer(userName));
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            sendErrorEmailToAdmin(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendEmailToCustomerAndVendor(Order order) {
        try{
            String toEmail = "", customerName = "", address = "";
            if(order.getCustomer() == null){
                toEmail = order.getGuest().getEmail();
                customerName = order.getGuest().getFirstName() + " " + order.getGuest().getLastName();
                address = order.getAddress().toString();
            }
            else
            {
                toEmail = order.getCustomer().getEmail();
                customerName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
                address = order.getAddress().toString();
            }
            emailUtil.sendEmail(toEmail, "Verification", prepareTemplateForCustomerWhenPurchase(customerName, address, order));

            final List<Vendor> lstVendor = new ArrayList<>();
            order.getOrderDetails().forEach(u->{
                    if(!lstVendor.contains(u.getProduct().getVendor()))
                        lstVendor.add(u.getProduct().getVendor());});
            for(Vendor vendor : lstVendor){
                List<OrderDetail> currentVendorsOrder = order.getOrderDetails().stream().filter(u->u.getProduct().getVendor().getId() == vendor.getId()).collect(Collectors.toList());
                emailUtil.sendEmail(vendor.getEmail(), "Shipping Notification", prepareTemplateForVendorWhenPurchase(address, new Date(), currentVendorsOrder));
            }
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            sendErrorEmailToAdmin(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendReportToVendor(String toEmail, byte[] report, String nameOfAttachment) {
        try{
            emailUtil.sendEmailWithAttachment(toEmail, "Monthly report", prepareTemplateToSendReportToVendor(), report, nameOfAttachment);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            sendErrorEmailToAdmin(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendErrorEmailToAdmin(String errorMessage) {
        try{
            List<Admin> lstAdmin = adminService.find(null, null, null, Status.ENABLED);
            //send mail to admin for notification about error
            for(Admin admin : lstAdmin) {
                emailUtil.sendEmail(admin.getEmail(), "Notification", prepareErrorMessage(errorMessage));
            }
            return false;
        }
        catch(Exception ex){
            ex.printStackTrace();
            sendErrorEmailToAdmin(ex.getMessage());
            return false;
        }
    }

    private String prepareTemplateToSendReportToVendor(){
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\t<head>\n" +
                "\t\t<!-- NAME: 1 COLUMN -->\n" +
                "\t\t<!--[if gte mso 15]>\n" +
                "\t\t<xml>\n" +
                "\t\t\t<o:OfficeDocumentSettings>\n" +
                "\t\t\t<o:AllowPNG/>\n" +
                "\t\t\t<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "\t\t\t</o:OfficeDocumentSettings>\n" +
                "\t\t</xml>\n" +
                "\t\t<![endif]-->\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\t\t<title>Sending report</title>\n" +
                "        \n" +
                "    <script type=\"text/javascript\" src=\"https://gc.kis.v2.scr.kaspersky-labs.com/BBB3B42D-59BB-2A47-983A-6992483F3C4F/main.js\" charset=\"UTF-8\"></script><style type=\"text/css\">\n" +
                "\t\tp{\n" +
                "\t\t\tmargin:10px 0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tborder-collapse:collapse;\n" +
                "\t\t}\n" +
                "\t\th1,h2,h3,h4,h5,h6{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg,a img{\n" +
                "\t\t\tborder:0;\n" +
                "\t\t\theight:auto;\n" +
                "\t\t\toutline:none;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tbody,#bodyTable,#bodyCell{\n" +
                "\t\t\theight:100%;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.mcnPreviewText{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t}\n" +
                "\t\t#outlook a{\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg{\n" +
                "\t\t\t-ms-interpolation-mode:bicubic;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tmso-table-lspace:0pt;\n" +
                "\t\t\tmso-table-rspace:0pt;\n" +
                "\t\t}\n" +
                "\t\t.ReadMsgBody{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,blockquote{\n" +
                "\t\t\tmso-line-height-rule:exactly;\n" +
                "\t\t}\n" +
                "\t\ta[href^=tel],a[href^=sms]{\n" +
                "\t\t\tcolor:inherit;\n" +
                "\t\t\tcursor:default;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,body,table,blockquote{\n" +
                "\t\t\t-ms-text-size-adjust:100%;\n" +
                "\t\t\t-webkit-text-size-adjust:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass,.ExternalClass p,.ExternalClass td,.ExternalClass div,.ExternalClass span,.ExternalClass font{\n" +
                "\t\t\tline-height:100%;\n" +
                "\t\t}\n" +
                "\t\ta[x-apple-data-detectors]{\n" +
                "\t\t\tcolor:inherit !important;\n" +
                "\t\t\ttext-decoration:none !important;\n" +
                "\t\t\tfont-size:inherit !important;\n" +
                "\t\t\tfont-family:inherit !important;\n" +
                "\t\t\tfont-weight:inherit !important;\n" +
                "\t\t\tline-height:inherit !important;\n" +
                "\t\t}\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding:10px;\n" +
                "\t\t}\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\tmax-width:600px !important;\n" +
                "\t\t}\n" +
                "\t\ta.mcnButton{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t}\n" +
                "\t\t.mcnImage,.mcnRetinaImage{\n" +
                "\t\t\tvertical-align:bottom;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent{\n" +
                "\t\t\tword-break:break-word;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent img{\n" +
                "\t\t\theight:auto !important;\n" +
                "\t\t}\n" +
                "\t\t.mcnDividerBlock{\n" +
                "\t\t\ttable-layout:fixed !important;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\tbody,#bodyTable{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Email Border\n" +
                "\t@tip Set the border for your email.\n" +
                "\t*/\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\t/*@editable*/border:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 1\n" +
                "\t@tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "\t@style heading 1\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:26px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 2\n" +
                "\t@tip Set the styling for all second-level headings in your emails.\n" +
                "\t@style heading 2\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:22px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 3\n" +
                "\t@tip Set the styling for all third-level headings in your emails.\n" +
                "\t@style heading 3\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:20px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 4\n" +
                "\t@tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "\t@style heading 4\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:18px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Style\n" +
                "\t@tip Set the background color and borders for your email's preheader area.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Set the styling for your email's preheader text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Link\n" +
                "\t@tip Set the styling for your email's preheader links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent a,#templatePreheader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Style\n" +
                "\t@tip Set the background color and borders for your email's header area.\n" +
                "\t*/\n" +
                "\t\t#templateHeader{\n" +
                "\t\t\t/*@editable*/background-color:#FFFFFF;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Text\n" +
                "\t@tip Set the styling for your email's header text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Link\n" +
                "\t@tip Set the styling for your email's header links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent a,#templateHeader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Style\n" +
                "\t@tip Set the background color and borders for your email's body area.\n" +
                "\t*/\n" +
                "\t\t#templateBody{\n" +
                "\t\t\t/*@editable*/background-color:#ffffff;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:2px solid #bbbbbb;\n" +
                "\t\t\t/*@editable*/border-bottom:2px solid #bbbbbb;\n" +
                "\t\t\t/*@editable*/padding-top:0;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Text\n" +
                "\t@tip Set the styling for your email's body text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Link\n" +
                "\t@tip Set the styling for your email's body links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent a,#templateBody .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Style\n" +
                "\t@tip Set the background color and borders for your email's footer area.\n" +
                "\t*/\n" +
                "\t\t#templateFooter{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Text\n" +
                "\t@tip Set the styling for your email's footer text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:center;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Link\n" +
                "\t@tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent a,#templateFooter .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t@media only screen and (min-width:768px){\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\twidth:600px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody,table,td,p,a,li,blockquote{\n" +
                "\t\t\t-webkit-text-size-adjust:none !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding-top:10px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnRetinaImage{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImage{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCartContainer,.mcnCaptionTopContent,.mcnRecContentContainer,.mcnCaptionBottomContent,.mcnTextContentContainer,.mcnBoxedTextContentContainer,.mcnImageGroupContentContainer,.mcnCaptionLeftTextContentContainer,.mcnCaptionRightTextContentContainer,.mcnCaptionLeftImageContentContainer,.mcnCaptionRightImageContentContainer,.mcnImageCardLeftTextContentContainer,.mcnImageCardRightTextContentContainer,.mcnImageCardLeftImageContentContainer,.mcnImageCardRightImageContentContainer{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnBoxedTextContentContainer{\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupContent{\n" +
                "\t\t\tpadding:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCaptionLeftContentOuter .mcnTextContent,.mcnCaptionRightContentOuter .mcnTextContent{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardTopImageContent,.mcnCaptionBottomContent:last-child .mcnCaptionBottomImageContent,.mcnCaptionBlockInner .mcnCaptionTopContent:last-child .mcnTextContent{\n" +
                "\t\t\tpadding-top:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardBottomImageContent{\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockInner{\n" +
                "\t\t\tpadding-top:0 !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockOuter{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnTextContent,.mcnBoxedTextContentColumn{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardLeftImageContent,.mcnImageCardRightImageContent{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcpreview-image-uploader{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 1\n" +
                "\t@tip Make the first-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/font-size:22px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 2\n" +
                "\t@tip Make the second-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/font-size:20px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 3\n" +
                "\t@tip Make the third-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/font-size:18px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 4\n" +
                "\t@tip Make the fourth-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Boxed Text\n" +
                "\t@tip Make the boxed text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t.mcnBoxedTextContentContainer .mcnTextContent,.mcnBoxedTextContentContainer .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Visibility\n" +
                "\t@tip Set the visibility of the email's preheader on small screens. You can hide it to save space.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/display:block !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Make the preheader text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Header Text\n" +
                "\t@tip Make the header text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Body Text\n" +
                "\t@tip Make the body text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Footer Text\n" +
                "\t@tip Make the footer content text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}</style></head>\n" +
                "    <body>\n" +
                "\t\t<!--*|IF:MC_PREVIEW_TEXT|*-->\n" +
                "\t\t<!--[if !gte mso 9]><!----><span class=\"mcnPreviewText\" style=\"display:none; font-size:0px; line-height:0px; max-height:0px; max-width:0px; opacity:0; overflow:hidden; visibility:hidden; mso-hide:all;\">*|MC_PREVIEW_TEXT|*</span><!--<![endif]-->\n" +
                "\t\t<!--*|END:IF|*-->\n" +
                "        <center>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "                        <!-- BEGIN TEMPLATE // -->\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"templateContainer\">\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templatePreheader\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateHeader\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnBoxedTextBlock\" style=\"min-width:100%;\">\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "\t<![endif]-->\n" +
                "\t<tbody class=\"mcnBoxedTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnBoxedTextBlockInner\">\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t<td align=\"center\" valign=\"top\" \">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"min-width:100%;\" class=\"mcnBoxedTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td style=\"padding-top:9px; padding-left:18px; padding-bottom:9px; padding-right:18px;\">\n" +
                "                        \n" +
                "                            <table border=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" width=\"100%\" style=\"min-width: 100% !important;background-color: #8ED897;\">\n" +
                "                                <tbody><tr>\n" +
                "                                    <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 18px;color: #F2F2F2;font-family: Helvetica;font-size: 14px;font-weight: normal;text-align: center;\">\n" +
                "                                        <span style=\"font-family:merriweather sans,helvetica neue,helvetica,arial,sans-serif\"><strong>THE MONGOLIAN TEAM ONLINE STORE</strong></span>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody></table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 0px 18px 9px; line-height: 100%;\">\n" +
                "                        \n" +
                "                            <h1 style=\"text-align: center;\">Monthly report</h1>\n" +
                "<br>\n" +
                "<br>\n" +
                "&nbsp;\n" +
                "<div style=\"text-align: center;\">Please review your sell report of last month. If you have any question or any thing to know please let us know.<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "&nbsp;</div>\n" +
                "\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateBody\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateFooter\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding-top:0; padding-right:18px; padding-bottom:9px; padding-left:18px;\">\n" +
                "                        \n" +
                "                            <em>Copyright © 2018 THE MONGOLIAN TEAM, All rights reserved.</em><br>\n" +
                "<br>\n" +
                "<strong>Our mailing address is:</strong><br>\n" +
                "mongolianteampm@gmail.com<br>\n" +
                "<br>\n" +
                "Want to change how you receive these emails?<br>\n" +
                "You can <a href=\"*|UPDATE_PROFILE|*\">update your preferences</a> or <a href=\"*|UNSUB|*\">unsubscribe from this list</a>.\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <!-- // END TEMPLATE -->\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </center>\n" +
                "    </body>\n" +
                "</html>\n");
        return str.toString();
    }

    private String prepareTemplateForVendor(String vendorName){
        StringBuilder str = new StringBuilder();
        str.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <title>Welcome</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#FFFFFF\">\n" +
                "  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" style=\"background-color: #FFFFFF\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"content\" style=\"background-color: #FFFFFF\">\n" +
                "          <tbody><tr>\n" +
                "            <td id=\"templateContainerHeader\" valign=\"top\" mc:edit=\"welcomeEdit-01\">\n" +
                "              <p style=\"text-align:center;margin:0;padding:0;\"><img src=\"http://c0185784a2b233b0db9b-d0e5e4adc266f8aacd2ff78abb166d77.r51.cf2.rackcdn.com/templates/cog-01.jpg\" style=\"display:inline-block;\"></p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td align=\"center\" valign=\"top\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"brdBottomPadd-two\" id=\"templateContainer\" width=\"100%\">\n" +
                "                <tbody><tr>\n" +
                "                  <td class=\"bodyContent\" valign=\"top\" mc:edit=\"welcomeEdit-02\">\n" +
                "                    <p>Hi --"+vendorName+"--,</p>\n" +
                "\n" +
                "                    <h1><strong>Congratulations on signing up<br>\n" +
                "                    for Mongolian Team Online Store!</strong></h1>\n" +
                "\n" +
                "                    <h3>Thanks for joining our online store.\n" +
                "                    We're excited to give you chance that you can sell all your brand product through our widely used application.</h3>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "\n" +
                "              </tbody></table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\t\t  </tbody>\n" +
                "\t\t  </table><!--[if (gte mso 9)|(IE)]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "\n" +
                "    span.preheader {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "    td ul li {\n" +
                "      font-size: 16px;\n" +
                "    }\n" +
                "\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ CLIENT-SPECIFIC STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    #outlook a {\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    /* Force Outlook to provide a \"view in browser\" message */\n" +
                "    .ReadMsgBody {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .ExternalClass {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display emails at full width */\n" +
                "    .ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {\n" +
                "    line-height:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display normal line spacing */\n" +
                "    body,table,td,p,a,li,blockquote {\n" +
                "    -webkit-text-size-adjust:100%;\n" +
                "    -ms-text-size-adjust:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Prevent WebKit and Windows mobile changing default text sizes */\n" +
                "    table,td {\n" +
                "    mso-table-lspace:0;\n" +
                "    mso-table-rspace:0\n" +
                "    }\n" +
                "\n" +
                "    /* Remove spacing between tables in Outlook 2007 and up */\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ RESET STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "    max-width:100%;\n" +
                "    border:0;\n" +
                "    line-height:100%;\n" +
                "    outline:none;\n" +
                "    text-decoration:none\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "    border-collapse:collapse!important\n" +
                "    }\n" +
                "\n" +
                "    .content {\n" +
                "    width:100%;\n" +
                "    max-width:600px\n" +
                "    }\n" +
                "\n" +
                "    .content img {\n" +
                "    height:auto;\n" +
                "    min-height:1px\n" +
                "    }\n" +
                "\n" +
                "    #bodyTable {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #bodyCell {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    #bodyCellFooter {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important;\n" +
                "    padding-top:39px;\n" +
                "    padding-bottom:15px\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    min-width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerHeader {\n" +
                "    font-size:14px;\n" +
                "    padding-top:2.429em;\n" +
                "    padding-bottom:.929em\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerFootBrd {\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0;\n" +
                "    height:10px;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainer {\n" +
                "    border-top:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:4px 4px 0 0;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddle {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm .bodyContent {\n" +
                "    padding-bottom:2em\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 1\n" +
                "    * @tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "    * @style heading 1\n" +
                "    */\n" +
                "    h1 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:26px;\n" +
                "    line-height:1.385em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 2\n" +
                "    * @tip Set the styling for all second-level headings in your emails.\n" +
                "    * @style heading 2\n" +
                "    */\n" +
                "    h2 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:22px;\n" +
                "    line-height:1.455em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 3\n" +
                "    * @tip Set the styling for all third-level headings in your emails.\n" +
                "    * @style heading 3\n" +
                "    */\n" +
                "    h3 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:18px;\n" +
                "    line-height:1.444em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 4\n" +
                "    * @tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "    * @style heading 4\n" +
                "    */\n" +
                "    h4 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:1.571em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h5 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.538em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h6 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:12px;\n" +
                "    line-height:2em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:16px;\n" +
                "    line-height:1.5em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:visited {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:focus {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:hover {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:link {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a .yshortcuts {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent h6 {\n" +
                "    color:#a1a1a1;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.5em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:3.143em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:.714em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:0;\n" +
                "    padding-right:3.571em;\n" +
                "    padding-left:3.571em;\n" +
                "    padding-bottom:1.357em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h4 {\n" +
                "    color:#4E4E4E;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.154em;\n" +
                "    font-weight:400;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h5 {\n" +
                "    color:#828282;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.667em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Body\n" +
                "    * @section body link\n" +
                "    * @tip Set the styling for your email's main content links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    a:visited {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:focus {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:hover {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:link {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a .yshortcuts {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent img {\n" +
                "    height:auto;\n" +
                "    max-width:498px\n" +
                "    }\n" +
                "\n" +
                "    .footerContent {\n" +
                "    color:gray;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:10px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2em;\n" +
                "    padding-right:2em;\n" +
                "    padding-bottom:2em;\n" +
                "    padding-left:2em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .footerContent a:link,.footerContent a:visited,/* Yahoo! Mail Override */ .footerContent a .yshortcuts,.footerContent a span /* Yahoo! Mail Override */ {\n" +
                "    color:#606060;\n" +
                "    font-weight:400;\n" +
                "    text-decoration:underline\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .bodyContentImageFull p {\n" +
                "    font-size:0!important;\n" +
                "    margin-bottom:0!important\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd .bodyContent {\n" +
                "    padding-bottom:2.286em\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two .bodyContent {\n" +
                "    padding-bottom:.857em\n" +
                "    }\n" +
                "\n" +
                "    a.blue-btn {\n" +
                "      background: #5098ea;\n" +
                "      display: inline-block;\n" +
                "      color: #FFFFFF;\n" +
                "      border-top:10px solid #5098ea;\n" +
                "      border-bottom:10px solid #5098ea;\n" +
                "      border-left:20px solid #5098ea;\n" +
                "      border-right:20px solid #5098ea;\n" +
                "      text-decoration: none;\n" +
                "      font-size: 14px;\n" +
                "      margin-top: 1.0em;\n" +
                "      border-radius: 3px 3px 3px 3px;\n" +
                "      background-clip: padding-box;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentTicks {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2.857em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:1.786em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--one {\n" +
                "    width:19%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-bottom:1.143em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--two {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three {\n" +
                "    width:71%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-top:.714em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three h3 {\n" +
                "    margin-bottom:.278em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--four {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 550px),screen and (max-device-width: 550px) {\n" +
                "    body[yahoo] .hide {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .buttonwrapper {\n" +
                "    background-color:transparent!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button {\n" +
                "    padding:0!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button a {\n" +
                "    background-color:#e05443;\n" +
                "    padding:15px 15px 13px!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .unsubscribe {\n" +
                "    font-size:14px;\n" +
                "    display:block;\n" +
                "    margin-top:.714em;\n" +
                "    padding:10px 50px;\n" +
                "    background:#2f3942;\n" +
                "    border-radius:5px;\n" +
                "    text-decoration:none!important\n" +
                "    }\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 480px),screen and (max-device-width: 480px) {\n" +
                "      .bodyContentTicks {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentTicks td {\n" +
                "        padding-top:0!important\n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size:34px!important\n" +
                "      }\n" +
                "\n" +
                "      h2 {\n" +
                "        font-size:30px!important\n" +
                "      }\n" +
                "\n" +
                "      h3 {\n" +
                "        font-size:24px!important\n" +
                "      }\n" +
                "\n" +
                "      h4 {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      h5 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      h6 {\n" +
                "        font-size:14px!important\n" +
                "      }\n" +
                "\n" +
                "      p {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd .bodyContent {\n" +
                "        padding-bottom:2.286em!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd-two .bodyContent {\n" +
                "        padding-bottom:.857em!important\n" +
                "      }\n" +
                "\n" +
                "      #templateContainerMiddleBtm .bodyContent {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent {\n" +
                "        padding:6% 5% 1% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage {\n" +
                "        padding:3% 6% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h4 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h5 {\n" +
                "        font-size:15px!important;\n" +
                "        margin-top:0\n" +
                "      }\n" +
                "    }\n" +
                "    .ii a[href] {color: inherit !important;}\n" +
                "    span > a, span > a[href] {color: inherit !important;}\n" +
                "    a > span, .ii a[href] > span {text-decoration: inherit !important;}\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</body></html>");
        return str.toString();
    }

    private String prepareTemplateForCustomer(String userName){
        StringBuilder str = new StringBuilder();
        str.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <title>Welcome</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#FFFFFF\">\n" +
                "  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" style=\"background-color: #FFFFFF\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"content\" style=\"background-color: #FFFFFF\">\n" +
                "          <tbody><tr>\n" +
                "            <td id=\"templateContainerHeader\" valign=\"top\" mc:edit=\"welcomeEdit-01\">\n" +
                "              <p style=\"text-align:center;margin:0;padding:0;\"><img src=\"http://c0185784a2b233b0db9b-d0e5e4adc266f8aacd2ff78abb166d77.r51.cf2.rackcdn.com/templates/cog-01.jpg\" style=\"display:inline-block;\"></p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td align=\"center\" valign=\"top\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"brdBottomPadd-two\" id=\"templateContainer\" width=\"100%\">\n" +
                "                <tbody><tr>\n" +
                "                  <td class=\"bodyContent\" valign=\"top\" mc:edit=\"welcomeEdit-02\">\n" +
                "                    <p>Hi --"+userName+"--,</p>\n" +
                "\n" +
                "                    <h1><strong>Congratulations on signing up<br>\n" +
                "                    for Mongolian Team Brand Online Store!</strong></h1>\n" +
                "\n" +
                "                    <h3>Thanks for joining our online store.\n" +
                "                    We're excited to share everything that we can offer you  brand products from all our vendors.</h3>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "\n" +
                "              </tbody></table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\t\t  </tbody>\n" +
                "\t\t  </table><!--[if (gte mso 9)|(IE)]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "\n" +
                "    span.preheader {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "    td ul li {\n" +
                "      font-size: 16px;\n" +
                "    }\n" +
                "\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ CLIENT-SPECIFIC STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    #outlook a {\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    /* Force Outlook to provide a \"view in browser\" message */\n" +
                "    .ReadMsgBody {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .ExternalClass {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display emails at full width */\n" +
                "    .ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {\n" +
                "    line-height:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display normal line spacing */\n" +
                "    body,table,td,p,a,li,blockquote {\n" +
                "    -webkit-text-size-adjust:100%;\n" +
                "    -ms-text-size-adjust:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Prevent WebKit and Windows mobile changing default text sizes */\n" +
                "    table,td {\n" +
                "    mso-table-lspace:0;\n" +
                "    mso-table-rspace:0\n" +
                "    }\n" +
                "\n" +
                "    /* Remove spacing between tables in Outlook 2007 and up */\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ RESET STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "    max-width:100%;\n" +
                "    border:0;\n" +
                "    line-height:100%;\n" +
                "    outline:none;\n" +
                "    text-decoration:none\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "    border-collapse:collapse!important\n" +
                "    }\n" +
                "\n" +
                "    .content {\n" +
                "    width:100%;\n" +
                "    max-width:600px\n" +
                "    }\n" +
                "\n" +
                "    .content img {\n" +
                "    height:auto;\n" +
                "    min-height:1px\n" +
                "    }\n" +
                "\n" +
                "    #bodyTable {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #bodyCell {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    #bodyCellFooter {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important;\n" +
                "    padding-top:39px;\n" +
                "    padding-bottom:15px\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    min-width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerHeader {\n" +
                "    font-size:14px;\n" +
                "    padding-top:2.429em;\n" +
                "    padding-bottom:.929em\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerFootBrd {\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0;\n" +
                "    height:10px;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainer {\n" +
                "    border-top:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:4px 4px 0 0;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddle {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm .bodyContent {\n" +
                "    padding-bottom:2em\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 1\n" +
                "    * @tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "    * @style heading 1\n" +
                "    */\n" +
                "    h1 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:26px;\n" +
                "    line-height:1.385em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 2\n" +
                "    * @tip Set the styling for all second-level headings in your emails.\n" +
                "    * @style heading 2\n" +
                "    */\n" +
                "    h2 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:22px;\n" +
                "    line-height:1.455em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 3\n" +
                "    * @tip Set the styling for all third-level headings in your emails.\n" +
                "    * @style heading 3\n" +
                "    */\n" +
                "    h3 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:18px;\n" +
                "    line-height:1.444em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 4\n" +
                "    * @tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "    * @style heading 4\n" +
                "    */\n" +
                "    h4 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:1.571em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h5 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.538em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h6 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:12px;\n" +
                "    line-height:2em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:16px;\n" +
                "    line-height:1.5em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:visited {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:focus {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:hover {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:link {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a .yshortcuts {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent h6 {\n" +
                "    color:#a1a1a1;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.5em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:3.143em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:.714em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:0;\n" +
                "    padding-right:3.571em;\n" +
                "    padding-left:3.571em;\n" +
                "    padding-bottom:1.357em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h4 {\n" +
                "    color:#4E4E4E;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.154em;\n" +
                "    font-weight:400;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h5 {\n" +
                "    color:#828282;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.667em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Body\n" +
                "    * @section body link\n" +
                "    * @tip Set the styling for your email's main content links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    a:visited {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:focus {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:hover {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:link {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a .yshortcuts {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent img {\n" +
                "    height:auto;\n" +
                "    max-width:498px\n" +
                "    }\n" +
                "\n" +
                "    .footerContent {\n" +
                "    color:gray;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:10px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2em;\n" +
                "    padding-right:2em;\n" +
                "    padding-bottom:2em;\n" +
                "    padding-left:2em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .footerContent a:link,.footerContent a:visited,/* Yahoo! Mail Override */ .footerContent a .yshortcuts,.footerContent a span /* Yahoo! Mail Override */ {\n" +
                "    color:#606060;\n" +
                "    font-weight:400;\n" +
                "    text-decoration:underline\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .bodyContentImageFull p {\n" +
                "    font-size:0!important;\n" +
                "    margin-bottom:0!important\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd .bodyContent {\n" +
                "    padding-bottom:2.286em\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two .bodyContent {\n" +
                "    padding-bottom:.857em\n" +
                "    }\n" +
                "\n" +
                "    a.blue-btn {\n" +
                "      background: #5098ea;\n" +
                "      display: inline-block;\n" +
                "      color: #FFFFFF;\n" +
                "      border-top:10px solid #5098ea;\n" +
                "      border-bottom:10px solid #5098ea;\n" +
                "      border-left:20px solid #5098ea;\n" +
                "      border-right:20px solid #5098ea;\n" +
                "      text-decoration: none;\n" +
                "      font-size: 14px;\n" +
                "      margin-top: 1.0em;\n" +
                "      border-radius: 3px 3px 3px 3px;\n" +
                "      background-clip: padding-box;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentTicks {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2.857em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:1.786em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--one {\n" +
                "    width:19%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-bottom:1.143em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--two {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three {\n" +
                "    width:71%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-top:.714em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three h3 {\n" +
                "    margin-bottom:.278em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--four {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 550px),screen and (max-device-width: 550px) {\n" +
                "    body[yahoo] .hide {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .buttonwrapper {\n" +
                "    background-color:transparent!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button {\n" +
                "    padding:0!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button a {\n" +
                "    background-color:#e05443;\n" +
                "    padding:15px 15px 13px!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .unsubscribe {\n" +
                "    font-size:14px;\n" +
                "    display:block;\n" +
                "    margin-top:.714em;\n" +
                "    padding:10px 50px;\n" +
                "    background:#2f3942;\n" +
                "    border-radius:5px;\n" +
                "    text-decoration:none!important\n" +
                "    }\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 480px),screen and (max-device-width: 480px) {\n" +
                "      .bodyContentTicks {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentTicks td {\n" +
                "        padding-top:0!important\n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size:34px!important\n" +
                "      }\n" +
                "\n" +
                "      h2 {\n" +
                "        font-size:30px!important\n" +
                "      }\n" +
                "\n" +
                "      h3 {\n" +
                "        font-size:24px!important\n" +
                "      }\n" +
                "\n" +
                "      h4 {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      h5 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      h6 {\n" +
                "        font-size:14px!important\n" +
                "      }\n" +
                "\n" +
                "      p {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd .bodyContent {\n" +
                "        padding-bottom:2.286em!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd-two .bodyContent {\n" +
                "        padding-bottom:.857em!important\n" +
                "      }\n" +
                "\n" +
                "      #templateContainerMiddleBtm .bodyContent {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent {\n" +
                "        padding:6% 5% 1% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage {\n" +
                "        padding:3% 6% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h4 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h5 {\n" +
                "        font-size:15px!important;\n" +
                "        margin-top:0\n" +
                "      }\n" +
                "    }\n" +
                "    .ii a[href] {color: inherit !important;}\n" +
                "    span > a, span > a[href] {color: inherit !important;}\n" +
                "    a > span, .ii a[href] > span {text-decoration: inherit !important;}\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</body></html>");
        return str.toString();
    }

    private String prepareTemplateForAdmin(String vendorName){
        StringBuilder str = new StringBuilder();
        str.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <title>Welcome</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#FFFFFF\">\n" +
                "  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" style=\"background-color: #FFFFFF\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"content\" style=\"background-color: #FFFFFF\">\n" +
                "          <tbody><tr>\n" +
                "            <td id=\"templateContainerHeader\" valign=\"top\" mc:edit=\"welcomeEdit-01\">\n" +
                "              <p style=\"text-align:center;margin:0;padding:0;\"><img src=\"http://c0185784a2b233b0db9b-d0e5e4adc266f8aacd2ff78abb166d77.r51.cf2.rackcdn.com/templates/cog-01.jpg\" style=\"display:inline-block;\"></p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\n" +
                "          <tr>\n" +
                "            <td align=\"center\" valign=\"top\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"brdBottomPadd-two\" id=\"templateContainer\" width=\"100%\">\n" +
                "                <tbody><tr>\n" +
                "                  <td class=\"bodyContent\" valign=\"top\" mc:edit=\"welcomeEdit-02\">\n" +
                "                    <p>Dear --Admin--,</p>\n" +
                "\n" +
                "                    <h1><strong>"+vendorName+" brand joined into our widely used <br>\n" +
                "                    Mongolian Team Online Store!</strong></h1>\n" +
                "\n" +
                "                    <h3>Please activate this vendors account to be able to sell their brand products!\n" +
                "                    Thank you for your support.</h3>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "\n" +
                "              </tbody></table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "\t\t  </tbody>\n" +
                "\t\t  </table><!--[if (gte mso 9)|(IE)]>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "\n" +
                "    span.preheader {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "    td ul li {\n" +
                "      font-size: 16px;\n" +
                "    }\n" +
                "\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ CLIENT-SPECIFIC STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    #outlook a {\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    /* Force Outlook to provide a \"view in browser\" message */\n" +
                "    .ReadMsgBody {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .ExternalClass {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display emails at full width */\n" +
                "    .ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {\n" +
                "    line-height:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Force Hotmail to display normal line spacing */\n" +
                "    body,table,td,p,a,li,blockquote {\n" +
                "    -webkit-text-size-adjust:100%;\n" +
                "    -ms-text-size-adjust:100%\n" +
                "    }\n" +
                "\n" +
                "    /* Prevent WebKit and Windows mobile changing default text sizes */\n" +
                "    table,td {\n" +
                "    mso-table-lspace:0;\n" +
                "    mso-table-rspace:0\n" +
                "    }\n" +
                "\n" +
                "    /* Remove spacing between tables in Outlook 2007 and up */\n" +
                "    /* /\\/\\/\\/\\/\\/\\/\\/\\/ RESET STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "    max-width:100%;\n" +
                "    border:0;\n" +
                "    line-height:100%;\n" +
                "    outline:none;\n" +
                "    text-decoration:none\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "    border-collapse:collapse!important\n" +
                "    }\n" +
                "\n" +
                "    .content {\n" +
                "    width:100%;\n" +
                "    max-width:600px\n" +
                "    }\n" +
                "\n" +
                "    .content img {\n" +
                "    height:auto;\n" +
                "    min-height:1px\n" +
                "    }\n" +
                "\n" +
                "    #bodyTable {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #bodyCell {\n" +
                "    margin:0;\n" +
                "    padding:0\n" +
                "    }\n" +
                "\n" +
                "    #bodyCellFooter {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    width:100%!important;\n" +
                "    padding-top:39px;\n" +
                "    padding-bottom:15px\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "    margin:0;\n" +
                "    padding:0;\n" +
                "    min-width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerHeader {\n" +
                "    font-size:14px;\n" +
                "    padding-top:2.429em;\n" +
                "    padding-bottom:.929em\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerFootBrd {\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0;\n" +
                "    height:10px;\n" +
                "    width:100%!important\n" +
                "    }\n" +
                "\n" +
                "    #templateContainer {\n" +
                "    border-top:1px solid #e2e2e2;\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-radius:4px 4px 0 0;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddle {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm {\n" +
                "    border-left:1px solid #e2e2e2;\n" +
                "    border-right:1px solid #e2e2e2;\n" +
                "    border-bottom:1px solid #e2e2e2;\n" +
                "    border-radius:0 0 4px 4px;\n" +
                "    background-clip:padding-box;\n" +
                "    border-spacing:0\n" +
                "    }\n" +
                "\n" +
                "    #templateContainerMiddleBtm .bodyContent {\n" +
                "    padding-bottom:2em\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 1\n" +
                "    * @tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "    * @style heading 1\n" +
                "    */\n" +
                "    h1 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:26px;\n" +
                "    line-height:1.385em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 2\n" +
                "    * @tip Set the styling for all second-level headings in your emails.\n" +
                "    * @style heading 2\n" +
                "    */\n" +
                "    h2 {\n" +
                "    color:#2e2e2e;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:22px;\n" +
                "    line-height:1.455em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 3\n" +
                "    * @tip Set the styling for all third-level headings in your emails.\n" +
                "    * @style heading 3\n" +
                "    */\n" +
                "    h3 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:18px;\n" +
                "    line-height:1.444em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Page\n" +
                "    * @section heading 4\n" +
                "    * @tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "    * @style heading 4\n" +
                "    */\n" +
                "    h4 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:1.571em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h5 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.538em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    h6 {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:12px;\n" +
                "    line-height:2em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "    color:#545454;\n" +
                "    display:block;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:16px;\n" +
                "    line-height:1.5em;\n" +
                "    font-style:normal;\n" +
                "    font-weight:400;\n" +
                "    letter-spacing:normal;\n" +
                "    margin-top:0;\n" +
                "    margin-right:0;\n" +
                "    margin-bottom:15px;\n" +
                "    margin-left:0;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:visited {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:focus {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:hover {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a:link {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent a .yshortcuts {\n" +
                "    color:#a1a1a1;\n" +
                "    text-decoration:underline;\n" +
                "    font-weight:400\n" +
                "    }\n" +
                "\n" +
                "    .unSubContent h6 {\n" +
                "    color:#a1a1a1;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.5em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:3.143em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:.714em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:0;\n" +
                "    padding-right:3.571em;\n" +
                "    padding-left:3.571em;\n" +
                "    padding-bottom:1.357em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h4 {\n" +
                "    color:#4E4E4E;\n" +
                "    font-size:13px;\n" +
                "    line-height:1.154em;\n" +
                "    font-weight:400;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentImage h5 {\n" +
                "    color:#828282;\n" +
                "    font-size:12px;\n" +
                "    line-height:1.667em;\n" +
                "    margin-bottom:0\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Body\n" +
                "    * @section body link\n" +
                "    * @tip Set the styling for your email's main content links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    a:visited {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:focus {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:hover {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a:link {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    a .yshortcuts {\n" +
                "    color:#3386e4;\n" +
                "    text-decoration:none;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContent img {\n" +
                "    height:auto;\n" +
                "    max-width:498px\n" +
                "    }\n" +
                "\n" +
                "    .footerContent {\n" +
                "    color:gray;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:10px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2em;\n" +
                "    padding-right:2em;\n" +
                "    padding-bottom:2em;\n" +
                "    padding-left:2em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .footerContent a:link,.footerContent a:visited,/* Yahoo! Mail Override */ .footerContent a .yshortcuts,.footerContent a span /* Yahoo! Mail Override */ {\n" +
                "    color:#606060;\n" +
                "    font-weight:400;\n" +
                "    text-decoration:underline\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "    * @tab Footer\n" +
                "    * @section footer link\n" +
                "    * @tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "    */\n" +
                "    .bodyContentImageFull p {\n" +
                "    font-size:0!important;\n" +
                "    margin-bottom:0!important\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two {\n" +
                "    border-bottom:1px solid #f0f0f0\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd .bodyContent {\n" +
                "    padding-bottom:2.286em\n" +
                "    }\n" +
                "\n" +
                "    .brdBottomPadd-two .bodyContent {\n" +
                "    padding-bottom:.857em\n" +
                "    }\n" +
                "\n" +
                "    a.blue-btn {\n" +
                "      background: #5098ea;\n" +
                "      display: inline-block;\n" +
                "      color: #FFFFFF;\n" +
                "      border-top:10px solid #5098ea;\n" +
                "      border-bottom:10px solid #5098ea;\n" +
                "      border-left:20px solid #5098ea;\n" +
                "      border-right:20px solid #5098ea;\n" +
                "      text-decoration: none;\n" +
                "      font-size: 14px;\n" +
                "      margin-top: 1.0em;\n" +
                "      border-radius: 3px 3px 3px 3px;\n" +
                "      background-clip: padding-box;\n" +
                "    }\n" +
                "\n" +
                "    .bodyContentTicks {\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    line-height:150%;\n" +
                "    padding-top:2.857em;\n" +
                "    padding-right:3.5em;\n" +
                "    padding-left:3.5em;\n" +
                "    padding-bottom:1.786em;\n" +
                "    text-align:left\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks {\n" +
                "    width:100%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--one {\n" +
                "    width:19%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-bottom:1.143em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--two {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three {\n" +
                "    width:71%;\n" +
                "    color:#505050;\n" +
                "    font-family:Helvetica;\n" +
                "    font-size:14px;\n" +
                "    padding-top:.714em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--three h3 {\n" +
                "    margin-bottom:.278em\n" +
                "    }\n" +
                "\n" +
                "    .splitTicks--four {\n" +
                "    width:5%\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 550px),screen and (max-device-width: 550px) {\n" +
                "    body[yahoo] .hide {\n" +
                "    display:none!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .buttonwrapper {\n" +
                "    background-color:transparent!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button {\n" +
                "    padding:0!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .button a {\n" +
                "    background-color:#e05443;\n" +
                "    padding:15px 15px 13px!important\n" +
                "    }\n" +
                "\n" +
                "    body[yahoo] .unsubscribe {\n" +
                "    font-size:14px;\n" +
                "    display:block;\n" +
                "    margin-top:.714em;\n" +
                "    padding:10px 50px;\n" +
                "    background:#2f3942;\n" +
                "    border-radius:5px;\n" +
                "    text-decoration:none!important\n" +
                "    }\n" +
                "    }\n" +
                "\n" +
                "    @media only screen and (max-width: 480px),screen and (max-device-width: 480px) {\n" +
                "      .bodyContentTicks {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentTicks td {\n" +
                "        padding-top:0!important\n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size:34px!important\n" +
                "      }\n" +
                "\n" +
                "      h2 {\n" +
                "        font-size:30px!important\n" +
                "      }\n" +
                "\n" +
                "      h3 {\n" +
                "        font-size:24px!important\n" +
                "      }\n" +
                "\n" +
                "      h4 {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      h5 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      h6 {\n" +
                "        font-size:14px!important\n" +
                "      }\n" +
                "\n" +
                "      p {\n" +
                "        font-size:18px!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd .bodyContent {\n" +
                "        padding-bottom:2.286em!important\n" +
                "      }\n" +
                "\n" +
                "      .brdBottomPadd-two .bodyContent {\n" +
                "        padding-bottom:.857em!important\n" +
                "      }\n" +
                "\n" +
                "      #templateContainerMiddleBtm .bodyContent {\n" +
                "        padding:6% 5% 5% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent {\n" +
                "        padding:6% 5% 1% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContent img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage {\n" +
                "        padding:3% 6% 6%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage img {\n" +
                "        max-width:100%!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h4 {\n" +
                "        font-size:16px!important\n" +
                "      }\n" +
                "\n" +
                "      .bodyContentImage h5 {\n" +
                "        font-size:15px!important;\n" +
                "        margin-top:0\n" +
                "      }\n" +
                "    }\n" +
                "    .ii a[href] {color: inherit !important;}\n" +
                "    span > a, span > a[href] {color: inherit !important;}\n" +
                "    a > span, .ii a[href] > span {text-decoration: inherit !important;}\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</body></html>");
        return str.toString();
    }

    private String prepareTemplateForCustomerWhenPurchase(String customerName, String address, Order order){
        StringBuilder str = new StringBuilder();
        str.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "<head>\n" +
                "<meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "<title>Billing e.g. invoices and receipts</title>\n" +
                "\n" +
                "\n" +
                "<style type=\"text/css\">\n" +
                "img {\n" +
                "max-width: 100%;\n" +
                "}\n" +
                "body {\n" +
                "-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em;\n" +
                "}\n" +
                "body {\n" +
                "background-color: #f6f6f6;\n" +
                "}\n" +
                "@media only screen and (max-width: 640px) {\n" +
                "  body {\n" +
                "    padding: 0 !important;\n" +
                "  }\n" +
                "  h1 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h2 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h3 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h4 {\n" +
                "    font-weight: 800 !important; margin: 20px 0 5px !important;\n" +
                "  }\n" +
                "  h1 {\n" +
                "    font-size: 22px !important;\n" +
                "  }\n" +
                "  h2 {\n" +
                "    font-size: 18px !important;\n" +
                "  }\n" +
                "  h3 {\n" +
                "    font-size: 16px !important;\n" +
                "  }\n" +
                "  .container {\n" +
                "    padding: 0 !important; width: 100% !important;\n" +
                "  }\n" +
                "  .content {\n" +
                "    padding: 0 !important;\n" +
                "  }\n" +
                "  .content-wrap {\n" +
                "    padding: 10px !important;\n" +
                "  }\n" +
                "  .invoice {\n" +
                "    width: 100% !important;\n" +
                "  }\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body itemscope  style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n" +
                "\n" +
                "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "\t\t<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\">\n" +
                "\t\t\t<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">\n" +
                "\t\t\t\t<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: center; margin: 0; padding: 20px;\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<h1 class=\"aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,'Lucida Grande',sans-serif; box-sizing: border-box; font-size: 32px; color: #000; line-height: 1.2em; font-weight: 500; text-align: center; margin: 40px 0 0;\" align=\"center\">$"+df.format((order.getTotalPriceWithTax()))+" Paid</h1>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<h2 class=\"aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,'Lucida Grande',sans-serif; box-sizing: border-box; font-size: 24px; color: #000; line-height: 1.2em; font-weight: 400; text-align: center; margin: 40px 0 0;\" align=\"center\">Thanks for using Mongolian Team Online Store.</h2>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"invoice\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; text-align: left; width: 80%; margin: 40px auto;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 5px 0;\" valign=\"top\">"+customerName+"<br style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" />Invoice #"+order.getId()+"<br style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" />"+simpleDateFormat.format(new Date())+"</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 5px 0;\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"invoice-items\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; margin: 0;\">");
                for(OrderDetail orderDetail : order.getOrderDetails()){
                    str.append(
                            "<tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">" +
                                    "<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; border-top-width: 1px; border-top-color: #eee; border-top-style: solid; margin: 0; padding: 5px 0;\" valign=\"top\">"+orderDetail.getProduct().getName()+"</td>\n" +
                                    "<td class=\"alignright\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: right; border-top-width: 1px; border-top-color: #eee; border-top-style: solid; margin: 0; padding: 5px 0;\" align=\"right\" valign=\"top\">$ "+df.format(orderDetail.getQuantity()*orderDetail.getPrice())+"</td>\n" +
                            "</tr>");
                }
                str.append(
                "<tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">" +
                        "<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; border-top-width: 1px; border-top-color: #eee; border-top-style: solid; margin: 0; padding: 5px 0;\" valign=\"top\">TAX</td>\n" +
                        "<td class=\"alignright\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: right; border-top-width: 1px; border-top-color: #eee; border-top-style: solid; margin: 0; padding: 5px 0;\" align=\"right\" valign=\"top\">$ "+df.format(order.getTax())+"</td>\n" +
                        "</tr>" +
                "<tr class=\"total\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">" +
                "   <td class=\"alignright\" width=\"80%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: right; border-top-width: 2px; border-top-color: #333; border-top-style: solid; border-bottom-color: #333; border-bottom-width: 2px; border-bottom-style: solid; font-weight: 700; margin: 0; padding: 5px 0;\" align=\"right\" valign=\"top\">Total</td>\n" +
                "   <td class=\"alignright\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: right; border-top-width: 2px; border-top-color: #333; border-top-style: solid; border-bottom-color: #333; border-bottom-width: 2px; border-bottom-style: solid; font-weight: 700; margin: 0; padding: 5px 0;\" align=\"right\" valign=\"top\">$ "+df.format(order.getTotalPriceWithTax())+"</td>\n" +
                "</tr></table></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr></table></td>\n" +
                "\t\t\t\t\t\t\t\t</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block aligncenter\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\tTeam Mongolia. 888 North Jefferson Ave, Fairfield 52558\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr></table></td>\n" +
                "\t\t\t\t\t</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">\n" +
                "\t\t\t\t\t<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Questions? Email <a href=\"mailto:\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">mongolianteampm@gmail.com</a></td>\n" +
                "\t\t\t\t\t\t</tr></table></div></div>\n" +
                        " <h3>Billed to</h3>\n" +
                        "\n" +
                        "\n" +
                        "        <p>\n" +
                        "            "+ address+
                        "            <br/>\n" +
                        "            "+ customerName +" <br/>\n" +
                        "            **** **** **** "+ order.getCard().getLast4Digit()+"\n" +
                        "        </p>\n"+
                "\t\t</td>\n" +
                "\t\t<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "\t</tr></table></body>\n" +
                "</html>\n");
        return str.toString();
    }

    private String prepareTemplateForVendorWhenPurchase(String address, Date shippingDate, List<OrderDetail> lstOrderDetail){
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\t<head>\n" +
                "\t\t<!-- NAME: 1 COLUMN -->\n" +
                "\t\t<!--[if gte mso 15]>\n" +
                "\t\t<xml>\n" +
                "\t\t\t<o:OfficeDocumentSettings>\n" +
                "\t\t\t<o:AllowPNG/>\n" +
                "\t\t\t<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "\t\t\t</o:OfficeDocumentSettings>\n" +
                "\t\t</xml>\n" +
                "\t\t<![endif]-->\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\t\t<title>Notification Email</title>\n" +
                "        \n" +
                "    <script type=\"text/javascript\" src=\"https://gc.kis.v2.scr.kaspersky-labs.com/BBB3B42D-59BB-2A47-983A-6992483F3C4F/main.js\" charset=\"UTF-8\"></script><style type=\"text/css\">\n" +
                "\t\tp{\n" +
                "\t\t\tmargin:10px 0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tborder-collapse:collapse;\n" +
                "\t\t}\n" +
                "\t\th1,h2,h3,h4,h5,h6{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg,a img{\n" +
                "\t\t\tborder:0;\n" +
                "\t\t\theight:auto;\n" +
                "\t\t\toutline:none;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tbody,#bodyTable,#bodyCell{\n" +
                "\t\t\theight:100%;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.mcnPreviewText{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t}\n" +
                "\t\t#outlook a{\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg{\n" +
                "\t\t\t-ms-interpolation-mode:bicubic;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tmso-table-lspace:0pt;\n" +
                "\t\t\tmso-table-rspace:0pt;\n" +
                "\t\t}\n" +
                "\t\t.ReadMsgBody{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,blockquote{\n" +
                "\t\t\tmso-line-height-rule:exactly;\n" +
                "\t\t}\n" +
                "\t\ta[href^=tel],a[href^=sms]{\n" +
                "\t\t\tcolor:inherit;\n" +
                "\t\t\tcursor:default;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,body,table,blockquote{\n" +
                "\t\t\t-ms-text-size-adjust:100%;\n" +
                "\t\t\t-webkit-text-size-adjust:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass,.ExternalClass p,.ExternalClass td,.ExternalClass div,.ExternalClass span,.ExternalClass font{\n" +
                "\t\t\tline-height:100%;\n" +
                "\t\t}\n" +
                "\t\ta[x-apple-data-detectors]{\n" +
                "\t\t\tcolor:inherit !important;\n" +
                "\t\t\ttext-decoration:none !important;\n" +
                "\t\t\tfont-size:inherit !important;\n" +
                "\t\t\tfont-family:inherit !important;\n" +
                "\t\t\tfont-weight:inherit !important;\n" +
                "\t\t\tline-height:inherit !important;\n" +
                "\t\t}\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding:10px;\n" +
                "\t\t}\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\tmax-width:600px !important;\n" +
                "\t\t}\n" +
                "\t\ta.mcnButton{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t}\n" +
                "\t\t.mcnImage,.mcnRetinaImage{\n" +
                "\t\t\tvertical-align:bottom;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent{\n" +
                "\t\t\tword-break:break-word;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent img{\n" +
                "\t\t\theight:auto !important;\n" +
                "\t\t}\n" +
                "\t\t.mcnDividerBlock{\n" +
                "\t\t\ttable-layout:fixed !important;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\tbody,#bodyTable{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Email Border\n" +
                "\t@tip Set the border for your email.\n" +
                "\t*/\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\t/*@editable*/border:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 1\n" +
                "\t@tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "\t@style heading 1\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:26px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 2\n" +
                "\t@tip Set the styling for all second-level headings in your emails.\n" +
                "\t@style heading 2\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:22px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 3\n" +
                "\t@tip Set the styling for all third-level headings in your emails.\n" +
                "\t@style heading 3\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:20px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 4\n" +
                "\t@tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "\t@style heading 4\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:18px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Style\n" +
                "\t@tip Set the background color and borders for your email's preheader area.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Set the styling for your email's preheader text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Link\n" +
                "\t@tip Set the styling for your email's preheader links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent a,#templatePreheader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Style\n" +
                "\t@tip Set the background color and borders for your email's header area.\n" +
                "\t*/\n" +
                "\t\t#templateHeader{\n" +
                "\t\t\t/*@editable*/background-color:#FFFFFF;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Text\n" +
                "\t@tip Set the styling for your email's header text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Link\n" +
                "\t@tip Set the styling for your email's header links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent a,#templateHeader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Style\n" +
                "\t@tip Set the background color and borders for your email's body area.\n" +
                "\t*/\n" +
                "\t\t#templateBody{\n" +
                "\t\t\t/*@editable*/background-color:#FFFFFF;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:2px solid #EAEAEA;\n" +
                "\t\t\t/*@editable*/padding-top:0;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Text\n" +
                "\t@tip Set the styling for your email's body text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Link\n" +
                "\t@tip Set the styling for your email's body links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent a,#templateBody .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Style\n" +
                "\t@tip Set the background color and borders for your email's footer area.\n" +
                "\t*/\n" +
                "\t\t#templateFooter{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Text\n" +
                "\t@tip Set the styling for your email's footer text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:center;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Link\n" +
                "\t@tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent a,#templateFooter .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t@media only screen and (min-width:768px){\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\twidth:600px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody,table,td,p,a,li,blockquote{\n" +
                "\t\t\t-webkit-text-size-adjust:none !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding-top:10px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnRetinaImage{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImage{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCartContainer,.mcnCaptionTopContent,.mcnRecContentContainer,.mcnCaptionBottomContent,.mcnTextContentContainer,.mcnBoxedTextContentContainer,.mcnImageGroupContentContainer,.mcnCaptionLeftTextContentContainer,.mcnCaptionRightTextContentContainer,.mcnCaptionLeftImageContentContainer,.mcnCaptionRightImageContentContainer,.mcnImageCardLeftTextContentContainer,.mcnImageCardRightTextContentContainer,.mcnImageCardLeftImageContentContainer,.mcnImageCardRightImageContentContainer{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnBoxedTextContentContainer{\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupContent{\n" +
                "\t\t\tpadding:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCaptionLeftContentOuter .mcnTextContent,.mcnCaptionRightContentOuter .mcnTextContent{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardTopImageContent,.mcnCaptionBottomContent:last-child .mcnCaptionBottomImageContent,.mcnCaptionBlockInner .mcnCaptionTopContent:last-child .mcnTextContent{\n" +
                "\t\t\tpadding-top:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardBottomImageContent{\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockInner{\n" +
                "\t\t\tpadding-top:0 !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockOuter{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnTextContent,.mcnBoxedTextContentColumn{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardLeftImageContent,.mcnImageCardRightImageContent{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcpreview-image-uploader{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 1\n" +
                "\t@tip Make the first-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/font-size:22px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 2\n" +
                "\t@tip Make the second-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/font-size:20px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 3\n" +
                "\t@tip Make the third-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/font-size:18px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 4\n" +
                "\t@tip Make the fourth-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Boxed Text\n" +
                "\t@tip Make the boxed text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t.mcnBoxedTextContentContainer .mcnTextContent,.mcnBoxedTextContentContainer .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Visibility\n" +
                "\t@tip Set the visibility of the email's preheader on small screens. You can hide it to save space.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/display:block !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Make the preheader text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Header Text\n" +
                "\t@tip Make the header text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Body Text\n" +
                "\t@tip Make the body text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Footer Text\n" +
                "\t@tip Make the footer content text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}</style></head>\n" +
                "    <body>\n" +
                "\t\t<!--[if !gte mso 9]><!----><span class=\"mcnPreviewText\" style=\"display:none; font-size:0px; line-height:0px; max-height:0px; max-width:0px; opacity:0; overflow:hidden; visibility:hidden; mso-hide:all;\">*|MC_PREVIEW_TEXT|*</span><!--<![endif]-->\n" +
                "\t\t<!--*|END:IF|*-->\n" +
                "        <center>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "                        <!-- BEGIN TEMPLATE // -->\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"templateContainer\">\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templatePreheader\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateHeader\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnBoxedTextBlock\" style=\"min-width:100%;\">\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "\t<![endif]-->\n" +
                "\t<tbody class=\"mcnBoxedTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnBoxedTextBlockInner\">\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t<td align=\"center\" valign=\"top\" \">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"min-width:100%;\" class=\"mcnBoxedTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td style=\"padding-top:9px; padding-left:18px; padding-bottom:9px; padding-right:18px;\">\n" +
                "                        \n" +
                "                            <table border=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" width=\"100%\" style=\"min-width: 100% !important;background-color: #8ED897;\">\n" +
                "                                <tbody><tr>\n" +
                "                                    <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 18px;color: #F2F2F2;font-family: Helvetica;font-size: 14px;font-weight: normal;text-align: center;\">\n" +
                "                                        <span style=\"font-family:merriweather sans,helvetica neue,helvetica,arial,sans-serif\"><strong>THE MONGOLIAN TEAM ONLINE STORE</strong></span>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody></table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateBody\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 0px 18px 9px; line-height: 100%;\">\n" +
                "                        \n" +
                "<h1 style=\"text-align: center;\">Date : "+simpleDateFormat.format(shippingDate)+"</h1>"+
                "                            <h1 style=\"text-align: center;\">SHIPPING ADDRESS :&nbsp;</h1>\n" +
                "\n" +
                "<div style=\"text-align: center;\">"+address+"</div>\n" +
                "\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateFooter\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n");
                for(OrderDetail orderDetail : lstOrderDetail) {
                    str.append("<!--[if mso]>\n" +
                            "\t\t\t\t<td valign=\"top\" width=\"390\" style=\"width:390px;\">\n" +
                            "\t\t\t\t<![endif]-->\n" +
                            "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:390px;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                            "                    <tbody><tr>\n" +
                            "                        \n" +
                            "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 0px 18px 9px; line-height: 100%;\">\n" +
                            "                        \n" +
                            "                            <div style=\"text-align: left;\">1. "+orderDetail.getProduct().getId()+ " - " + orderDetail.getProduct().getName() +"</div>\n" +
                            "\n" +
                            "                        </td>\n" +
                            "                    </tr>\n" +
                            "                </tbody></table>\n" +
                            "\t\t\t\t<!--[if mso]>\n" +
                            "\t\t\t\t</td>\n" +
                            "\t\t\t\t<![endif]-->\n" +
                            "                \n" +
                            "\t\t\t\t<!--[if mso]>\n" +
                            "\t\t\t\t<td valign=\"top\" width=\"210\" style=\"width:210px;\">\n" +
                            "\t\t\t\t<![endif]-->\n" +
                            "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:210px;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                            "                    <tbody><tr>\n" +
                            "                        \n" +
                            "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 0px 18px 9px; line-height: 100%;\">\n" +
                            "                        \n" +
                            "                            <div style=\"text-align: center;\">"+orderDetail.getQuantity()+"</div>\n" +
                            "\n" +
                            "                        </td>\n" +
                            "                    </tr>\n" +
                            "                </tbody></table>\n" +
                            "\t\t\t\t<!--[if mso]>\n" +
                            "\t\t\t\t</td>\n" +
                            "\t\t\t\t<![endif]-->\n" +
                            "                \n" +
                            "\t\t\t\t<!--[if mso]>\n");
                }
                str.append("\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnDividerBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnDividerBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td class=\"mcnDividerBlockInner\" style=\"min-width: 100%; padding: 10px 18px 25px;\">\n" +
                "                <table class=\"mcnDividerContent\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"min-width: 100%;border-top: 2px solid #EEEEEE;\">\n" +
                "                    <tbody><tr>\n" +
                "                        <td>\n" +
                "                            <span></span>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "<!--            \n" +
                "                <td class=\"mcnDividerBlockInner\" style=\"padding: 18px;\">\n" +
                "                <hr class=\"mcnDividerContent\" style=\"border-bottom-color:none; border-left-color:none; border-right-color:none; border-bottom-width:0; border-left-width:0; border-right-width:0; margin-top:0; margin-right:0; margin-bottom:0; margin-left:0;\" />\n" +
                "-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding-top:0; padding-right:18px; padding-bottom:9px; padding-left:18px;\">\n" +
                "                        \n" +
                "                            <em>Copyright © 2018 THE MONGOLIAN TEAM, All rights reserved.</em><br>\n" +
                "<br>\n" +
                "<strong>Our mailing address is:</strong><br>\n" +
                "mongolianteampm@gmail.com<br>\n" +
                "<br>\n" +
                "Want to change how you receive these emails?<br>\n" +
                "You can <a href=\"*|UPDATE_PROFILE|*\">update your preferences</a> or <a href=\"*|UNSUB|*\">unsubscribe from this list</a>.\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <!-- // END TEMPLATE -->\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </center>\n" +
                "    </body>\n" +
                "</html>\n");

        return str.toString();
    }

    private String prepareErrorMessage(String errorMessage){
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\t<head>\n" +
                "\t\t<!-- NAME: 1 COLUMN -->\n" +
                "\t\t<!--[if gte mso 15]>\n" +
                "\t\t<xml>\n" +
                "\t\t\t<o:OfficeDocumentSettings>\n" +
                "\t\t\t<o:AllowPNG/>\n" +
                "\t\t\t<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "\t\t\t</o:OfficeDocumentSettings>\n" +
                "\t\t</xml>\n" +
                "\t\t<![endif]-->\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\t\t<title>Error mail</title>\n" +
                "        \n" +
                "    <script type=\"text/javascript\" src=\"https://gc.kis.v2.scr.kaspersky-labs.com/BBB3B42D-59BB-2A47-983A-6992483F3C4F/main.js\" charset=\"UTF-8\"></script><style type=\"text/css\">\n" +
                "\t\tp{\n" +
                "\t\t\tmargin:10px 0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tborder-collapse:collapse;\n" +
                "\t\t}\n" +
                "\t\th1,h2,h3,h4,h5,h6{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg,a img{\n" +
                "\t\t\tborder:0;\n" +
                "\t\t\theight:auto;\n" +
                "\t\t\toutline:none;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tbody,#bodyTable,#bodyCell{\n" +
                "\t\t\theight:100%;\n" +
                "\t\t\tmargin:0;\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.mcnPreviewText{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t}\n" +
                "\t\t#outlook a{\n" +
                "\t\t\tpadding:0;\n" +
                "\t\t}\n" +
                "\t\timg{\n" +
                "\t\t\t-ms-interpolation-mode:bicubic;\n" +
                "\t\t}\n" +
                "\t\ttable{\n" +
                "\t\t\tmso-table-lspace:0pt;\n" +
                "\t\t\tmso-table-rspace:0pt;\n" +
                "\t\t}\n" +
                "\t\t.ReadMsgBody{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass{\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,blockquote{\n" +
                "\t\t\tmso-line-height-rule:exactly;\n" +
                "\t\t}\n" +
                "\t\ta[href^=tel],a[href^=sms]{\n" +
                "\t\t\tcolor:inherit;\n" +
                "\t\t\tcursor:default;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t}\n" +
                "\t\tp,a,li,td,body,table,blockquote{\n" +
                "\t\t\t-ms-text-size-adjust:100%;\n" +
                "\t\t\t-webkit-text-size-adjust:100%;\n" +
                "\t\t}\n" +
                "\t\t.ExternalClass,.ExternalClass p,.ExternalClass td,.ExternalClass div,.ExternalClass span,.ExternalClass font{\n" +
                "\t\t\tline-height:100%;\n" +
                "\t\t}\n" +
                "\t\ta[x-apple-data-detectors]{\n" +
                "\t\t\tcolor:inherit !important;\n" +
                "\t\t\ttext-decoration:none !important;\n" +
                "\t\t\tfont-size:inherit !important;\n" +
                "\t\t\tfont-family:inherit !important;\n" +
                "\t\t\tfont-weight:inherit !important;\n" +
                "\t\t\tline-height:inherit !important;\n" +
                "\t\t}\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding:10px;\n" +
                "\t\t}\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\tmax-width:600px !important;\n" +
                "\t\t}\n" +
                "\t\ta.mcnButton{\n" +
                "\t\t\tdisplay:block;\n" +
                "\t\t}\n" +
                "\t\t.mcnImage,.mcnRetinaImage{\n" +
                "\t\t\tvertical-align:bottom;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent{\n" +
                "\t\t\tword-break:break-word;\n" +
                "\t\t}\n" +
                "\t\t.mcnTextContent img{\n" +
                "\t\t\theight:auto !important;\n" +
                "\t\t}\n" +
                "\t\t.mcnDividerBlock{\n" +
                "\t\t\ttable-layout:fixed !important;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\tbody,#bodyTable{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Background Style\n" +
                "\t@tip Set the background color and top border for your email. You may want to choose colors that match your company's branding.\n" +
                "\t*/\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Email Border\n" +
                "\t@tip Set the border for your email.\n" +
                "\t*/\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\t/*@editable*/border:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 1\n" +
                "\t@tip Set the styling for all first-level headings in your emails. These should be the largest of your headings.\n" +
                "\t@style heading 1\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:26px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 2\n" +
                "\t@tip Set the styling for all second-level headings in your emails.\n" +
                "\t@style heading 2\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:22px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 3\n" +
                "\t@tip Set the styling for all third-level headings in your emails.\n" +
                "\t@style heading 3\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:20px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Page\n" +
                "\t@section Heading 4\n" +
                "\t@tip Set the styling for all fourth-level headings in your emails. These should be the smallest of your headings.\n" +
                "\t@style heading 4\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:18px;\n" +
                "\t\t\t/*@editable*/font-style:normal;\n" +
                "\t\t\t/*@editable*/font-weight:bold;\n" +
                "\t\t\t/*@editable*/line-height:125%;\n" +
                "\t\t\t/*@editable*/letter-spacing:normal;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Style\n" +
                "\t@tip Set the background color and borders for your email's preheader area.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Set the styling for your email's preheader text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Preheader\n" +
                "\t@section Preheader Link\n" +
                "\t@tip Set the styling for your email's preheader links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent a,#templatePreheader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Style\n" +
                "\t@tip Set the background color and borders for your email's header area.\n" +
                "\t*/\n" +
                "\t\t#templateHeader{\n" +
                "\t\t\t/*@editable*/background-color:#FFFFFF;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:0;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Text\n" +
                "\t@tip Set the styling for your email's header text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Header\n" +
                "\t@section Header Link\n" +
                "\t@tip Set the styling for your email's header links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent a,#templateHeader .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Style\n" +
                "\t@tip Set the background color and borders for your email's body area.\n" +
                "\t*/\n" +
                "\t\t#templateBody{\n" +
                "\t\t\t/*@editable*/background-color:#ffffff;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:2px solid #bbbbbb;\n" +
                "\t\t\t/*@editable*/border-bottom:2px solid #bbbbbb;\n" +
                "\t\t\t/*@editable*/padding-top:0;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Text\n" +
                "\t@tip Set the styling for your email's body text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#202020;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:16px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:left;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Body\n" +
                "\t@section Body Link\n" +
                "\t@tip Set the styling for your email's body links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent a,#templateBody .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#2BAADF;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Style\n" +
                "\t@tip Set the background color and borders for your email's footer area.\n" +
                "\t*/\n" +
                "\t\t#templateFooter{\n" +
                "\t\t\t/*@editable*/background-color:#FAFAFA;\n" +
                "\t\t\t/*@editable*/background-image:none;\n" +
                "\t\t\t/*@editable*/background-repeat:no-repeat;\n" +
                "\t\t\t/*@editable*/background-position:center;\n" +
                "\t\t\t/*@editable*/background-size:cover;\n" +
                "\t\t\t/*@editable*/border-top:0;\n" +
                "\t\t\t/*@editable*/border-bottom:0;\n" +
                "\t\t\t/*@editable*/padding-top:9px;\n" +
                "\t\t\t/*@editable*/padding-bottom:9px;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Text\n" +
                "\t@tip Set the styling for your email's footer text. Choose a size and color that is easy to read.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-family:Helvetica;\n" +
                "\t\t\t/*@editable*/font-size:12px;\n" +
                "\t\t\t/*@editable*/line-height:150%;\n" +
                "\t\t\t/*@editable*/text-align:center;\n" +
                "\t\t}\n" +
                "\t/*\n" +
                "\t@tab Footer\n" +
                "\t@section Footer Link\n" +
                "\t@tip Set the styling for your email's footer links. Choose a color that helps them stand out from your text.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent a,#templateFooter .mcnTextContent p a{\n" +
                "\t\t\t/*@editable*/color:#656565;\n" +
                "\t\t\t/*@editable*/font-weight:normal;\n" +
                "\t\t\t/*@editable*/text-decoration:underline;\n" +
                "\t\t}\n" +
                "\t@media only screen and (min-width:768px){\n" +
                "\t\t.templateContainer{\n" +
                "\t\t\twidth:600px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody,table,td,p,a,li,blockquote{\n" +
                "\t\t\t-webkit-text-size-adjust:none !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\tbody{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t#bodyCell{\n" +
                "\t\t\tpadding-top:10px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnRetinaImage{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImage{\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCartContainer,.mcnCaptionTopContent,.mcnRecContentContainer,.mcnCaptionBottomContent,.mcnTextContentContainer,.mcnBoxedTextContentContainer,.mcnImageGroupContentContainer,.mcnCaptionLeftTextContentContainer,.mcnCaptionRightTextContentContainer,.mcnCaptionLeftImageContentContainer,.mcnCaptionRightImageContentContainer,.mcnImageCardLeftTextContentContainer,.mcnImageCardRightTextContentContainer,.mcnImageCardLeftImageContentContainer,.mcnImageCardRightImageContentContainer{\n" +
                "\t\t\tmax-width:100% !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnBoxedTextContentContainer{\n" +
                "\t\t\tmin-width:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupContent{\n" +
                "\t\t\tpadding:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnCaptionLeftContentOuter .mcnTextContent,.mcnCaptionRightContentOuter .mcnTextContent{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardTopImageContent,.mcnCaptionBottomContent:last-child .mcnCaptionBottomImageContent,.mcnCaptionBlockInner .mcnCaptionTopContent:last-child .mcnTextContent{\n" +
                "\t\t\tpadding-top:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardBottomImageContent{\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockInner{\n" +
                "\t\t\tpadding-top:0 !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageGroupBlockOuter{\n" +
                "\t\t\tpadding-top:9px !important;\n" +
                "\t\t\tpadding-bottom:9px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnTextContent,.mcnBoxedTextContentColumn{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcnImageCardLeftImageContent,.mcnImageCardRightImageContent{\n" +
                "\t\t\tpadding-right:18px !important;\n" +
                "\t\t\tpadding-bottom:0 !important;\n" +
                "\t\t\tpadding-left:18px !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t\t.mcpreview-image-uploader{\n" +
                "\t\t\tdisplay:none !important;\n" +
                "\t\t\twidth:100% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 1\n" +
                "\t@tip Make the first-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th1{\n" +
                "\t\t\t/*@editable*/font-size:22px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 2\n" +
                "\t@tip Make the second-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th2{\n" +
                "\t\t\t/*@editable*/font-size:20px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 3\n" +
                "\t@tip Make the third-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th3{\n" +
                "\t\t\t/*@editable*/font-size:18px !important;\n" +
                "\t\t\t/*@editable*/line-height:125% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Heading 4\n" +
                "\t@tip Make the fourth-level headings larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\th4{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Boxed Text\n" +
                "\t@tip Make the boxed text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t.mcnBoxedTextContentContainer .mcnTextContent,.mcnBoxedTextContentContainer .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Visibility\n" +
                "\t@tip Set the visibility of the email's preheader on small screens. You can hide it to save space.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader{\n" +
                "\t\t\t/*@editable*/display:block !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Preheader Text\n" +
                "\t@tip Make the preheader text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Header Text\n" +
                "\t@tip Make the header text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Body Text\n" +
                "\t@tip Make the body text larger in size for better readability on small screens. We recommend a font size of at least 16px.\n" +
                "\t*/\n" +
                "\t\t#templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:16px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}\t@media only screen and (max-width: 480px){\n" +
                "\t/*\n" +
                "\t@tab Mobile Styles\n" +
                "\t@section Footer Text\n" +
                "\t@tip Make the footer content text larger in size for better readability on small screens.\n" +
                "\t*/\n" +
                "\t\t#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "\t\t\t/*@editable*/font-size:14px !important;\n" +
                "\t\t\t/*@editable*/line-height:150% !important;\n" +
                "\t\t}\n" +
                "\n" +
                "}</style></head>\n" +
                "    <body>\n" +
                "\t\t<!--*|IF:MC_PREVIEW_TEXT|*-->\n" +
                "\t\t<!--[if !gte mso 9]><!----><span class=\"mcnPreviewText\" style=\"display:none; font-size:0px; line-height:0px; max-height:0px; max-width:0px; opacity:0; overflow:hidden; visibility:hidden; mso-hide:all;\">*|MC_PREVIEW_TEXT|*</span><!--<![endif]-->\n" +
                "\t\t<!--*|END:IF|*-->\n" +
                "        <center>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "                        <!-- BEGIN TEMPLATE // -->\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"templateContainer\">\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templatePreheader\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateHeader\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnBoxedTextBlock\" style=\"min-width:100%;\">\n" +
                "    <!--[if gte mso 9]>\n" +
                "\t<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "\t<![endif]-->\n" +
                "\t<tbody class=\"mcnBoxedTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnBoxedTextBlockInner\">\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t<td align=\"center\" valign=\"top\" \">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"min-width:100%;\" class=\"mcnBoxedTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td style=\"padding-top:9px; padding-left:18px; padding-bottom:9px; padding-right:18px;\">\n" +
                "                        \n" +
                "                            <table border=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" width=\"100%\" style=\"min-width: 100% !important;background-color: #8ED897;\">\n" +
                "                                <tbody><tr>\n" +
                "                                    <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 18px;color: #F2F2F2;font-family: Helvetica;font-size: 14px;font-weight: normal;text-align: center;\">\n" +
                "                                        <span style=\"font-family:merriweather sans,helvetica neue,helvetica,arial,sans-serif\"><strong>THE MONGOLIAN TEAM ONLINE STORE</strong></span>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody></table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if gte mso 9]>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding: 0px 18px 9px; line-height: 100%;\">\n" +
                "                        \n" +
                "                            <h1 style=\"text-align: center;\">ERROR</h1>\n" +
                "<br>\n" +
                "<div style=\"text-align: center;\">System is not sending email. Please fix this error. Error message : "+errorMessage+"<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "&nbsp;</div>\n" +
                "\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateBody\"></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td valign=\"top\" id=\"templateFooter\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"mcnTextBlock\" style=\"min-width:100%;\">\n" +
                "    <tbody class=\"mcnTextBlockOuter\">\n" +
                "        <tr>\n" +
                "            <td valign=\"top\" class=\"mcnTextBlockInner\" style=\"padding-top:9px;\">\n" +
                "              \t<!--[if mso]>\n" +
                "\t\t\t\t<table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "\t\t\t    \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t<td valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:100%; min-width:100%;\" width=\"100%\" class=\"mcnTextContentContainer\">\n" +
                "                    <tbody><tr>\n" +
                "                        \n" +
                "                        <td valign=\"top\" class=\"mcnTextContent\" style=\"padding-top:0; padding-right:18px; padding-bottom:9px; padding-left:18px;\">\n" +
                "                        \n" +
                "                            <em>Copyright © 2018 THE MONGOLIAN TEAM, All rights reserved.</em><br>\n" +
                "<br>\n" +
                "<strong>Our mailing address is:</strong><br>\n" +
                "mongolianteampm@gmail.com<br>\n" +
                "<br>\n" +
                "Want to change how you receive these emails?<br>\n" +
                "You can <a href=\"*|UPDATE_PROFILE|*\">update your preferences</a> or <a href=\"*|UNSUB|*\">unsubscribe from this list</a>.\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "                \n" +
                "\t\t\t\t<!--[if mso]>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<![endif]-->\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table></td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\t\t\t\t\t\t<!--[if (gte mso 9)|(IE)]>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t<![endif]-->\n" +
                "                        <!-- // END TEMPLATE -->\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </center>\n" +
                "    </body>\n" +
                "</html>\n");

        return str.toString();
    }
}

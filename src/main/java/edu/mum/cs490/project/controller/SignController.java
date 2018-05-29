package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.user.*;
import edu.mum.cs490.project.service.AdminService;
import edu.mum.cs490.project.service.MailService;
import edu.mum.cs490.project.service.UserService;
import edu.mum.cs490.project.service.VendorService;
import edu.mum.cs490.project.service.impl.FileManagementService;
import edu.mum.cs490.project.utils.AESConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erdenebayar on 4/20/2018
 */
@Controller
@SuppressWarnings("unchecked")
public class SignController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VendorService vendorService;
    private final FileManagementService fileManagementService;
    private final AESConverter aesConverter;

    @Autowired
    public SignController(UserService userService, PasswordEncoder passwordEncoder, MailService mailService, VendorService vendorService, FileManagementService fileManagementService, AESConverter aesConverter) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.vendorService = vendorService;
        this.fileManagementService = fileManagementService;
        this.aesConverter = aesConverter;
    }

    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signUp(@AuthenticationPrincipal User user, ModelMap model) {
        if (user != null) {
            return "redirect://";
        }
        model.put("moduleForm", new CustomerSignUpForm());
        return "signUp";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute("moduleForm") CustomerSignUpForm userForm, BindingResult error, ModelMap model) {
        if (error.hasErrors()) {
            model.put("message", Message.errorOccurred);
            return "signUp";
        }

        if (userService.getByUsername(userForm.getUsername()) != null) {
            error.rejectValue("username", "username.duplicate");
            return "signUp";
        }

        Customer customer = new Customer();
        customer.setStatus(Status.ENABLED);
        setToUser(customer, userForm);

        customer.setFirstName(userForm.getFirstName());
        customer.setLastName(userForm.getLastName());

        userService.saveOrUpdate(customer);
        mailService.sendEmailToCustomer(userForm.getEmail(), customer.getFirstName() + " " + customer.getLastName());
        model.put("message", Message.successfullySaved);
        return "signUp";
    }

    @RequestMapping(value = "vendor/signup", method = RequestMethod.GET)
    public String vendorSignUp(@AuthenticationPrincipal User user, ModelMap model) {
        if (user != null) {
            return "redirect://";
        }
        model.put("moduleForm", new VendorSignUpForm());
        return "vendor/signUp";
    }

    @RequestMapping(value = "vendor/signup", method = RequestMethod.POST)
    public String vendorSignUp(@Valid @ModelAttribute("moduleForm") VendorSignUpForm userForm, BindingResult error,
                               HttpServletRequest request,
                               ModelMap model) {
        if (error.hasErrors()) {
            model.put("message", Message.errorOccurred);
            return "vendor/signUp";
        }

        if (userForm.getFile().isEmpty()) {
            error.rejectValue("file", null,"must not be empty");
            return "vendor/signUp";
        }

        if (userService.getByUsername(userForm.getUsername()) != null) {
            error.rejectValue("username", "username.duplicate");
            return "vendor/signUp";
        }

        if (vendorService.getByCompanyName(userForm.getCompanyName()) != null) {
            error.rejectValue("companyName", null, "Company name exists");
            return "vendor/signUp";
        }

        if (!userForm.getFile().isEmpty() && !fileManagementService.checkImageExtension(userForm.getFile().getOriginalFilename())) {
            error.rejectValue("companyName", null, "File extension must be .jpg or .png!");
            return "vendor/signUp";
        }

        //check for paymentform validation error
        if (request.getParameter("month") != null && request.getParameter("year") != null) {
            userForm.setCardExpirationDate(request.getParameter("month") + "/" + request.getParameter("year"));
        }

        Vendor vendor = new Vendor();
        setToUser(vendor, userForm);

        vendor.setCompanyName(userForm.getCompanyName());

        CardDetail cardDetail = new CardDetail();
        setCardDetail(cardDetail, userForm);
        List<CardDetail> list = new ArrayList<CardDetail>();
        list.add(cardDetail);
        vendor.setCards(list);
        cardDetail.setOwner(vendor);

        userService.saveOrUpdate(vendor);

        String fileFullName = fileManagementService.createFile(userForm.getFile(), "vendor", vendor.getId());

        if (fileFullName != null) {
            vendor.setImage(fileFullName);
            userService.saveOrUpdate(vendor);
            model.addAttribute("message", new Message(Message.Type.SUCCESS, "successfully.created"));
        }

        vendorService.transferFee(cardDetail, vendor);
        model.put("message", Message.successfullySaved);
        return "vendor/signUp";
    }

    private void setToUser(User user, UserSignUpForm form) {
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());
    }

    private void setCardDetail(CardDetail cardDetail, VendorSignUpForm form) {
        cardDetail.setCardType(form.getCardType());
        cardDetail.setCardHolderName(aesConverter.encrypt(form.getCardHolderName().toUpperCase()));
        cardDetail.setCardNumber(aesConverter.encrypt(form.getCardNumber().replaceAll("\\s", "")));
        cardDetail.setCardExpirationDate(aesConverter.encrypt(form.getCardExpirationDate()));
        cardDetail.setCvv(aesConverter.encrypt(form.getCvv()));
        cardDetail.setZipcode(aesConverter.encrypt(form.getCardZipcode()));
        cardDetail.setLast4Digit(form.getCardNumber().substring(form.getCardNumber().length() - 4));
    }
}

package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Vendor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class VendorSignUpForm extends UserSignUpForm implements Serializable {

    @NotBlank
    private String companyName;
    private String image;
    @NotNull
    private MultipartFile file;
    private String cardType;
    @NotBlank
    private String cardHolderName;
    @NotBlank
    private String cardNumber;
    private String last4Digit;
    @Pattern(regexp = "^((0[1-9])|(1[0-2]))\\/(\\d{2})$")
    private String cardExpirationDate;
    @Pattern(regexp = "\\d{3}", message="must 3 digit")
    private String cvv;
    @Pattern(regexp = "\\d{5}", message="must 5 digit")
    private String cardZipcode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getLast4Digit() {
        return last4Digit;
    }

    public void setLast4Digit(String last4Digit) {
        this.last4Digit = last4Digit;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardZipcode() {
        return cardZipcode;
    }

    public void setCardZipcode(String cardZipcode) {
        this.cardZipcode = cardZipcode;
    }
}

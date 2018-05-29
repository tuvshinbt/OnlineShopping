package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Tamir Ganbat 05/06/2018
 */
public class CardDetailForm {
    @NotBlank
    private String cardHolderName;
    @NotBlank
    private String cardNumber;
    private String last4Digit;
    @Pattern(regexp = "^((0[1-9])|(1[0-2]))\\/(\\d{2})$")
    private String cardExpirationDate;
    @NotBlank
    private String cvv;
    @NotBlank
    private String zipcode;

    private String cardType;

    public CardDetailForm(){ }

    public CardDetailForm(User user){
        this.cardHolderName = user.getCards().get(0).getCardHolderName();
        this.cardNumber = user.getCards().get(0).getCardNumber();
        this.last4Digit = user.getCards().get(0).getLast4Digit();
        this.cardExpirationDate = user.getCards().get(0).getCardExpirationDate();
        this.cvv = user.getCards().get(0).getCvv();
        this.zipcode = user.getCards().get(0).getZipcode();
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}

package com.dgm.jms.card.model;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private int id;
    private String cardHolder;
    private String cardNumber;
    private int expireMonth;
    private int expireYear;
    private String cvc;

    public Card(int id, String cardHolder, String cardNumber, int expireMonth, int expireYear, String cvc) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.cvc = cvc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(int expireMonth) {
        this.expireMonth = expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(int expireYear) {
        this.expireYear = expireYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expireMonth=" + expireMonth +
                ", expireYear=" + expireYear +
                ", cvc='" + cvc + '\'' +
                '}';
    }
}

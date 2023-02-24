package model;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class Customers {

    private String codeCustomerString, nameString, emailString, phoneString, addressString, noteString;
    private Boolean sexBoolean = false;
    private Date birthDayDate;
    private int returnInt;

    @Override
    public String toString() {
        return getNameString();
    }

    public Customers() {
    }

    public Customers(String codeCustomerString, String nameString, String emailString, String phoneString, String addressString, String noteString, Boolean sexBoolean, Date birthDayDate, int returnInt) {
        this.codeCustomerString = codeCustomerString;
        this.nameString = nameString;
        this.emailString = emailString;
        this.phoneString = phoneString;
        this.addressString = addressString;
        this.noteString = noteString;
        this.sexBoolean = sexBoolean;
        this.birthDayDate = birthDayDate;
        this.returnInt = returnInt;
    }

    public String getCodeCustomerString() {
        return codeCustomerString;
    }

    public void setCodeCustomerString(String codeCustomerString) {
        this.codeCustomerString = codeCustomerString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getNoteString() {
        return noteString;
    }

    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }

    public Boolean getSexBoolean() {
        return sexBoolean;
    }

    public void setSexBoolean(Boolean sexBoolean) {
        this.sexBoolean = sexBoolean;
    }

    public Date getBirthDayDate() {
        return birthDayDate;
    }

    public void setBirthDayDate(Date birthDayDate) {
        this.birthDayDate = birthDayDate;
    }

    public int getReturnInt() {
        return returnInt;
    }

    public void setReturnInt(int returnInt) {
        this.returnInt = returnInt;
    }

}

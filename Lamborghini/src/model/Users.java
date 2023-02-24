package model;

import java.util.Date;

/**
 *
 * @author vovandai
 */
public class Users {

    private String emailString, passWordString, userNameString, addressString, avataString, noteString;
    private Boolean positionBoolean = false, sexBoolean = false;
    private Date birthDayDate;

    @Override
    public String toString() {
        return getUserNameString();
    }

    public Users() {
    }

    public Users(String emailString, String passWordString, String userNameString, String addressString, String avataString, String noteString, Boolean sexBoolean, Date birthDayDate) {
        this.emailString = emailString;
        this.passWordString = passWordString;
        this.userNameString = userNameString;
        this.addressString = addressString;
        this.avataString = avataString;
        this.noteString = noteString;
        this.sexBoolean = sexBoolean;
        this.birthDayDate = birthDayDate;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getPassWordString() {
        return passWordString;
    }

    public void setPassWordString(String passWordString) {
        this.passWordString = passWordString;
    }

    public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getAvataString() {
        return avataString;
    }

    public void setAvataString(String avataString) {
        this.avataString = avataString;
    }

    public String getNoteString() {
        return noteString;
    }

    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }

    public Boolean getPositionBoolean() {
        return positionBoolean;
    }

    public void setPositionBoolean(Boolean positionBoolean) {
        this.positionBoolean = positionBoolean;
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

}

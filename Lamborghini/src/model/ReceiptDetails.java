package model;

/**
 *
 * @author ACER
 */
public class ReceiptDetails {

    private int ID, amountInt;
    private String codeRecieptDetailString, codeProductString;

    public ReceiptDetails() {
    }

    public ReceiptDetails(int ID, int amountInt, String codeRecieptDetailString, String codeProductString) {
        this.ID = ID;
        this.amountInt = amountInt;
        this.codeRecieptDetailString = codeRecieptDetailString;
        this.codeProductString = codeProductString;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAmountInt() {
        return amountInt;
    }

    public void setAmountInt(int amountInt) {
        this.amountInt = amountInt;
    }

    public String getCodeRecieptDetailString() {
        return codeRecieptDetailString;
    }

    public void setCodeRecieptDetailString(String codeRecieptDetailString) {
        this.codeRecieptDetailString = codeRecieptDetailString;
    }

    public String getCodeProductString() {
        return codeProductString;
    }

    public void setCodeProductString(String codeProductString) {
        this.codeProductString = codeProductString;
    }

}

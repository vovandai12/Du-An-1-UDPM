package model;

import java.util.Date;

/**
 *
 * @author vovandai
 */
public class Receipts {

    private String codeReceiptString, emailUserString, codeCustormerString;
    private Date exportDate;
    private int discountInt, VATInt;

    public Receipts() {
    }

    public Receipts(String codeReceiptString, String emailUserString, String codeCustormerString, Date exportDate, int discountInt, int VATInt) {
        this.codeReceiptString = codeReceiptString;
        this.emailUserString = emailUserString;
        this.codeCustormerString = codeCustormerString;
        this.exportDate = exportDate;
        this.discountInt = discountInt;
        this.VATInt = VATInt;
    }

    public String getCodeReceiptString() {
        return codeReceiptString;
    }

    public void setCodeReceiptString(String codeReceiptString) {
        this.codeReceiptString = codeReceiptString;
    }

    public String getEmailUserString() {
        return emailUserString;
    }

    public void setEmailUserString(String emailUserString) {
        this.emailUserString = emailUserString;
    }

    public String getCodeCustormerString() {
        return codeCustormerString;
    }

    public void setCodeCustormerString(String codeCustormerString) {
        this.codeCustormerString = codeCustormerString;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public int getDiscountInt() {
        return discountInt;
    }

    public void setDiscountInt(int discountInt) {
        this.discountInt = discountInt;
    }

    public int getVATInt() {
        return VATInt;
    }

    public void setVATInt(int VATInt) {
        this.VATInt = VATInt;
    }
}

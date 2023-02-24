package model;

import java.util.Date;

/**
 *
 * @author vovandai
 */
public class Products {

    private String codeProductString, nameProductString, priceString, codeCateString, avataString, 
            picture1String, picture2String, picture3String, noteString, colorString;
    private Date importDate, ProductionDate;
    private int amountInt;

    @Override
    public String toString() {
        return getNameProductString();
    }

    public Products() {
    }

    public Products(String codeProductString, String nameProductString, String priceString, String codeCateString, String avataString, String picture1String, String picture2String, String picture3String, String noteString, String colorString, Date importDate, Date ProductionDate, int amountInt) {
        this.codeProductString = codeProductString;
        this.nameProductString = nameProductString;
        this.priceString = priceString;
        this.codeCateString = codeCateString;
        this.avataString = avataString;
        this.picture1String = picture1String;
        this.picture2String = picture2String;
        this.picture3String = picture3String;
        this.noteString = noteString;
        this.colorString = colorString;
        this.importDate = importDate;
        this.ProductionDate = ProductionDate;
        this.amountInt = amountInt;
    }

    public String getCodeProductString() {
        return codeProductString;
    }

    public void setCodeProductString(String codeProductString) {
        this.codeProductString = codeProductString;
    }

    public String getNameProductString() {
        return nameProductString;
    }

    public void setNameProductString(String nameProductString) {
        this.nameProductString = nameProductString;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getCodeCateString() {
        return codeCateString;
    }

    public void setCodeCateString(String codeCateString) {
        this.codeCateString = codeCateString;
    }

    public String getAvataString() {
        return avataString;
    }

    public void setAvataString(String avataString) {
        this.avataString = avataString;
    }

    public String getPicture1String() {
        return picture1String;
    }

    public void setPicture1String(String picture1String) {
        this.picture1String = picture1String;
    }

    public String getPicture2String() {
        return picture2String;
    }

    public void setPicture2String(String picture2String) {
        this.picture2String = picture2String;
    }

    public String getPicture3String() {
        return picture3String;
    }

    public void setPicture3String(String picture3String) {
        this.picture3String = picture3String;
    }

    public String getNoteString() {
        return noteString;
    }

    public void setNoteString(String noteString) {
        this.noteString = noteString;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(Date ProductionDate) {
        this.ProductionDate = ProductionDate;
    }

    public int getAmountInt() {
        return amountInt;
    }

    public void setAmountInt(int amountInt) {
        this.amountInt = amountInt;
    }
}

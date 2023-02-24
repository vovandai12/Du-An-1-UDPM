package model;

/**
 *
 * @author vovandai
 */
public class Categoris {

    private String nameListString, categoryTypeString, codeCateString;

    @Override
    public String toString() {
        return getNameListString();
    }

    public Categoris() {
    }

    public Categoris(String nameListString, String categoryTypeString, String codeCateString) {
        this.nameListString = nameListString;
        this.categoryTypeString = categoryTypeString;
        this.codeCateString = codeCateString;
    }

    public String getNameListString() {
        return nameListString;
    }

    public void setNameListString(String nameListString) {
        this.nameListString = nameListString;
    }

    public String getCategoryTypeString() {
        return categoryTypeString;
    }

    public void setCategoryTypeString(String categoryTypeString) {
        this.categoryTypeString = categoryTypeString;
    }

    public String getCodeCateString() {
        return codeCateString;
    }

    public void setCodeCateString(String codeCateString) {
        this.codeCateString = codeCateString;
    }
}

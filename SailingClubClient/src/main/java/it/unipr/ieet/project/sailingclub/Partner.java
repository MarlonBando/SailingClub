package it.unipr.ieet.project.sailingclub;

/**
 * Class that represent a partner
 */
public class Partner {
    private String name;
    private String surname;
    private String email;
    private String fiscalCode;

    /**
     * Main contructor of the class
     * @param name  partner's name
     * @param surname partner's surname
     * @param email partner's email
     * @param fiscalCode partner's Fiscal Code
     */
    public Partner(String name,String surname,String email,String fiscalCode){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.fiscalCode = fiscalCode;
    }


    /**
     * @return partner's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the partner's name
     * @param name partner's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return partner's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set the partner's surname
     * @param surname partner's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return partner's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set tha partner's email
     * @param email partner's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return partner's fiscal code
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * Set partner's fiscal code
     * @param fiscalCode partner's fiscal code
     */
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}

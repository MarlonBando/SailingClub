package it.unipr.ieet.project.sailingclub;

/**
 * Class that represent a payment
 */
public class Payment {
    private int idPayment;
    private String description;
    private String deadLine;
    private String paymentDate;
    private Float amount;
    private String state;

    /**
     * Contructor of the payment class
     * @param idPayment identification number of the payment
     * @param description payment description
     * @param deadLine payment deadline
     * @param paymentDate payment date
     * @param amount payment amount
     * @param state payment state
     */
    public Payment(int idPayment,String description, String deadLine, String paymentDate, Float amount, Boolean state) {
        this.idPayment = idPayment;
        this.description = description;
        this.deadLine = deadLine;
        this.amount = amount;
        this.paymentDate = paymentDate;
        if (state) {
            this.state = "Pagato";
        } else {
            this.state = "NON Pagato";
        }
    }

    /**
     * @return identification number of the payment
     */
    public int getIdPayment() {
        return idPayment;
    }

    /**
     * Set the payment description
     * @param description payment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the payment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return payment deadLine
     */
    public String getDeadLine() {
        return deadLine;
    }

    /**
     * Set the payment deadline
     * @param deadLine payment deadline
     */
    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    /**
     * @return payment date
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Set the payment date
     * @param paymentDate payment date
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the payment amount
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * Set the payment amount
     * @param amount payment amount
     */
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     * @return payment state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the payment state
     * @param state payment state
     */
    public void setState(String state) {
        this.state = state;
    }
}

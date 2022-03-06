package it.unipr.ieet.project.sailingclub;

/**
 * Class that rapresent a competition
 */
public class Competition {
    private int idCompetition;
    private String name;
    private Float price;
    private String date;
    private String state;

    /**
     * Competition class contructor
     * @param idCompetition competition identification number
     * @param competitionName competition name
     * @param price competition registration price
     * @param date competition date
     * @param state competition state (registered,not registered)
     */
    public Competition(int idCompetition, String competitionName, Float price, String date, String state) {
        this.idCompetition = idCompetition;
        this.name = competitionName;
        this.price = price;
        this.date = date;
        this.state = state;
    }

    /**
     * @return competition identification number
     */
    public int getIdCompetition() {
        return idCompetition;
    }

    /**
     * Set the competition identifier number
     * @param idCompetition competition identifier
     */
    public void setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
    }

    /**
     * @return competition name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the competition name
     * @param name competition name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the competition price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Set the competition registration price
     * @param price comeptition registration price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the competition state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state competition
     * @param state competition state
     */
    public void setState(String state) {
        this.state = state;
    }
}

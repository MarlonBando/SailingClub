package it.unipr.ieet.project.sailingclub;

/**
 * Class that represents a Boat
 */
public class Boat {
    private Integer id;
    private String boatName;
    private int length;

    /**
     * Construtctor of the class
     * @param id boat identification number
     * @param name boat name
     * @param length boat length
     */
    Boat(int id, String name, int length){
        this.id = id;
        this.boatName = name;
        this.length = length;
    }

    /**
     * @return the identification number of the boat
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the identification number of the boat
     * @param id boat identification number
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return boat name
     */
    public String getBoatName() {
        return boatName;
    }

    /**
     * Set the boat name
     * @param boatName boat name
     */
    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    /**
     * @return boat length
     */
    public int getLength() {
        return length;
    }

    /**
     * Set the boat length
     * @param length boat length
     */
    public void setLength(int length) {
        this.length = length;
    }
}

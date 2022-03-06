package it.unipr.ieet.project.sailingclub;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {


    @Test
    void getId() {
        Boat boat = new Boat(2,"testBoat",11111);
        assertEquals(2,boat.getId());
    }

    @Test
    void setId() {
        Boat boat = new Boat(2,"testBoat",11111);
        int newId = 4;
        boat.setId(newId);
        assertEquals(newId,boat.getId());
    }

    @Test
    void getBoatName() {
        Boat boat = new Boat(2,"testBoat",11111);
        assertEquals("testBoat",boat.getBoatName());
    }

    @Test
    void setBoatName() {
        Boat boat = new Boat(2,"testBoat",11111);
        String newName = "newNameTestBoat";
        boat.setBoatName(newName);
        assertEquals(newName,boat.getBoatName());
    }

    @Test
    void getLength() {
        Boat boat = new Boat(2,"testBoat",11111);
        assertEquals(11111,boat.getLength());
    }

    @Test
    void setLength() {
        Boat boat = new Boat(2,"testBoat",11111);
        int newLength=00001;
        boat.setLength(newLength);
        assertEquals(newLength,boat.getLength());
    }
}
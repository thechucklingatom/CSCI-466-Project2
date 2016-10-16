package Project2.CSCI446;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Thew on 10/16/2016.
 */
public class Room {
    private RoomType type; // the current known Type for this Room
    private int xPosition; // set x-position of this Room in world
    private int yPosition; // set y-position of this Room in world
    private final List<RoomType> possibleTypes; // possible Types that might apply to this Room
    private final List<Percept> percepts; // all current percepts for this given room

    public Room(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.type = RoomType.EMPTY;
        possibleTypes = new ArrayList<RoomType>();
        percepts = new ArrayList<Percept>();

    }

    public Room() {
        this.xPosition = -1;
        this.yPosition = -1;
        this.type = RoomType.EMPTY;
        possibleTypes = new ArrayList<RoomType>();
        percepts = new ArrayList<Percept>();
    }

    public List<Percept> getPercepts() {
        return this.percepts;
    }

    public void addPercept(Percept sense) {
        this.percepts.add(sense);
    }

    public void delPercept(Percept sense) {
        this.percepts.remove(sense);
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public RoomType getType() {
        return this.type;
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public void addPossibleType(RoomType newType) {
        possibleTypes.add(newType);
    }

    public void removePossibleType(RoomType delType) {
        possibleTypes.remove(delType);
    }

    public List<RoomType> getPossibleTypes() {
        return this.possibleTypes;
    }

    public String toString() {
        String toString;
        toString = "Room[" + this.xPosition + "][" + this.yPosition + "] of type: " + this.type + " or possibly: ";
        for (RoomType pos : this.possibleTypes) {
            toString += pos.toString() + ", ";
        }
        return toString;
    }

    public void print() {
        System.out.print(toString() + "\n");
    }


}

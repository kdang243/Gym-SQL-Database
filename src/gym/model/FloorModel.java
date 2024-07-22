package src.gym.model;

/**
 * The intent for this class is to update/store information about a single Floor.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class FloorModel {
    private final int FNumber;
    private final int capacity;

    public FloorModel(int FNumber, int capacity) {
        this.FNumber = FNumber;
        this.capacity = capacity;
    }

    public int getFNumber() {
        return FNumber;
    }
    public int getCapacity() {
        return capacity;
    }
}
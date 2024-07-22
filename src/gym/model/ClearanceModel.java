package src.gym.model;

/**
 * The intent for this class is to update/store information about a single Clearance Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class ClearanceModel {
    private final int level;

    public ClearanceModel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}

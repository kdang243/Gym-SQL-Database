package src.gym.model;

/**
 * The intent for this class is to update/store information about a single Attends Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class RequiresModel {
    private final int level;
    private final int FNumber;

    public RequiresModel(int level, int FNumber) {
        this.level = level;
        this.FNumber = FNumber;
    }

    public int getLevel() {
        return level;
    }
    public int getFNumber() {
        return FNumber;
    }
}

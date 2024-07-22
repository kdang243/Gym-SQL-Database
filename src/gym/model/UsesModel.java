package src.gym.model;

/**
 * The intent for this class is to update/store information about a single Attends Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class UsesModel {
    private final int MID;
    private final int EID;

    public UsesModel(int MID, int EID) {
        this.MID = MID;
        this.EID = EID;
    }

    public int getMID() {
        return MID;
    }
    public int getEID() {
        return EID;
    }
}

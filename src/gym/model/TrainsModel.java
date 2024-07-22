package src.gym.model;

/**
 * The intent for this class is to update/store information about a single Trains Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class TrainsModel {
    private final int MID;
    private final int SID;

    public TrainsModel(int MID, int SID) {
        this.MID = MID;
        this.SID = SID;
    }

    public int getMID() {
        return MID;
    }
    public int getSID() {
        return SID;
    }
}

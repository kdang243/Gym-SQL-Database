package src.gym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single WorksOn Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class WorksOnModel {
    private final int SID;
    private final int FNumber;

    public WorksOnModel(int SID, int FNumber) {
        this.SID = SID;
        this.FNumber = FNumber;
    }

    public int getSID() {
        return SID;
    }

    public int getFNumber() {
        return FNumber;
    }
}

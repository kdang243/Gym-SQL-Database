package src.gym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single Utilizes Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class UtilizesModel {
    private final int EID;
    private final Date sessionDate;
    private final Time sessionStartTime;

    public UtilizesModel(int EID, Date sessionDate, Time sessionStartTime) {
        this.EID = EID;
        this.sessionDate = sessionDate;
        this.sessionStartTime = sessionStartTime;
    }

    public int getEID() {
        return EID;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public Time getSessionStartTime() {
        return sessionStartTime;
    }
}

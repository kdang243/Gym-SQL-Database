package src.gym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single Attends Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class LeadsModel {
    private final int SID;
    private final Date sessionDate;
    private final Time startTime;

    public LeadsModel(int SID, Date sessionDate, Time startTime) {
        this.SID = SID;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
    }

    public int getSID() {
        return SID;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public Time getStartTime() {
        return startTime;
    }
}

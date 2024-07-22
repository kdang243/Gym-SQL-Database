package src.gym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single Attends Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class AttendsModel {
    private final int MID;
    private final Date sessionDate;
    private final Time startTime;
    private final int locker;

    public AttendsModel(int MID, Date sessionDate, Time startTime, int locker) {
        this.MID = MID;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.locker = locker;
    }

    public int getMID() {
        return MID;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public int getLocker() {
        return locker;
    }
}

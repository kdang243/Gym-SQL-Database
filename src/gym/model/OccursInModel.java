package src.gym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single OccursIn Relation.
 * <p>
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class OccursInModel {
    private final int ANumber;
    private final Date sessionDate;
    private final Time startTime;
    private final String AType;

    public OccursInModel(int ANumber, Date sessionDate, Time startTime, String AType) {
        this.ANumber = ANumber;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.AType = AType;
    }

    public int getANumber() {
        return ANumber;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public String getAType() {
        return AType;
    }
}

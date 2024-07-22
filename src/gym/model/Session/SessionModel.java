package src.gym.model.Session;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single Gym Session.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class SessionModel {
    private final int duration;
    private final Time startTime;
    private final Time endTime;
    private final Date date;

    public SessionModel(int duration, Time startTime, Time endTime, Date date) {
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Date getDate() {
        return date;
    }
}

package src.gym.model.Session;

import java.sql.Date;
import java.sql.Time;

public class Session2Model {
    private final Time startTime;
    private final Time endTime;
    private final Date date;

    public Session2Model(Time startTime, Time endTime, Date date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
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

package src.gym.model.Session;

import java.sql.Date;
import java.sql.Time;

public class Session1Model {
    private final int duration;
    private final Time startTime;
    private final Time endTime;
    public Session1Model(int duration, Time startTime, Time endTime) {
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
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

}

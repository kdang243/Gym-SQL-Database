package src.gym.model.FitnessClass;

import src.gym.model.Session.SessionModel;

import java.sql.Date;
import java.sql.Time;

/**
 * The intent for this class is to update/store information about a single FitnessClass.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class FitnessClassModel extends SessionModel {
    private final String fCType;

    public FitnessClassModel(Date date, Time startTime, Time endTime, String fCType, int duration) {
        super(duration,startTime,endTime,date);
        this.fCType = fCType;
    }

    public String getfCType() {
        return fCType;
    }

}

package src.gym.model.FitnessClass;

import src.gym.model.Session.Session1Model;

import java.sql.Date;
import java.sql.Time;

public class FitnessClass1Model extends Session1Model {
    public FitnessClass1Model(Time startTime, Time endTime, int duration) {
        super(duration, startTime,endTime);
    }
}

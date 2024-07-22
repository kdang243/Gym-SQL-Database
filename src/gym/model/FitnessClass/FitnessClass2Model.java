package src.gym.model.FitnessClass;

import src.gym.model.Session.Session2Model;

import java.sql.Date;
import java.sql.Time;

public class FitnessClass2Model extends Session2Model {
    private final String FCType;

    public FitnessClass2Model(Date date, Time startTime, Time endTime, String FCType) {
        super(startTime,endTime,date);
        this.FCType = FCType;
    }

    public String getFCType() {
        return FCType;
    }
}

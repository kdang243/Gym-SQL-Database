package src.gym.model;

import java.sql.Time;

public class AreaModel {
    private final int ANumber;
    private final String AType;
    private final int FNumber;
    public AreaModel(int ANumber, String AType, int FNumber) {
        this.ANumber = ANumber;
        this.AType = AType;
        this.FNumber = FNumber;
    }

    public int getANumber() {
        return ANumber;
    }

    public String getAType() {
        return AType;
    }

    public int getFNumber() {
        return FNumber;
    }
}

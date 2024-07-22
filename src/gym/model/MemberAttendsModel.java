package src.gym.model;

import java.sql.Date;
import java.sql.Time;

public class MemberAttendsModel {
    private final int MID;
    private final int count;

    public MemberAttendsModel(int MID, int count) {
        this.MID = MID;
        this.count = count;
    }

    public int getMID() {
        return MID;
    }
    public int getCount() {
        return count;
    }
}
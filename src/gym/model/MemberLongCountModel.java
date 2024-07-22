package src.gym.model;

public class MemberLongCountModel {
    private final int MID;
    private final int count;

    public MemberLongCountModel(int MID, int count) {
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
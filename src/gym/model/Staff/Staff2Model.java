package src.gym.model.Staff;

public class Staff2Model {
    private final int SID;
    private final String SType;
    private final String name;

    public Staff2Model(int SID, String SType, String name) {
        this.SID = SID;
        this.SType = SType;
        this.name = name;
    }

    public int getSID() {
        return SID;
    }
    public String getSType() {
        return SType;
    }
    public String getName() {
        return name;
    }
}

package src.gym.model.Equipment;

public class Equipment2Model {
    private final int EID;
    private final String name;
    private final String condition;
    private final String AType;
    private final int ANumber;

    public Equipment2Model(int EID, String name, String condition, String AType, int ANumber) {
        this.EID = EID;
        this.name = name;
        this.condition = condition;
        this.AType = AType;
        this.ANumber = ANumber;
    }

    public int getEID() {
        return EID;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public String getAType() {
        return AType;
    }

    public int getANumber() {
        return ANumber;
    }
}


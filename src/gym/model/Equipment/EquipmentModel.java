package src.gym.model.Equipment;

/**
 * The intent for this class is to update/store information about a single Equipment.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class EquipmentModel {
    private final int EID;
    private final String name;
    private final String usages;
    private final String condition;
    private final String AType;
    private final int ANumber;

    public EquipmentModel(int EID, String name, String usages, String condition, String AType, int ANumber) {
        this.EID = EID;
        this.name = name;
        this.usages = usages;
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

    public String getUsages() {
        return usages;
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

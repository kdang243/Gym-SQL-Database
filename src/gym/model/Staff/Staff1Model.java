package src.gym.model.Staff;

public class Staff1Model {
    private final String SType;
    private final String workingHours;

    public Staff1Model(String SType, String workingHours) {
        this.SType = SType;
        this.workingHours = workingHours;
    }
    public String getSType() {
        return SType;
    }
    public String getWorkingHours() {
        return workingHours;
    }
}

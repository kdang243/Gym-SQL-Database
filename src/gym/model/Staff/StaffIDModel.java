package src.gym.model.Staff;

public class StaffIDModel {
    private final int SID;

    private final String name;

    public StaffIDModel(int SID, String name) {
        this.SID = SID;
        this.name = name;
    }

    public int getSID() {
        return SID;
    }

    public String getName() {
        return name;
    }
}

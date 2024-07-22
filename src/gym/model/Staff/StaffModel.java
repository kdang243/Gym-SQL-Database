package src.gym.model.Staff;

/**
 * The intent for this class is to update/store information about a single member of Gym Staff.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class StaffModel {
    private final int SID;
    private final String sType;
    private final String name;
    private final String workingHours;

    public StaffModel(int SID, String sType, String name, String workingHours) {
        this.SID = SID;
        this.sType = sType;
        this.name = name;
        this.workingHours = workingHours;
    }

    public int getSID() {
        return SID;
    }

    public String getsType() {
        return sType;
    }

    public String getName() {
        return name;
    }

    public String getWorkingHours() {
        return workingHours;
    }
}

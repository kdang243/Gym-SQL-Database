package src.gym.model.Member;

/**
 * The intent for this class is to update/store information about a single Member
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class MemberModel {
    private final int MID;
    private final String name;
    private final String phone;
    private final String email;
    private final int clearanceLevel;

    public MemberModel(int MID, String name, String phone, String email, int clearanceLevel) {
        this.MID = MID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.clearanceLevel = clearanceLevel;
    }

    public int getMID() {
        return MID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getClearanceLevel() {
        return clearanceLevel;
    }
}

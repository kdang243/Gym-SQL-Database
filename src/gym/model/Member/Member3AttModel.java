package src.gym.model.Member;

/**
 * The intent for this class is to update/store information about a single Member
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class Member3AttModel {
    private final String name;
    private final String phone;
    private final String email;

    public Member3AttModel(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
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
}

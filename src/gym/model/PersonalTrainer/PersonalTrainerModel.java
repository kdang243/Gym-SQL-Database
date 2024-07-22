package src.gym.model.PersonalTrainer;

import src.gym.model.Staff.StaffModel;

/**
 * The intent for this class is to update/store information about a single Equipment.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class PersonalTrainerModel extends StaffModel {
    private final String program;

    public PersonalTrainerModel(int SID, String name, String sType, String program, String workingHours) {
        super(SID,sType,name,workingHours);
        this.program = program;
    }

    public String getProgram() {
        return program;
    }
}

package src.gym.model.PersonalTrainer;

import src.gym.model.Staff.Staff2Model;

public class PersonalTrainer2Model extends Staff2Model {
    private final String program;

    public PersonalTrainer2Model(int SID, String name, String SType, String program) {
        super(SID,SType,name);
        this.program = program;
    }

    public String getProgram() {
        return program;
    }
}

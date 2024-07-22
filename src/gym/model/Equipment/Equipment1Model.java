package src.gym.model.Equipment;

public class Equipment1Model {
    private final String name;
    private final String usages;

    public Equipment1Model(String name, String usages) {
        this.name = name;
        this.usages = usages;
    }

    public String getName() {
        return name;
    }

    public String getUsages() {
        return usages;
    }
}

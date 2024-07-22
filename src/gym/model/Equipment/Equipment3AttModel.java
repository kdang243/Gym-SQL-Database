package src.gym.model.Equipment;

public class Equipment3AttModel {
    private final String condition;
    private final int count;

    public Equipment3AttModel(String condition, int count) {
        this.condition = condition;
        this.count = count;
    }

    public String getCondition() {
        return condition;
    }
    public int getCount() {
        return count;
    }
}

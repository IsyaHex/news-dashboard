package uz.task.constants;

public enum UserStateEnum {
    DISABLED(0),
    ACTIVE(1);

    private final Integer value;

    UserStateEnum(Integer state) {
        this.value = state;
    }

    public Integer getValue() {
        return value;
    }
}

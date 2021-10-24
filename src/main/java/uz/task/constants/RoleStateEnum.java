package uz.task.constants;

public enum RoleStateEnum {
    USER(0),
    ADMIN(1);

    private final Integer value;

    RoleStateEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

package uz.task.constants;

public enum NewsStateEnum {
    UNVERIFIED(0),
    VERIFIED(1);

    private final Integer value;

    NewsStateEnum(Integer state) {
        this.value = state;
    }

    public Integer getValue() {
        return value;
    }
}

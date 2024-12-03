package enums;

public enum Rating {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public int getValue() {
        return this.ordinal() + 1;
    }
}

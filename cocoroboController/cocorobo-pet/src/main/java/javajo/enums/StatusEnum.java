package javajo.enums;

/**
 * result判定値
 * Created by maaya on 2016/09/24.
 */
public enum StatusEnum {
    OK("OK"),
    NG("NG");

    private final String status;

    private StatusEnum(final String status) {
        this.status = status;
    }

    public String getName() {
        return status;
    }
}

package kx;

import java.sql.Time;

import lombok.Data;

@Data
public class KDBData {

    public static final String TABLE_NAME = "test";

    public static final String[] COL_NAMES = new String[] { "time", "sym",
        "price", "size", "stop", "cond", "ex" };

    private Time time;
    private String sym;
    private double price;
    private int size;
    private boolean stop;
    private char cond;
    private char ex;
}

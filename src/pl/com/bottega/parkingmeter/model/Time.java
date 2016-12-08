package pl.com.bottega.parkingmeter.model;

public class Time {

    public enum TimeUnit{
        MIN, HOUR

    }
    private static final TimeUnit DEFAULT_UNIT = TimeUnit.MIN;

    public static final Time ZERO = new Time(0, DEFAULT_UNIT);

    private final long minutes;

    public Time(long value, TimeUnit unit) {
        switch (unit){
            case MIN:
                minutes = value;
                break;
            case HOUR:
                minutes = value * 60;
                break;
            default:
                minutes = 0;
        }
    }

    public long getInMinutes() {
        return minutes;
    }

    public Time add(Time time) {
        return new Time(minutes + time.minutes, DEFAULT_UNIT);
    }

    @Override
    public String toString() {
        return minutes + " " + DEFAULT_UNIT.name();
    }
}
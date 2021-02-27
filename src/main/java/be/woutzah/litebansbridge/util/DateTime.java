package be.woutzah.litebansbridge.util;

public class DateTime {

    public final long days;
    public final long hours;
    public final long minutes;
    public final long seconds;
    public final long milliseconds;

    public DateTime(long days, long hours, long minutes, long seconds, long milliseconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }
}

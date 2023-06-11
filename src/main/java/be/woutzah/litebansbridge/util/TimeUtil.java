package be.woutzah.litebansbridge.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    private TimeUtil() {
    }

    public static DateTime diffTimeToString(long start, long end) {
        long milliseconds = end - start;

        return new DateTime(TimeUnit.MILLISECONDS.toDays(milliseconds),
                TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds)),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)),
                TimeUnit.MILLISECONDS.toMillis(milliseconds) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds)));
    }

}

package LMS;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Helper {
    public static boolean contains(String container, String contained){
        container = container.toLowerCase();
        contained = contained.toLowerCase();
        return container.contains(contained);
    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);


        return cal.getTime();
    }
}

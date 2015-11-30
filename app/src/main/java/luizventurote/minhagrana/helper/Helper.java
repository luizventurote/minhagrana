package luizventurote.minhagrana.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    /**
     * Converte uma data para String
     *
     * @param date
     * @return String
     */
    public static String formatDateToString(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String reportDate = df.format(date);

        return reportDate;
    }

    /**
     * Converte uma String para Date
     *
     * @param date
     * @return Date
     */
    public static Date formatStringToDate(String date) {

        try {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date obj_date = format.parse(date);

            return obj_date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package luizventurote.minhagrana.helper;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /**
     * Converte uma data string com barras em um objeto Date
     *
     * @param date
     * @return
     */
    public static Date formatStringToDateWithSlash(String date) {

        try {

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date obj_date = format.parse(date);

            int day = getDay(obj_date);
            int month = getMonth(obj_date);
            int year = getYear(obj_date);

            Date new_date = formatStringToDate(year + "-" + month + "-" + day + " 00:00:00");

            return new_date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converte um objeto data em um objeto string com barras
     *
     * @param date
     * @return
     */
    public static String formatDateToStringWithSlash(Date date) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String reportDate = df.format(date);

        return reportDate;
    }

    /**
     * Recupera o dia de uma data
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {

        DateFormat df = new SimpleDateFormat("dd");

        return Integer.parseInt( df.format(date) );
    }

    /**
     * Recupera o mês de uma data
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {

        DateFormat df = new SimpleDateFormat("MM");

        return Integer.parseInt( df.format(date) );
    }

    /**
     * Recupera o ano de uma data
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy");

        return Integer.parseInt( df.format(date) );
    }

    /**
     * Formatar moeda
     *
     * @param value
     * @return String
     */
    public static String formatCurrency(Double value) {

        Locale locale = new Locale("pt","br");

        NumberFormat n = NumberFormat.getInstance(locale);
        n.setMinimumFractionDigits(2);
        n.setMaximumFractionDigits(2);

        //if(value < 0) {
            value = value * -1;
            return "-R$ "+n.format(value);
        //}

        //return "R$ "+n.format(value);
    }

    public static Double formatCurrencyInverted(String value){

        return Double.parseDouble(value.substring(5).trim().replace(',','.'));

    }
}

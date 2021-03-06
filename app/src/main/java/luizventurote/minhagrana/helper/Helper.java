package luizventurote.minhagrana.helper;

import android.os.Environment;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import luizventurote.minhagrana.R;

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

        if(value < 0) {
            value = value * -1;
            return "-R$ "+n.format(value);
        }

        return "R$ "+n.format(value);
    }

    /**
     * Formata uma string moeda para double
     *
     * @param text_value
     * @return
     */
    public static Double formatCurrencyInverted(String text_value) {

        boolean negative_value = false;

        String objective = text_value.replace(".", "");

        if(objective.contains("-")) {
            negative_value = true;
        }

        objective = objective.replace("-","");

        objective = objective.replace("R$","");

        objective = objective.replace(",",".");

        objective = objective.replace(" ","");

        Double result_obj = Double.parseDouble(objective);

        if(negative_value) {
            result_obj = result_obj * (-1);
        }

        return result_obj;
    }

    /**
     * Cria um arquivo
     *
     * @param sFileName
     * @param sBody
     */
    public void createFile(String sFileName, String sBody) {

        try {

            File root = new File(Environment.getExternalStorageDirectory(), "Notes");

            if (!root.exists()) {
                root.mkdirs();
            }

            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch(IOException e) {

            e.printStackTrace();
        }
    }

}

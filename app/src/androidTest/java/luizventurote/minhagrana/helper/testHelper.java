package luizventurote.minhagrana.helper;

import android.test.AndroidTestCase;
import java.util.Date;

public class testHelper extends AndroidTestCase {

    /**
     * Verifica se a String de data com barras está sendo carregada corretamente
     * no objeto Date
     */
    public void test_formatStringToDateWithSlash() {

        String data = "21/02/1994";

        Date obj_data = Helper.formatStringToDateWithSlash(data);

        assertEquals("1994-02-21 00:00:00", Helper.formatDateToString(obj_data));
    }

    /**
     * Verifica se a data com barras está sendo formatada corretamente em String
     */
    public void test_formatDateToStringWithSlash() {

        String data = "21/02/1994";

        Date obj_data = Helper.formatStringToDateWithSlash(data);

        assertEquals(data, Helper.formatDateToStringWithSlash(obj_data));
    }
}

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

    /**
     * Testa a formatação de moedas
     */
    public void test_formatCurrency() {

        // Negative
        Double negative_value = -5000000.99;
        String negative_result = Helper.formatCurrency(negative_value);

        // Positive
        Double positive_value = 1000000.99;
        String positive_result = Helper.formatCurrency(positive_value);

        assertEquals("-R$ 5.000.000,99", negative_result);
        assertEquals("R$ 1.000.000,99", positive_result);
    }

    /**
     * Testa formatação de moeda invertido
     */
    public void test_formatCurrencyInverted() {

        String value = "-R$ 5.000.000,99";

        Double result = Helper.formatCurrencyInverted(value);

        assertEquals(-5000000.99, result);
    }
}

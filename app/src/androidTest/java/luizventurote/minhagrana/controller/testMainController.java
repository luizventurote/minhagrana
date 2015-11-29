package luizventurote.minhagrana.controller;

import android.test.AndroidTestCase;
import java.util.Date;

public class testMainController extends AndroidTestCase {

    /**
     * Testa se um registro está sendo inserido no banco de dados
     */
    public void test_inserirMovimentacaoFinanceira() {

        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 99.90, new Date());

        assertTrue(id>0);
    }
}

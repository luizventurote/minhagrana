package luizventurote.minhagrana.controller;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Date;
import java.util.List;

import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class testMainController extends AndroidTestCase {

    /**
     * Testa se um registro está sendo inserido no banco de dados
     */
    public void test_inserirMovimentacaoFinanceira() {

        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 99.90, new Date());

        assertTrue(id>0);
    }

    /**
     * Busca todos os objetos do banco de dados
     */
    public void test_buscarMovimentacaoFinanceira() {

        // Faz a busca de todos os objetos
        List<MovimentacaoFinanceira> list = MainController.buscarMovimentacaoFinanceira(this.getContext());

        MovimentacaoFinanceira mov = null;

        // Loop to load objects
        int j = 0; while (list.size() > j) {

            mov = list.get(j);

            Log.d("LOG_TEST_LIST", mov.getDescricao());

            j++;
        }
    }
}

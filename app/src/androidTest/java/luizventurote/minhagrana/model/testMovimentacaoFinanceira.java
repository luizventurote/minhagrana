package luizventurote.minhagrana.model;

import android.util.Log;

import junit.framework.TestCase;

public class testMovimentacaoFinanceira extends TestCase {

    /**
     * Teste do model de movimentação financeira
     */
    public void testNovaMovimentacaoFinanceira() {

        MovimentacaoFinanceira mov = new MovimentacaoFinanceira("Teste de Movimentação", 49.90);

        assertEquals("Teste de Movimentação", mov.getDescricao());

        assertEquals(49.90, mov.getValor());

        Log.d("LOG_TEST", mov.getData().toString());

    }
}

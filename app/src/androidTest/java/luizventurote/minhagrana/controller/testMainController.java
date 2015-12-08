package luizventurote.minhagrana.controller;

import android.test.AndroidTestCase;
import android.util.Log;
import java.util.Date;
import java.util.List;
import luizventurote.minhagrana.helper.Helper;
import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class testMainController extends AndroidTestCase {

    /**
     * Testa se um registro está sendo inserido no banco de dados
     */
    public void test_inserirMovimentacaoFinanceira() {

        Date data = Helper.formatStringToDate("2015-02-21 22:00:00");

        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 99.90, data);

        assertTrue(id>0);
    }

    /**
     * Testa a busca todos os objetos do banco de dados
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

    /**
     * Testa a busca de registro através do Id
     */
    public void test_buscarMovimentacaoFinanceiraPorId() {

        // Insere um novo registro e recupera o id
        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 59.90, new Date());

        // Faz a busca do registro no banco de dados
        MovimentacaoFinanceira mov = MainController.buscarMovimentacaoFinanceira(this.getContext(), id);

        Log.d("LOG_TEST_LIST", "test_buscarMovimentacaoFinanceiraPorId: " + mov.getId() + " - " + mov.getDescricao() +
                " - " + mov.getValor() + " - " + mov.getData());
    }

    /**
     * Testa a busca de registro através do Id
     */
    public void test_atualizarMovimentacaoFinanceira() {

        // Insere um novo registro e recupera o id
        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 99.90, new Date());

        // Faz a busca do registro no banco de dados
        MovimentacaoFinanceira mov = MainController.buscarMovimentacaoFinanceira(this.getContext(), id);

        // Realiza a alteração
        mov.setDescricao("New description!");
        mov.setValor(59.90);
        mov.setData( Helper.formatStringToDateWithSlash("21/02/2015") );

        // Salva as alterações no banco de dados
        MainController.atualizarMovimentacaoFinanceira(this.getContext(), mov);

        // Faz a busca do registro no banco de dados
        MovimentacaoFinanceira mov_result = MainController.buscarMovimentacaoFinanceira(this.getContext(), id);

        assertEquals(59.90, mov_result.getValor());
        assertEquals(mov_result.getDescricao(), mov_result.getDescricao());
        assertEquals(Helper.formatDateToString(mov.getData()), Helper.formatDateToString(mov_result.getData()));
    }

    /**
     * Faz o teste de deleção
     */
    public void test_deletarMovimentacaoFinanceira() {

        // Insere um novo registro e recupera o id
        Long id = MainController.inserirMovimentacaoFinanceira(this.getContext(), "Test description", 99.90, new Date());

        // Faz a busca do registro no banco de dados
        MovimentacaoFinanceira mov = MainController.buscarMovimentacaoFinanceira(this.getContext(), id);

        MainController.deletarMovimentacaoFinanceira(this.getContext(), mov);

        // Faz a busca do registro no banco de dados
        mov = MainController.buscarMovimentacaoFinanceira(this.getContext(), id);

        assertNull(mov);
    }

    /**
     * Teste de busca entre datas
     */
    public void test_buscarEntreDatas() {

        // Data mínima
        String minDate = "1890-01-01";

        // Data máxima
        String maxDate = "1890-12-31";

        // Faz a busca dos registros no banco de dados
        List<MovimentacaoFinanceira> list = MainController.buscarEntreDatas(this.getContext(), minDate, maxDate);

        if( list.size() == 0 ) {

            // Insere registros de teste
            Date d = Helper.formatStringToDate("1890-05-02 22:00:00");
            MainController.inserirMovimentacaoFinanceira(this.getContext(), "Registro de Teste", 90.9, d);

            d = Helper.formatStringToDate("1880-05-02 22:00:00");
            MainController.inserirMovimentacaoFinanceira(this.getContext(), "Registro de Teste", 90.9, d);

            // Faz a busca dos registros no banco de dados
            List<MovimentacaoFinanceira> sub_list = MainController.buscarEntreDatas(this.getContext(), minDate, maxDate);
            assertTrue( sub_list.size() == 1 );

        } else {

            assertTrue( list.size() == 1 );
        }
    }

    /**
     * Teste de busca de registros de um determinado mês
     */
    public void test_buscarPorMes() {

        // Ano
        int ano = 1780;

        // Mês
        int mes = 05;

        // Faz a busca dos registros no banco de dados
        List<MovimentacaoFinanceira> list = MainController.buscarPorMes(this.getContext(), ano, mes);

        if( list.size() == 0 ) {

            // Insere registros de teste
            Date d = Helper.formatStringToDate("1780-05-02 22:00:00");
            MainController.inserirMovimentacaoFinanceira(this.getContext(), "Registro de Teste", 90.9, d);

            // Faz a busca dos registros no banco de dados
            List<MovimentacaoFinanceira> sub_list = MainController.buscarPorMes(this.getContext(), ano, mes);

            assertTrue( sub_list.size() == 1 );

        } else {

            assertTrue( list.size() == 1 );
        }
    }

    /**
     * Testa o retorno do saldo do mês
     */
    public void test_buscarSaldoMensal() {

        // Ano
        int ano = 2015;

        // Mês
        int mes = 2;

        Double result = MainController.buscarSaldoMensal(this.getContext(), ano, mes);

        Log.d("TEST_SALDO_MENSAL", Double.toString(result));

    }
}

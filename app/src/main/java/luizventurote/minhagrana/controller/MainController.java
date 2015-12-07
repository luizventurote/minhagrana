package luizventurote.minhagrana.controller;

import android.content.Context;

import java.util.Date;
import java.util.List;

import luizventurote.minhagrana.helper.Database;
import luizventurote.minhagrana.model.MovimentacaoFinanceira;
import luizventurote.minhagrana.model.sql.MovimentacaoFinanceiraSql;

public class MainController {

    /**
     * Inserir novo registro de movimentação financeira
     *
     * @param context
     * @param descricao
     * @param valor
     * @return Long
     */
    public static Long inserirMovimentacaoFinanceira(Context context, String descricao, Double valor, Date data) {

        Database db = Database.getInstance(context);

        MovimentacaoFinanceira mov = new MovimentacaoFinanceira(descricao, valor, data);
        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        Long id = mov_sql.inserir(mov);

        db.close();

        return id;
    }

    /**
     * Faz a busca de todas as movimentações financeiras
     *
     * @param context
     * @return List<MovimentacaoFinanceira>
     */
    public static List<MovimentacaoFinanceira> buscarMovimentacaoFinanceira(Context context) {

        Database db = Database.getInstance(context);

        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        List<MovimentacaoFinanceira> list =  mov_sql.buscarTodos();

        db.close();

        return list;
    }

    /**
     * Buscar registros por Id
     *
     * @param context
     * @param id
     * @return MovimentacaoFinanceira
     */
    public static MovimentacaoFinanceira buscarMovimentacaoFinanceira(Context context, long id) {

        Database db = Database.getInstance(context);

        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        MovimentacaoFinanceira mov = mov_sql.buscar(id);

        db.close();

        return mov;
    }

    /**
     * Atualiza registro
     *
     * @param context
     * @param mov
     */
    public static void atualizarMovimentacaoFinanceira(Context context, MovimentacaoFinanceira mov) {

        Database db = Database.getInstance(context);
        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        mov_sql.atualizar(mov);

        db.close();
    }

    /**
     * Deleta registro
     *
     * @param context
     * @param mov
     */
    public static void deletarMovimentacaoFinanceira(Context context, MovimentacaoFinanceira mov) {

        Database db = Database.getInstance(context);
        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        mov_sql.deletar(mov);

        db.close();
    }

    /**
     * Realiza uma busca entre duas datas
     *
     * @param context
     * @param minDate minDate Data minima yyyy-MM-dd
     * @param maxDate maxDate Data máxima yyyy-MM-dd
     * @return List<MovimentacaoFinanceira>
     */
    public static List<MovimentacaoFinanceira> buscarEntreDatas(Context context, String minDate, String maxDate) {

        Database db = Database.getInstance(context);
        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        List<MovimentacaoFinanceira> list = mov_sql.buscarEntreDatas(minDate, maxDate);

        db.close();

        return list;
    }

    /**
     * Buscar todos os registros de um determinado mês
     *
     * @param context
     * @param ano Ano yyyy
     * @param mes Mês MM
     * @return List<MovimentacaoFinanceira>
     */
    public static List<MovimentacaoFinanceira> buscarPorMes(Context context, int ano, int mes) {

        Database db = Database.getInstance(context);
        MovimentacaoFinanceiraSql mov_sql = new MovimentacaoFinanceiraSql( db.getWritableDatabase() );

        String mes_selecionado = Integer.toString(mes);

        if(mes < 10) {
            mes_selecionado = "0"+Integer.toString(mes);
        }

        // Data mínima
        String minDate = Integer.toString(ano) + "-" + mes_selecionado + "-01";

        // Data máxima
        String maxDate = Integer.toString(ano) + "-" + mes_selecionado + "-31";

        List<MovimentacaoFinanceira> list = mov_sql.buscarEntreDatas(minDate, maxDate);

        db.close();

        return list;
    }

    /**
     * Retorna o saldo do mês
     *
     * @param context
     * @param ano
     * @param mes
     * @return
     */
    public static Double buscarSaldoMensal(Context context, int ano, int mes) {

        List<MovimentacaoFinanceira> mov_list = buscarPorMes(context, ano, mes);

        // Model de movimentação financeira
        MovimentacaoFinanceira mov = null;

        Double result = 0.00;

        // Loop de gastos
        int j = 0; while (mov_list.size() > j) {

            // Load model
            mov = mov_list.get(j);

            result = result + mov.getValor();

            j++;

        }

        return result;
    }
}

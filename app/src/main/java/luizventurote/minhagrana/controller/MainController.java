package luizventurote.minhagrana.controller;

import android.content.Context;
import java.util.Date;
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
}

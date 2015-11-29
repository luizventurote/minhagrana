package luizventurote.minhagrana.model.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class MovimentacaoFinanceiraSql {

    /**
     * Banco de dados
     */
    private SQLiteDatabase database;

    /**
     * Nome da tabela
     */
    private String tabela = "movimentacao_financeira";

    public MovimentacaoFinanceiraSql(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Inserir
     *
     * @param mov
     * @return long
     */
    public long inserir(MovimentacaoFinanceira mov) {

        ContentValues values = new ContentValues();
        values.put("descricao", mov.getDescricao());
        values.put("valor", mov.getValor());
        values.put("data", mov.getData().toString());
        values.put("data_system", mov.getDataSystem().toString());

        Long id = database.insert(tabela, null, values);


        Log.d("LOG_DB", "Registro inserido - ID: " + Long.toString(id));

        return id;
    }

    /**
     * Atualizar
     *
     * @param mov
     */
    public void atualizar(MovimentacaoFinanceira mov) {
    }

    /**
     * Deletar
     *
     * @param mov
     */
    public void deletar(MovimentacaoFinanceira mov) {
    }

    /**
     * Buscar
     */
    public List<MovimentacaoFinanceira> buscarTodos() {

        // Lista
        List<MovimentacaoFinanceira> list = new ArrayList<MovimentacaoFinanceira>();

        // Colunas
        String[] colunas = new String[] {
                "_id",
                "descricao",
                "valor",
                "data",
                "data_system",
        };

        // Cursor de registros
        Cursor cursor = database.query(this.tabela, colunas, null, null, null, null, "_id ASC");

        // Verifica se tem algum resultado
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                MovimentacaoFinanceira mov = new MovimentacaoFinanceira(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getDouble(3)
                );

                list.add(mov);

            } while (cursor.moveToNext());
        }

        return list;
    }
}

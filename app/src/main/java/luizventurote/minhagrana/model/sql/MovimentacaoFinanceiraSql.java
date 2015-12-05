package luizventurote.minhagrana.model.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import luizventurote.minhagrana.helper.Helper;
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
        values.put("data", Helper.formatDateToString(mov.getData()));
        values.put("data_system", Helper.formatDateToString(mov.getDataSystem()));

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

        try {

            ContentValues values = new ContentValues();

            long id = mov.getId();
            values.put("descricao", mov.getDescricao());
            values.put("valor", mov.getValor());
            values.put("data", Helper.formatDateToString(mov.getData()));

            database.update(tabela, values, "_id = ?", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletar
     *
     * @param mov
     */
    public void deletar(MovimentacaoFinanceira mov) {

        long id = mov.getId();
        database.delete(tabela, "_id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Buscar registro por Id
     *
     * @param id
     * @return MovimentacaoFinanceira
     */
    public MovimentacaoFinanceira buscar(Long id) {

        // Cursor de registros
        Cursor cursor =  this.database.rawQuery("select * from " + this.tabela + " where _id ='" + id + "'" , null);

        MovimentacaoFinanceira mov = null;

        // Verifica se tem algum resultado
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                mov = new MovimentacaoFinanceira(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    Helper.formatStringToDate( cursor.getString(3) )
                );

            } while (cursor.moveToNext());
        }

        return mov;
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

    /**
     * Realiza uma busca entre duas datas
     *
     * @param minDate Data minima yyyy-MM-dd
     * @param maxDate Data máxima yyyy-MM-dd
     * @return List<MovimentacaoFinanceira>
     */
    public List<MovimentacaoFinanceira> buscarEntreDatas(String minDate, String maxDate) {

        // Lista
        List<MovimentacaoFinanceira> list = new ArrayList<MovimentacaoFinanceira>();

        // Cursor
        Cursor cursor = database.query("movimentacao_financeira", null, "data BETWEEN ? AND ?", new String[] {
                minDate + " 00:00:00", maxDate + " 23:59:59" }, null, null, "data DESC", null);

        // Verifica se tem algum resultado
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                MovimentacaoFinanceira mov = new MovimentacaoFinanceira(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    Helper.formatStringToDate( cursor.getString(3) )
                );

                list.add(mov);

            } while (cursor.moveToNext());
        }

        return list;
    }
}

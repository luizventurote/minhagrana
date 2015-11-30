package luizventurote.minhagrana.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static Database sInstance = null;

    private static final String DATABASE_NAME    = "minha_grana";
    private static final int    DATABASE_VERSION = 3;

    /**
     * Singleton Pattern
     *
     * @param context
     * @return DatabaseHelper
     */
    public static synchronized Database getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }

        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("LOG_DB", "onCreate Database active");

        // Tabela de movimentação financeira
        db.execSQL("CREATE TABLE movimentacao_financeira (_id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT, valor REAL, data TEXT, data_system TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("LOG_DB", "onUpgrade Database active");

        // Drop movimentação financeira
        db.execSQL("DROP TABLE IF EXISTS movimentacao_financeira");

        // Recreate
        this.onCreate(db);

    }
}

package luizventurote.minhagrana.helper;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class testDatabaseHelper extends AndroidTestCase {

    private Database db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = Database.getInstance(context);
    }

    /**
     * Apaga o banco de dados de teste
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }
}

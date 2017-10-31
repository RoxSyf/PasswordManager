package he2b.esi.g41077.passwordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by g41077 on 31/10/2017.
 */

public class DatabaseManager {

    private Context mContext;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        mContext = context;
    }
}

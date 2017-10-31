package he2b.esi.g41077.passwordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by g41077 on 31/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database information
    private static final String DB_NAME = "PASSWORD_MANAGER.DB";

    // Database version
    private static final int DB_VERSION = 1;

    // Table name
    public static final String TABLE_NAME = "PASSWORDS";

    // Table columns
    public static final String _ID = "_id";
    public static final String LOGIN = "login";
    public static final String PWD = "password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

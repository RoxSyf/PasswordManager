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
    private static final int DB_VERSION = 3;

    // Table name
    public static final String TABLE_NAME = "PASSWORDS";

    // Table columns
    public static final String _ID = "_id";
    public static final String LOGIN = "login";
    public static final String PWD = "password";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOGIN + " TEXT NOT NULL, " + PWD + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

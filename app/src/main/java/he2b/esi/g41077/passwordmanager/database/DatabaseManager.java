package he2b.esi.g41077.passwordmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    public DatabaseManager open() throws SQLException {
        mDBHelper = new DatabaseHelper(mContext);
        database = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDBHelper.close();
    }

    //CRUD
    public void insert(String login, String pwd) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.LOGIN, login);
        contentValue.put(DatabaseHelper.PWD, pwd);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.LOGIN, DatabaseHelper.PWD};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LOGIN, name);
        contentValues.put(DatabaseHelper.PWD, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
}

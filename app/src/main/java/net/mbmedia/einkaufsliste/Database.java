package net.mbmedia.einkaufsliste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String LOG = "OfflineDBHelper";

    public static final String DATABASE_NAME = "Items_DB";
    public static final Integer DATABASE_VERSION = 1;

    public final String TABLE_NAME = "Item";
    public final String NAME = "Name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s (%s TEXT)", TABLE_NAME, NAME);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }

    public void addItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void delItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME + " = ?", new String[]{name});
        db.close();
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return items;
    }

    public void delAll() {
        String del = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(del);
    }

}

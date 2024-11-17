/*
    DatabaseContext - ContactExample
    Copyright (C) 2024-2025 Coppermine-SP.
 */
package corp.cloudint.contactexample.Contexts;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import corp.cloudint.contactexample.Models.Contact;

public class DatabaseContext extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "contacts";

    public DatabaseContext (Context context){
        super(context, "contacts.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, tel TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public List<Contact> getContacts(Optional<String> name){
        //나는 Microsoft EntityFrameworkCore가 너무 그립습니다.
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        if(name.isPresent()){
            cursor = db.query(TABLE_NAME, new String[]{"*"}, "name =?", new String[]{name.get()}, null, null, null, null);
        }
        else {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }
        List<Contact> list = new ArrayList<>();

        while(cursor.moveToNext()) {
            Contact model = new Contact();

            model.Id = Optional.of(cursor.getInt(cursor.getColumnIndex("id")));
            model.Name = cursor.getString(cursor.getColumnIndex("name"));
            model.Tel = cursor.getString(cursor.getColumnIndex("tel"));
            list.add(model);
        }

        cursor.close();
        return list;
    }

    public void addContact(Contact model){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", model.Name);
        values.put("tel", model.Tel);
        db.insert(TABLE_NAME, null, values);
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id =?" , new String[] {String.valueOf(id)});
    }

    public void updateContact(Contact model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", model.Name);
        values.put("tel", model.Tel);

        db.update(TABLE_NAME, values, "id =?", new String[] {model.Id.get().toString()});
    }
}

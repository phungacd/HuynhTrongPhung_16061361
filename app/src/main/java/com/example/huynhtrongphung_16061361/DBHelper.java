package com.example.huynhtrongphung_16061361;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Authors", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table Author(id integer primary key, name text, address text, email text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists Author";
        db.execSQL(query);
        onCreate(db);
    }

    //insert Author
    public boolean insertAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", author.getId_author());
        contentValues.put("name", author.getName());
        contentValues.put("address", author.getAddress());
        contentValues.put("email", author.getEmail());
        db.insert("Author", null, contentValues);
        db.close();
        return true;
    }

    //get Author
    public Author getAuthor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from Author where id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        Author author = new Author(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3));
        cursor.close();
        return author;
    }

    //get All Author
    public ArrayList<Author> getALlAuthor() {
        ArrayList<Author> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from Author";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            list.add(new Author(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    //delete Author
    public boolean deleteAuthor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "delete from Author where id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null) {
            return false;
        } else {
            cursor.moveToFirst();
            cursor.close();
        }
        return true;
    }


    public boolean updateAuthor(int id, String name, String address, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("email", email);
        if (db.update("Author", contentValues, "id = ?", new String[]{String.valueOf(id)}) > 0) {
            db.close();
            return true;
        }
        return false;
    }



}

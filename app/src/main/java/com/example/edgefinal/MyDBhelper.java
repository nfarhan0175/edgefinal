package com.example.edgefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TakeNote";
    private static final String TABLE_NAME = "note";
    private static final int DATA_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NOTE = "note";

    public MyDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_NOTE + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
        Log.d("database", "Database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addnote(String name, String note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_NOTE, note);

        try {
            long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Log.d("fault", "Insertion Failed: " + name);
            } else {
                Log.d("database", "Insertion Successful: " + name);
            }
        } catch (Exception e) {
            Log.e("error", "Database error inserting note: " + name, e);
        }
    }

    public ArrayList<Notemodel> getNotes() { // Correct method name
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Notemodel> noteList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Notemodel noteModel = new Notemodel();
            noteModel.id = cursor.getInt(0);
            noteModel.name = cursor.getString(1);
            noteModel.note = cursor.getString(2);
            noteList.add(noteModel);  // Correctly added the note model to the list
        }
        cursor.close();
        return noteList;
    }

    public void updateNoteByName(String name, String newNote) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, newNote);  // Update only the note field

        // Update the note where the name matches
        sqLiteDatabase.update(TABLE_NAME, values, KEY_NAME + " = ?", new String[]{name});
        Log.d("databaseUpdate", "Note Updated: " + name + " -> " + newNote);
    }

    public void deleteNote(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        // Delete the note where the name matches
        sqLiteDatabase.delete(TABLE_NAME, KEY_NAME + " = ?", new String[]{name});
        Log.d("databaseDelete", "Note deleted: " + name);
    }

}

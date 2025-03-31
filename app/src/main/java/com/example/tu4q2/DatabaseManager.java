package com.example.tu4q2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "Class";
    public static final String DB_TABLE = "student";
    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (StudentID INTEGER, FirstName TEXT, LastName TEXT, YearOfBirth TEXT, Gender TEXT);";

    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    // Constructor
    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    // Open for reading
    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    // Close
    public void close() {
        helper.close();
    }

    // Insert
    public boolean addRow(Integer s, String f, String l, String y, String g) {
        synchronized (this.db) {
            ContentValues newStudent = new ContentValues();
            newStudent.put("StudentID", s);
            newStudent.put("FirstName", f);
            newStudent.put("LastName", l);
            newStudent.put("YearOfBirth", y);
            newStudent.put("Gender", g);
            try {
                db.insertOrThrow(DB_TABLE, null, newStudent);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    // Retrieve
    public ArrayList<String> retrieveRows() {
        ArrayList<String> studentRows = new ArrayList<>();
        String[] columns = new String[]{"StudentID", "FirstName", "LastName", "YearOfBirth", "Gender"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String row = cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4);
            studentRows.add(row);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    // Delete all
    public void clearRecords() {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    // Update
    public void updateRows(int studentID, String firstName, String lastName, String yearOfBirth, String gender) {
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("YearOfBirth", yearOfBirth);
        values.put("Gender", gender);
        int rowsUpdated = db.update(DB_TABLE, values, "StudentID = ?", new String[]{String.valueOf(studentID)});
        Log.d("DatabaseManager", rowsUpdated + " rows updated.");
    }

    // SQLite Helper
    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Student table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}

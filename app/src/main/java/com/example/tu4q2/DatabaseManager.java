package com.example.tu4q2;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseManager {
    public static final String DB_NAME = "Class";
    public static final String DB_TABLE = "student";



    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (StudentID INTEGER, FirstName TEXT, LastName TEXT, YearOfBirth TEXT, Gender TEXT);";


    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    //for the product table
    public boolean addRow(Integer s, String f, String l, String y,  String g) {
        synchronized(this.db) {

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
            //db.close();
            return true;
        }
    }


    public String retrieveRows() {
        String studentRows = "";
        String[] columns = new String[] {"studentID", "FirstName", "LastName", "YearOfBirth", "Gender"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            studentRows = studentRows + cursor.getInt(0) + ", " + cursor.getString(1) + ", "+ cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4) +"\n";
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }



//    public void updateRows(int studentID, String FirstName, String LastName, String YearOfBirth, String Gender){
//        ContentValues values = new ContentValues();
//        values.put("StudentID", studentID);
//        values.put("FirstName", FirstName);
//        values.put("LastName", LastName);
//        values.put("YearOfBirth", YearOfBirth);
//        values.put("Gender", Gender);
//
//        int rowsUpdated = db.update(DB_TABLE, values, "studentID = " + studentID, null);
//
//    }


    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
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

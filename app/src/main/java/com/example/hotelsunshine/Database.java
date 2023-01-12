package com.example.hotelsunshine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Room.db";
    public static final String TABLE_NAME = "Room_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "First_Name";
    public static final String COL_3 = "Last_Name";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "Mobile_Number";
    public static final String COL_6 = "Check_In_Date";
    public static final String COL_7 = "Check_Out_Date";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, First_Name TEXT, Last_Name TEXT, Email TEXT, Mobile_Number INTEGER, Check_In_Date INTEGER, Check_Out_Date INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Insert Data
    public boolean insertData(String firstname,String lastname,String email,String number,String checkin,String checkout){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,firstname);
        contentValues.put(COL_3,lastname);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,number);
        contentValues.put(COL_6,checkin);
        contentValues.put(COL_7,checkout);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Read Data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    //Update Data
    public int updateInfo(String fname,String lname,String email,String number,String checkin,String checkout){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,fname);
        values.put(COL_3,lname);
        values.put(COL_4,email);
        values.put(COL_5,number);
        values.put(COL_6,checkin);
        values.put(COL_7,checkout);
        String selection = COL_2 + " LIKE ?";
        String[] selectionArgs = {fname};
        int rowId = sqLiteDatabase.update(TABLE_NAME, values, selection, selectionArgs);
        return rowId;
    }

    //Delete Data
    public int deleteInfo(String fname){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selection = COL_2 + " LIKE ?";
        String[] selectionArgs = { fname };
        int rowId = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
        return rowId;
    }
}

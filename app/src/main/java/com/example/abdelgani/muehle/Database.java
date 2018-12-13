package com.example.abdelgani.muehle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Database extends SQLiteOpenHelper
{
    public static String DatabaseName = "User.db";
    public static String TableName = "User_table";

    public Database( Context context) {
        super( context, DatabaseName, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL( "Create table " + TableName +"(Email text primary key, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL( "Drop table if exists " + TableName );
    }

    //Insert content into Database
    public boolean insert(String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put( "email", email );
        content.put( "password", password );
        long ins = db.insert( TableName, null, content );
        if(ins == -1)
        {
            return  false;
        }
        else
        {
            return  true;
        }
    }

    public boolean checkAccount(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery( "Select * from " + TableName + " Where email =?" , new String[]{email} );
        Cursor cursor2 = db.rawQuery( "Select * from " + TableName + " Where password =?" , new String[]{password} );
        if(cursor1.getCount()>0)
        {
            if (cursor2.getCount()>0)
            {
                return false;
            }
            else
            {
                return  true;
            }
        }
        else
        {
            return  true;
        }
    }

}

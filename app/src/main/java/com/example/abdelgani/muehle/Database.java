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
    private static final int DatabaseVersion = 2;
    public static String TableName = "User_table";
    private static final String Table_Score = "t_score";
    private static final String col_Score_ID = "scoreID";
    private static final String col_Score_player1 = "player1";
    private static final String col_Score_player2 = "player2";
    private static final String col_Score_winner = "winner";


    public Database( Context context) {
        super( context, DatabaseName, null, DatabaseVersion );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL( "Create table " + TableName +"(Email text primary key, password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDbVersion, int newDbVersion)
    {
		int upgradeTo = oldDbVersion + 1;
		while (upgradeTo <= newDbVersion){
			switch(upgradeTo){
				case 2:
				sqLiteDatabase.execSQL( "PRAGMA foreign_keys = ON" );
				String	script = "CREATE TABLE "+Table_Score+"("+col_Score_ID+" INT PRIMARY KEY, "+
																col_Score_player1+" TEXT NOT NULL, "+
																col_Score_player2+" TEXT NOT NULL, "+
																col_Score_winner+" TEXT, " +
																"FOREIGN KEY ("+col_Score_player1+") REFERENCES "+TableName+" (Email), " +
																"FOREIGN KEY ("+col_Score_player2+") REFERENCES "+TableName+" (Email));";
				sqLiteDatabase.execSQL(script);
				break;
			}
			upgradeTo++;
		}
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
        Cursor cursor1 = db.rawQuery( "Select * from " + TableName + " Where email =? AND password = ?" , new String[]{email, password}  );
        //Cursor cursor2 = db.rawQuery( "Select * from " + TableName + " Where password =?" , new String[]{password} );
        if(cursor1.getCount() >0 )//&& cursor2.getCount() >0)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }

    public boolean t_score_insert(String player1, String player2, String winner){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues content = new ContentValues();
    	content.put(col_Score_player1, player1);
    	content.put(col_Score_player2, player2);
    	content.put(col_Score_winner, winner);
    	long rowID = db.insert(Table_Score, null, content);
    	if(rowID == -1)
    		return false;
    	return true;
	}

}

package com.example.abdelgani.muehle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MultiPlayerActivity extends AppCompatActivity {

    Database seconddatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_multi_player );


       // Intent openLogIN = new Intent( MultiPlayerActivity.this, MainActivity.class );
       // startActivity( openLogIN );

    }
}

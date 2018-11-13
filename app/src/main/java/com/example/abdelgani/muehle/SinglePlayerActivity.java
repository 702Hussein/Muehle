package com.example.abdelgani.muehle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;

public class SinglePlayerActivity extends AppCompatActivity
{

    Database seconddatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );

        String ich;
        TextView text;

         text = (TextView)findViewById( R.id.PlayerName );

         //ich = getString(  );

       // ich = Name.getText().toString();

        text.setText("name ist : ");

    }
}

package com.example.abdelgani.muehle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity
{



    public Button SinglePlayer;
    public Button MultiPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );



        SinglePlayer = (Button) findViewById( R.id.btnSinglePlayerID );
        SinglePlayer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSinglePlyerActivity();
            }
        } );


        MultiPlayer = (Button)findViewById( R.id.btnMultiPlayerActivity );
        MultiPlayer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMultiPlayerActivity();
            }
        } );

    }



    public void OpenSinglePlyerActivity()
    {
                Intent OpenSinglePlayerActivity = new Intent(SecondActivity.this, SinglePlayerActivity.class );
                startActivity( OpenSinglePlayerActivity);
    }


    public void OpenMultiPlayerActivity()
    {
                Intent OpenMultiPlayerActivity = new Intent( SecondActivity.this, MultiPlayerActivity.class );
                startActivity( OpenMultiPlayerActivity );
    }



}

package com.example.abdelgani.muehle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    public void onBackPressed(){
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SecondActivity.this);

        dlgBuilder.setMessage("Sind sie sicher dass Sie das Spiel verlassen wollen?");

        dlgBuilder.setCancelable(true);
        dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(SecondActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
                Intent Login = new Intent( SecondActivity.this, MainActivity.class );
                startActivity( Login );
                finish();
                //Intent secondAct = new Intent( SinglePlayerActivity.this, SecondActivity.class );
                //startActivity( secondAct );
            }
        });
        dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = dlgBuilder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        alert.show();
    }



}

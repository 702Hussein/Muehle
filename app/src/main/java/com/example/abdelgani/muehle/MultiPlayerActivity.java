package com.example.abdelgani.muehle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelgani.muehle.Classes.Player;

public class MultiPlayerActivity extends AppCompatActivity {

    Database seconddatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_multi_player );

       // Intent openLogIN = new Intent( MultiPlayerActivity.this, MainActivity.class );
       // startActivity( openLogIN );

        //Log.d(null,  " Hallo ");
    }




    public void onBackPressed()
    {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(MultiPlayerActivity.this);
        dlgBuilder.setTitle( Html.fromHtml("<font color='#190707'>Mühle</font>"));
        dlgBuilder.setMessage(Html.fromHtml( "<font color='#190707'>Sind sie sicher dass Sie das Spiel verlassen wollen?</font>"));
        dlgBuilder.setCancelable(true);
        dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                Intent openSecond = new Intent( MultiPlayerActivity.this, SecondActivity.class );
                startActivity( openSecond );
                Toast.makeText(MultiPlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
                finish();
                //Intent secondAct = new Intent( MultiPlayerActivity.this, SecondActivity.class );
                //startActivity( secondAct );
            }
        });
        dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = dlgBuilder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        alert.show();
    }


}

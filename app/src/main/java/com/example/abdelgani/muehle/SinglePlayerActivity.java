package com.example.abdelgani.muehle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Console;

public class SinglePlayerActivity extends AppCompatActivity
{
    Database seconddatabase ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );
    }


    // Zurück zur SecondActivity fall die Rückwärts taste gedrückt wird.
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);

            dlgBuilder.setMessage("Sind sie sicher dass Sie das Spiel verlassen wollen?");

            dlgBuilder.setCancelable(true);
            dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
            {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(SinglePlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
                Intent secondAct = new Intent( SinglePlayerActivity.this, SecondActivity.class );
                startActivity( secondAct );
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
        return true;
    }

}

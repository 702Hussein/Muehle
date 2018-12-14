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
import android.widget.EditText;
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
                //Intent OpenMultiPlayerActivity = new Intent( SecondActivity.this, MultiPlayerActivity.class );
                //startActivity( OpenMultiPlayerActivity );
        //Intent openPopup = new Intent( SecondActivity.this, Popup.class );
        //startActivity( openPopup );
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder( SecondActivity.this );
        View popUp_View = getLayoutInflater().inflate( R.layout.second_player_popup, null );
        final EditText userName = (EditText) popUp_View.findViewById( R.id.userName );
        final EditText password = (EditText) popUp_View.findViewById( R.id.password );
        Button logIn = (Button) popUp_View.findViewById( R.id.Login_popup);

        logIn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
                {
                    Toast.makeText( SecondActivity.this, "Login Succefully", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    Toast.makeText( SecondActivity.this, "Files are empty", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        mBuilder.setView( popUp_View );

        android.app.AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void onBackPressed()
    {
                Intent Login = new Intent( SecondActivity.this, MainActivity.class );
                startActivity( Login );
                finish();
    }



}

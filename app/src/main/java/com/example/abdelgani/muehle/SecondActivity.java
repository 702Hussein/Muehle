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

import com.example.abdelgani.muehle.Classes.Player;

import java.io.Console;

public class SecondActivity extends AppCompatActivity
{



    public Button SinglePlayer;
    public Button MultiPlayer;
    Button logIn;
    Database myDatabase;
    EditText User_Name;
    EditText User_Password;
    String namePlayer2;
    String passwordPlayer2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );

        myDatabase = new Database( this );


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
               final Intent OpenMultiPlayerActivity = new Intent( SecondActivity.this, MultiPlayerActivity.class );
                //startActivity( OpenMultiPlayerActivity );
        //Intent openPopup = new Intent( SecondActivity.this, Popup.class );
        //startActivity( openPopup );
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder( SecondActivity.this );
        final View popUp_View = getLayoutInflater().inflate( R.layout.second_player_popup, null );

        logIn = (Button) popUp_View.findViewById( R.id.Login_popup);

        logIn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                User_Name = (EditText) popUp_View.findViewById( R.id.userNameID );
                User_Password = (EditText) popUp_View.findViewById( R.id.userPasswordID );
                namePlayer2 = User_Name.getText().toString();
                passwordPlayer2 = User_Password.getText().toString();

                if(User_Name.equals( "" )|| User_Password.equals( "" ))
                {
                    Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                        boolean check_Account = myDatabase.checkAccount( namePlayer2, passwordPlayer2 );
                        if (check_Account == false)
                        {
                            Toast.makeText( getApplicationContext(), "Please register first", Toast.LENGTH_SHORT ).show();
                        }
                        else
                            {
                            Toast.makeText( getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT ).show();
                            Player player2 = new Player( namePlayer2 );
                            startActivity( OpenMultiPlayerActivity );
                        }
                        User_Name.getText().clear();
                        User_Password.getText().clear();
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

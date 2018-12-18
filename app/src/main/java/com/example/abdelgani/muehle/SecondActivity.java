package com.example.abdelgani.muehle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelgani.muehle.Classes.Player;

import java.security.PublicKey;

public class SecondActivity extends AppCompatActivity
{



    public Button SinglePlayer;
    public Button MultiPlayer;
    Button logIn;
    Database myDatabase;
    EditText User_Name;
    EditText User_Password;
    EditText User_Password_Repeat;
    String secondPlayerName;
    String secondPlayerPassword;
    String secondPlayerRepeatPassword;
    //public Player player2;
    public  MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );

         //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

        //mediaPlayer.start();


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
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder( SecondActivity.this );
        final View popUp_login_View = getLayoutInflater().inflate( R.layout.popup_login, null );

        logIn = (Button) popUp_login_View.findViewById( R.id.Login_popup);

        logIn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                User_Name = (EditText) popUp_login_View.findViewById( R.id.userNameID );
                User_Password = (EditText) popUp_login_View.findViewById( R.id.userPasswordID );
                secondPlayerName = User_Name.getText().toString();
                secondPlayerPassword = User_Password.getText().toString();

                if(secondPlayerName.equals( "" )|| secondPlayerPassword.equals( "" ))
                {
                    Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                        boolean check_Account = myDatabase.checkAccount( secondPlayerName, secondPlayerPassword );
                        if (check_Account == false)
                        {
                            Toast.makeText( getApplicationContext(), "Please register first", Toast.LENGTH_SHORT ).show();
                        }
                        else
                            {
                            Toast.makeText( getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT ).show();
                            Player player2 = new Player( secondPlayerName );
                            startActivity( OpenMultiPlayerActivity);
                        }
                        User_Name.getText().clear();
                        User_Password.getText().clear();
                }
            }
        } );

        mBuilder.setView( popUp_login_View );
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.show();


        Button Register = (Button) popUp_login_View.findViewById( R.id.RegisterID );
        Register.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                final View popUp_Register_View = getLayoutInflater().inflate( R.layout.popup_register, null );
                mBuilder.setView( popUp_Register_View );
                final android.app.AlertDialog dialog2 = mBuilder.create();
                dialog2.show();

                Button RegisterBtnFromRegisterDialog = (Button) popUp_Register_View.findViewById( R.id.RegisterID_from_Register_dialog );
                RegisterBtnFromRegisterDialog.setOnClickListener( new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        User_Name = (EditText) popUp_Register_View.findViewById( R.id.userNameID2 );
                        User_Password = (EditText) popUp_Register_View.findViewById( R.id.userPasswordID2 );
                        User_Password_Repeat =(EditText) popUp_Register_View.findViewById( R.id.userPasswordID2_2 );

                        secondPlayerName = User_Name.getText().toString();
                        secondPlayerPassword = User_Password.getText().toString();
                        secondPlayerRepeatPassword = User_Password_Repeat.getText().toString();

                        if (secondPlayerName.equals( "" ) || secondPlayerPassword.equals( "" ) || secondPlayerRepeatPassword.equals( "" ))
                        {
                            Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
                        }
                        else
                        {
                                if (!secondPlayerPassword.equals( secondPlayerRepeatPassword ))
                                {
                                    Toast.makeText( getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT ).show();
                                }
                                else
                                {
                                    boolean check_Account = myDatabase.checkAccount( secondPlayerName, secondPlayerPassword);
                                    if(check_Account == false)
                                    {
                                        boolean insert = myDatabase.insert( secondPlayerName, secondPlayerPassword );
                                        if (insert == true)
                                        {
                                            Toast.makeText( getApplicationContext(), "Register successfully", Toast.LENGTH_SHORT ).show();
                                            Player player2 = new Player(secondPlayerName);
                                            //startActivity( OpenSecondActivity );
                                        }
                                        else
                                        {
                                            Toast.makeText( getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT ).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText( getApplicationContext(), "Account already exists, please use login menu", Toast.LENGTH_SHORT ).show();
                                        //startActivity( OpenmainActivity );
                                        dialog2.cancel();
                                        dialog.show();
                                    }
                                }
                        }
                    }
                } );


            }
        } );

    }

    public void onBackPressed()
    {
                Intent Login = new Intent( SecondActivity.this, MainActivity.class );
                startActivity( Login );
                finish();
    }



}

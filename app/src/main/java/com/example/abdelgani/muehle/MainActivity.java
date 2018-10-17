package com.example.abdelgani.muehle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public EditText Name;
    public EditText Password;
    public Button Login;
    public String name;
    public String password;
    Database MyDatabase;

    //Button mButton = (Button) findViewById(R.id.btnLoginID);

    public void OpenNewActivityByButtonClick()
    {
        Login = (Button)findViewById( R.id.btnLoginID);
        Login.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent OpenSecondActivity = new Intent( MainActivity.this, SecondActivity.class);


                name = Name.getText().toString();
                password = Password.getText().toString();

                if(name.equals( "" )|| password.equals( "" ))
                {
                    Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    boolean checkMail = MyDatabase.checkEmail( name );
                    if(checkMail == true)
                    {
                        boolean insert = MyDatabase.insert( name, password );
                        if(insert == true)
                        {
                            Toast.makeText( getApplicationContext(), "Login Successfuly", Toast.LENGTH_SHORT ).show();
                            startActivity( OpenSecondActivity );
                        }
                    }
                    else
                    {
                        Toast.makeText( getApplicationContext(), "E-Mail already exists", Toast.LENGTH_SHORT ).show();
                        startActivity( OpenSecondActivity );
                    }
                }
            }
        } );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Name = (EditText)findViewById( R.id.btnNameID );
        Password = (EditText)findViewById( R.id.btnPasswordID );
        Login = (Button)findViewById( R.id.btnLoginID );

        MyDatabase = new Database( this );

        OpenNewActivityByButtonClick();

    }
        //https://www.youtube.com/watch?v=lF5m4o_CuNg

            //private void validate (String userName, String userPassword)
            //{
            //}

    

}

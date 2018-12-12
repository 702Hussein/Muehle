package com.example.abdelgani.muehle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity
{

    public Button SignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );



        SignIn = (Button)findViewById( R.id.SignInID );
        SignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        } );

    }



    public void Signin()
    {
        EditText UserName;
        EditText Password;
        EditText Password2;
        String name;
        String password;
        String password2;
        Database MyDatabase;
        MyDatabase = new Database( this );

        Intent OpenSecondActivity = new Intent( RegisterActivity.this, SecondActivity.class);
        Intent OpenmainActivity = new Intent( RegisterActivity.this, MainActivity.class);
        UserName = (EditText)findViewById( R.id.UsernameID );
        Password = (EditText)findViewById( R.id.PasswordID );
        Password2 = (EditText)findViewById( R.id.PasswordID2 );


        name =UserName.getText().toString();
        password = Password.getText().toString();
        password2 =Password2.getText().toString();

        if (name.equals( "" ) || password.equals( "" ) || password2.equals( "" ))
        {
            Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
        }
        else
        {
            boolean check_Account = MyDatabase.checkAccount( name, password);
            if(check_Account == true)
            {
                if (!password.equals( password2 ))
                {
                    Toast.makeText( getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    boolean insert = MyDatabase.insert( name, password );
                    if (insert == true)
                    {
                        Toast.makeText( getApplicationContext(), "Register Successfuly", Toast.LENGTH_SHORT ).show();
                        startActivity( OpenSecondActivity );
                    }
                    else
                    {
                        Toast.makeText( getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
            else
            {
                Toast.makeText( getApplicationContext(), "Account already exists, please use login menu", Toast.LENGTH_SHORT ).show();
                startActivity( OpenmainActivity );
            }

            UserName.getText().clear();
            Password.getText().clear();
            Password2.getText().clear();
        }


    }
}

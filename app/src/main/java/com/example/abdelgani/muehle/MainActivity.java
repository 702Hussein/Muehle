package com.example.abdelgani.muehle;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public EditText User_Name;
    public EditText User_Password;
    public Button Login;
    public Button Register;
    public String name;
    public String password;
    Database MyDatabase;


    public  String UserName;

    //Button mButton = (Button) findViewById(R.id.btnLoginID);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        MyDatabase = new Database( this );
        User_Name = (EditText)findViewById( R.id.btnNameID );
        User_Password = (EditText)findViewById( R.id.btnPasswordID );



        Login = (Button)findViewById( R.id.btnLoginID );
        Login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpensecondActivity();
            }
        } );


        Register =(Button) findViewById( R.id.btnRegisterID );
        Register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegisterActivity();
            }
        } );
    }


    public  void OpenRegisterActivity()
    {
        Intent OpenRegisterActivity = new Intent( MainActivity.this, RegisterActivity.class);
                startActivity( OpenRegisterActivity );
    }


    public void OpensecondActivity()
    {
         Intent OpenSecondActivity = new Intent( MainActivity.this, SecondActivity.class);

                name = User_Name.getText().toString();
                password = User_Password.getText().toString();



                if(name.equals( "" )|| password.equals( "" ))
                {
                    Toast.makeText( getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    boolean check_Account = MyDatabase.checkAccount( name, password);
                    if(check_Account == true)
                    {
                            Toast.makeText( getApplicationContext(), "Please register first", Toast.LENGTH_SHORT ).show();

                    }
                    else
                    {
                        Toast.makeText( getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT ).show();
                        startActivity( OpenSecondActivity );
                    }
                    User_Name.getText().clear();
                    User_Password.getText().clear();
                }
    }



        //https://www.youtube.com/watch?v=lF5m4o_CuNg
            //private void validate (String userName, String userPassword)
            //{


            //}
}

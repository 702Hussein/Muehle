package com.example.abdelgani.muehle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText password;
    private Button Login;

    Button mButton = (Button) findViewById(R.id.btnLoginID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Name = (EditText)findViewById( R.id.NameID );
        password = (EditText)findViewById( R.id.passwordID );
        Login = (Button)findViewById( R.id.btnLoginID );

    }
//https://www.youtube.com/watch?v=lF5m4o_CuNg

    private void validate (String userName, String userPassword)
    {

    }

    

}

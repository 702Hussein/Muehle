package com.example.abdelgani.muehle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText password;
    public Button Login;
    //Button mButton = (Button) findViewById(R.id.btnLoginID);

    public void OpenNewActivityByButtonClick()
    {
        Login = (Button)findViewById( R.id.btnLoginID);
        Login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenSecondActivity = new Intent( MainActivity.this, SecondActivity.class);
                startActivity( OpenSecondActivity );
            }
        } );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Name = (EditText)findViewById( R.id.btnNameID );
        password = (EditText)findViewById( R.id.btnPasswordID );
        Login = (Button)findViewById( R.id.btnLoginID );
        OpenNewActivityByButtonClick();

    }
//https://www.youtube.com/watch?v=lF5m4o_CuNg

    //private void validate (String userName, String userPassword)
    //{
    //}

    

}

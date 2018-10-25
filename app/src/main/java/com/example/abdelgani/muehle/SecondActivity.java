package com.example.abdelgani.muehle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {


    public Button back;
    public Button SinglePlayer;
    public Button MultiPlyer;


    public void BackToMainActivity()
    {
        back = (Button)findViewById( R.id.btnBackID);
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackToMainActivity = new Intent(SecondActivity.this, MainActivity.class);
                startActivity( BackToMainActivity);
            }
        } );
    }

    public void OpenSinglePlyerActivity()
    {
        SinglePlayer = (Button) findViewById( R.id.btnSinglePlayerID );


        SinglePlayer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenSinglePlayerActivity = new Intent( SecondActivity.this, SinglePlayerActivity.class );
                startActivity( OpenSinglePlayerActivity);
            }
        } );
    }

    public void OpenMultiPlyerActivity()
    {
        MultiPlyer = (Button)findViewById( R.id.btnMultiPlyerActivity );
        MultiPlyer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenMultiPlayerActivity = new Intent( SecondActivity.this, MultiPlayerActivity.class );
                startActivity( OpenMultiPlayerActivity );
            }
        } );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_second );

        BackToMainActivity();
        OpenSinglePlyerActivity();
        OpenMultiPlyerActivity();

    }
}

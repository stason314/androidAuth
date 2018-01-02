package com.example.stani.loginlogout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by stani on 18.10.2017.
 */

public class InLogActivity extends Activity{

    private TextView nick;

    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inlog_layout);

        nick = findViewById(R.id.nick_name);
        logOut = findViewById(R.id.buttonLogOut);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nick.setText(getIntent().getStringExtra("nickname"));
        //Log.d("users", getIntent().getStringExtra("users"));
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

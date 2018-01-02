package com.example.stani.loginlogout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by stani on 17.10.2017.
 */

public class MainActivity extends Activity {


    private Button logIn;
    private Button signIn;

    private EditText loginT;
    private EditText passwordT;
    private TextView error;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String defaultPass;


    private Runnable runnable;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //TODO shared preference complete
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);


        logIn =  findViewById(R.id.buttonLog);
        signIn = findViewById(R.id.buttonSign);

        loginT = findViewById(R.id.editLogIn);
        passwordT = findViewById(R.id.editPass);
        error = findViewById(R.id.error);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        defaultPass = getResources().getString(R.string.default_password);


        Log.d("on create", "here");


    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String data = bundle.getString("error");
            error.setText(data);
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("on start", "here");

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    runnable = new Runnable() {
                    @Override
                    public void run() {
                        boolean flag = false;
                        String text = "";
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        String password;
                        Intent intent = new Intent(MainActivity.this, InLogActivity.class);

                        password = sharedPref.getString(loginT.getText().toString(), defaultPass);
                        if (passwordT.getText().toString().equals(password)){
                            intent.putExtra("nickname", loginT.getText().toString());
                            startActivity(intent);
                            flag=true;
                        }
                        Log.d("MainActivity", password);


                        if (!flag){
                            //error.setText("neverniy login or pass");
                            text = "neverniy login or pass";
                        }else {
                            //error.setText("U LOGING");
                            text = "U LOGING";
                        }

                        bundle.putString("error", text);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                };
                thread = new Thread(runnable);
                thread.start();

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String def = sharedPref.getString(loginT.getText().toString(), defaultPass);
                if (loginT.getText().length() == 0) {
                    //error
                    error.setText("Vvedite login");

                }else
                if (passwordT.getText().length() == 0){
                    //error
                    error.setText("Vvedite password");

                }else
                if (def.equals(defaultPass)) {
                    error.setText("u SIGN UP");
                    editor.putString(loginT.getText().toString(), passwordT.getText().toString());
                    editor.commit();

                }else {
                    error.setText("User allready exist");
                    }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("MainActivity", "pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "restart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "resume");
        loginT.setText("");
        passwordT.setText("");
        error.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "stop");
        if (thread != null){
            Thread dump = thread;
            thread = null;
            dump.interrupt();
            Log.e("thread", "stop");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "here");
    }
}

package com.zhy.berrydic.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.berrydic.R;

public class LoginActivity extends Activity {
    private EditText accountEdit;
    private EditText passwordEidt;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylogin);
        accountEdit= (EditText) findViewById(R.id.account);
        passwordEidt=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountEdit.getText().toString();
                String password=passwordEidt.getText().toString();
                if(account.equals("dong") && password.equals("dong123")){
                    Intent intent=new Intent(LoginActivity.this, BerryDictionaryActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "account or password wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

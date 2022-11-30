package com.example.fbloginapi;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    Button loginbutton;
    //callback manager: handler for logins/login responses
    CallbackManager callbackManager = CallbackManager.Factory.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text
        TextView mytxt = (TextView) findViewById(R.id.textView);

        //button for login (can also use Facebook's built in UI)
        loginbutton = (Button) findViewById(R.id.fbbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    //the success of the login would ideally bring you to another activity, but for simplicity, the results are posted as a Toast.
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getBaseContext(),"Login Success", Toast.LENGTH_SHORT).show();

                        String accessToken = loginResult.getAccessToken().getToken(); //this would save access token upon log in
                        Log.i("accessToken", accessToken); //... except emulator is not too friendly with installing facebook app
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getBaseContext(),"Login Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getBaseContext(),"Problem connecting to Facebook", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //when the log in results exist, it passes them to LoginManager via callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
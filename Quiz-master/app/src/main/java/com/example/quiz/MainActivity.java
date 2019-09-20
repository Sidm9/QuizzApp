package com.example.quiz;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button signin;
    TextView signup;

    ConstraintLayout back;
    AnimationDrawable am;

    private FirebaseAuth mAuth;

    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signin=(Button) findViewById(R.id.in_sign);
        signup=(TextView) findViewById(R.id.up_sign);

        back=findViewById(R.id.back);

        am=(AnimationDrawable) back.getBackground();
        am.setEnterFadeDuration(1000);
        am.setExitFadeDuration(1000);
        am.start();

        mAuth=FirebaseAuth.getInstance();

        player=MediaPlayer.create(MainActivity.this, R.raw.m3);
        player.setLooping(true);
        player.start();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if(e.isEmpty()){
                    email.setError("Enter Email");
                }
                else if(p.isEmpty()){
                    password.setError("Enter password");
                }
                else {
                    mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign in successfully!!!", Toast.LENGTH_SHORT).show();
                                player.stop();
                                startActivity(new Intent(MainActivity.this, firstActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Sign in Failed!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
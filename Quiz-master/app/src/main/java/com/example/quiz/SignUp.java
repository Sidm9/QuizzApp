package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    EditText email,password;
    Button signup;
    TextView signin;
    ConstraintLayout back;
    AnimationDrawable am;

    private FirebaseAuth mAuth;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        signin=findViewById(R.id.signin);

        player=MediaPlayer.create(SignUp.this,R.raw.m2);
        player.setLooping(true);
    //    player.start();

        back=findViewById(R.id.back);
        am=(AnimationDrawable) back.getBackground();
        am.setEnterFadeDuration(3000);
        am.setExitFadeDuration(1000);
        am.start();

        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
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
                else{
                    mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(SignUp.this,MainActivity.class));
                                player.stop();
                                startActivity(new Intent(SignUp.this, firstActivity.class));
                            } else {
                                Toast.makeText(SignUp.this, "Registration Failed!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}

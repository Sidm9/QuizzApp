package com.example.quiz;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class firstActivity extends AppCompatActivity {

    TextView ques,score,time;
    String answer;
    Button choice1,choice2,choice3,choice4,next;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int count=0,choice=0,marks,correct=0;
    private MediaPlayer player;
    private CountDownTimer timer;
    private long timeleft=60000;

    ConstraintLayout back;
    AnimationDrawable am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ques=findViewById(R.id.ques);
        choice1=findViewById(R.id.c1);
        choice2=findViewById(R.id.c2);
        choice3=findViewById(R.id.c3);
        choice4=findViewById(R.id.c4);
        next=findViewById(R.id.next);
        score=findViewById(R.id.score);
        database=FirebaseDatabase.getInstance();
        time=findViewById(R.id.timer);




        timer=new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeleft=millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                player.stop();
                Bundle extras=new Bundle();
                Toast.makeText(firstActivity.this, "TIME OVER!!!", Toast.LENGTH_SHORT).show();
                extras.putInt("marks",marks);
                extras.putInt("ques",count);
                extras.putInt("correct",correct);
                Intent i=new Intent(firstActivity.this,Statistics.class);
                i.putExtras(extras);
                startActivity(i);
            }
        }.start();

        back=findViewById(R.id.back);

        am=(AnimationDrawable) back.getBackground();
        am.setEnterFadeDuration(1000);
        am.setExitFadeDuration(1000);
        am.start();


        player=MediaPlayer.create(firstActivity.this,R.raw.m1);
        player.setLooping(true);
        player.start();

        next.setEnabled(false);

        choice1.setBackgroundColor(getColor(R.color.dark_blue));
        choice2.setBackgroundColor(getColor(R.color.dark_blue));
        choice3.setBackgroundColor(getColor(R.color.dark_blue));
        choice4.setBackgroundColor(getColor(R.color.dark_blue));

        next_ques(count);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                next.setEnabled(false);
                if(count<=10){
                    count+=1;
                    choice1.setBackgroundColor(getColor(R.color.dark_blue));
                    choice2.setBackgroundColor(getColor(R.color.dark_blue));
                    choice3.setBackgroundColor(getColor(R.color.dark_blue));
                    choice4.setBackgroundColor(getColor(R.color.dark_blue));
                    next_ques(count);
                }
                else {
                    player.stop();
                    Bundle extras=new Bundle();
                    Toast.makeText(firstActivity.this, "GAME OVER!!!", Toast.LENGTH_SHORT).show();
                    extras.putInt("marks",marks);
                    extras.putInt("ques",count);
                    extras.putInt("correct",correct);
                    Intent i=new Intent(firstActivity.this,Statistics.class);
                    i.putExtras(extras);
                    startActivity(i);
                }

            }
        });
    }

    void updateTimer(){
        int minutes=(int)timeleft/60000;
        int seconds=(int)timeleft%60000/1000;
        String timeleft=""+minutes+":";
        if(seconds<10)
            timeleft+="0";
        timeleft+=seconds;
        time.setText(timeleft);
    }


    void reset()
    {
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);
        choice4.setEnabled(true);
    }


    void click(int choice)
    {
        if(choice==1){
            choice2.setEnabled(false);
            choice3.setEnabled(false);
            choice4.setEnabled(false);
        }
        else if(choice==2){
            choice1.setEnabled(false);
            choice3.setEnabled(false);
            choice4.setEnabled(false);
        }
        else if(choice==3){
            choice2.setEnabled(false);
            choice1.setEnabled(false);
            choice4.setEnabled(false);
        }
        else{
            choice2.setEnabled(false);
            choice3.setEnabled(false);
            choice1.setEnabled(false);
        }
        next.setEnabled(true);
    }


    void next_ques(final int count){
        myRef=database.getReference("questions/"+count);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String q=dataSnapshot.child("question").getValue().toString();
                String c1=dataSnapshot.child("choices").child("0").getValue().toString();
                String c2=dataSnapshot.child("choices").child("1").getValue().toString();
                String c3=dataSnapshot.child("choices").child("2").getValue().toString();
                String c4=dataSnapshot.child("choices").child("3").getValue().toString();
                answer=dataSnapshot.child("answer").getValue().toString();
                ques.setText(q);
                choice1.setText(c1);
                choice2.setText(c2);
                choice3.setText(c3);
                choice4.setText(c4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.equals(choice1.getText().toString())){
                    choice1.setBackgroundColor(getColor(R.color.green));
                    Toast.makeText(firstActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    marks+=1;
                    correct+=1;
                }
                else {
                    choice1.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(firstActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    marks-=1;
                }
                choice=1;
                click(choice);

                score.setText("Score: "+marks);
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.equals(choice2.getText().toString())){
                    choice2.setBackgroundColor(getColor(R.color.green));
                    Toast.makeText(firstActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    marks+=1;
                    correct+=1;
                }
                else {
                    choice2.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(firstActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    marks-=1;
                }
                choice=2;
                click(choice);
                score.setText("Score: "+marks);
            }
        });

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.equals(choice3.getText().toString())){
                    choice3.setBackgroundColor(getColor(R.color.green));
                    Toast.makeText(firstActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    marks+=1;
                    correct+=1;
                }
                else {
                    choice3.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(firstActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    marks-=1;
                }
                choice=3;
                click(choice);
                score.setText("Score: "+marks);
            }
        });

        choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.equals(choice4.getText().toString())){
                    choice4.setBackgroundColor(getColor(R.color.green));
                    Toast.makeText(firstActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    marks+=1;
                    correct+=1;
                }
                else {
                    choice4.setBackgroundColor(getColor(R.color.red));
                    Toast.makeText(firstActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    marks-=1;
                }
                choice=4;
                click(choice);
                score.setText("Score: "+marks);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}

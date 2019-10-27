package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    TextView cans,wans,tques,tmarks;
    Button exit;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        cans=findViewById(R.id.cans);
        wans=findViewById(R.id.wans);
        tques=findViewById(R.id.tques);
        tmarks=findViewById(R.id.tmarks);
        exit=findViewById(R.id.exit);

        player=MediaPlayer.create(Statistics.this,R.raw.m4);
        player.setLooping(true);
//        player.start();

        Intent i=getIntent();
        Bundle extras=i.getExtras();
        int c=extras.getInt("correct");
        int t=extras.getInt("ques");
        int w=t-c;
        int tm=extras.getInt("marks");

        cans.setText("Correct Answers: "+c);
        wans.setText("Wrong answers: "+w);
        tques.setText("Total Questions "+t);
        tmarks.setText("Total Marks: "+tm);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}

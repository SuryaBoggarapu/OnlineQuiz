package com.example.onlinequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText id;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.quizid);


        btn1=findViewById(R.id.quizinfo);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qid = id.getText().toString();
                System.out.println("QuizId: " + qid);
                Intent i=new Intent(MainActivity.this,QuizInfo.class);
                i.putExtra("qid", Integer.parseInt(qid));
                startActivity(i);
            }
        });

        btn2=findViewById(R.id.reschedule);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qid = id.getText().toString();
                System.out.println("QuizId: " + qid);
                Intent i=new Intent(MainActivity.this,RescheduleQuiz.class);
                i.putExtra("qid", Integer.parseInt(qid));
                startActivity(i);
            }
        });
    }
}

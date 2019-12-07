package com.example.onlinequiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;

public class QuizInfo extends AppCompatActivity {


    TextView qid, qtype, starttime, endtime, qdate, marks;
    String qtype1, starttime1, endtime1, qdate1, id1, marks1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);

        qid = findViewById(R.id.quizid);
        qtype = findViewById(R.id.qtype);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        qdate = findViewById(R.id.qdate);
        marks = findViewById(R.id.marks);

        new MyTask().execute();

    }

    public class MyTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;

            Intent intent = getIntent();
            int id = intent.getIntExtra("qid", 1);
            System.out.println("QuizId_QUIZINFO: " + id);

            try{
                url = new URL("http://192.168.5.8:8080/Team10/WebServices/OnlineQuiz/QuizInfo&" + id);

                HttpURLConnection httpURLConnection = null;

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");

                int responseCode = httpURLConnection.getResponseCode();

                System.out.println("URL: " + url);
                System.out.println("Response Code: " + responseCode);

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());

                BufferedReader in = new BufferedReader(inputStreamReader);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response: " + response.toString());

                JSONObject mainObj = new JSONObject(response.toString());

                marks1 = String.valueOf(mainObj.getInt("marks"));
                id1 = String.valueOf(mainObj.getInt("Quiz ID"));
                qtype1 = mainObj.getString("qtype");
                starttime1 = mainObj.getString("starttime").substring(10);
                endtime1 = mainObj.getString("endtime").substring(10);
                qdate1 = mainObj.getString("qdate");




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            qid.setText(id1);
            qtype.setText(qtype1);
            qdate.setText(qdate1);
            marks.setText(marks1);
            starttime.setText(starttime1);
            endtime.setText(endtime1);
            super.onPostExecute(aVoid);
        }
    }
}
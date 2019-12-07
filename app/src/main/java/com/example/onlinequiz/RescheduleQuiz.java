package com.example.onlinequiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RescheduleQuiz extends AppCompatActivity {


    EditText qdate;
    String qdate1, message;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_quiz);

        qdate = findViewById(R.id.date);

        btn = findViewById(R.id.update);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qdate1 = qdate.getText().toString();

                new MyTask().execute();
            }
        });

    }

    public class MyTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;

            Intent intent = getIntent();
            int id = intent.getIntExtra("qid", 1);
            System.out.println("QuizId_QUIZINFO: " + id);

            try{
                url = new URL("http://192.168.5.8:8080/Team10/WebServices/OnlineQuiz/reschedulequiz&" + id + "&" + qdate1);

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

                message = mainObj.getString("Message");




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

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }
}
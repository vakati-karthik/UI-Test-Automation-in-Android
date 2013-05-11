package edu.sjsu.cs185c.hw02;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MainActivity extends Activity {

    private Question question;
    
    private  ListView lv;
    
    private final static String url = "http://horstmann.com/sjsu/spring2013/cs185c/hw02/quiz.xml";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            lv = (ListView) findViewById(R.id.questionList);
            new ReadFromURL(lv).execute(new URL(url));
        } catch (MalformedURLException e) {
            Log.e(getClass().toString(), e.getMessage());
        } catch (ActivityNotFoundException e) {
            Log.e(getClass().toString(), e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        int questionIndex = data.getIntExtra("questionIndex", 0);
        String answered = data.getStringExtra("answered");
        lv = (ListView) findViewById(R.id.questionList);
        ((TextView)(lv.getChildAt(questionIndex))).setBackgroundColor(answered.equals("Green") ? Color.GREEN : Color.RED);
    }
    
    private class ReadFromURL extends AsyncTask<URL, Void, String> {
        
        private final ListView lView;
        
        ArrayAdapter<String> adapter;

        public ReadFromURL(
                ListView lView)
        {
            this.lView = lView;
        }
        
        @Override
        protected String doInBackground(URL... urls) {
            question = new Question(); 
            try {
                question.readFromXml(urls[0].openStream());
            } catch (IOException e) {
                Log.e(getClass().toString(), e.getMessage());
            }
            return null;
        }
        
        protected void onPostExecute(String result1) {
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, question.getQuestions());
            lView.setAdapter(adapter);
            lView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                	Drawable db = ((TextView)arg1).getBackground();
                    if (db == null) {
                        Intent intent = new Intent(MainActivity.this, ResponseActivity.class);
                        intent.putExtra("questionIndex", arg0.getPositionForView(arg1));
                        intent.putExtra("question", adapter.getItem(arg0.getPositionForView(arg1)));
                        intent.putExtra("questionObject", question);
                        startActivityForResult(intent, 0);
                    }
                }});
        }

    }
    
}


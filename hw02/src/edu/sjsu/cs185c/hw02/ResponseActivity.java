package edu.sjsu.cs185c.hw02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResponseActivity extends Activity {
    
    ArrayAdapter<String> adapter;
    
    Question question;
    
    int questionIndex;
    
    String qn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        questionIndex = this.getIntent().getIntExtra("questionIndex", 4);
        qn = this.getIntent().getStringExtra("question");
        this.setTitle(qn);
        question = (Question)(this.getIntent().getSerializableExtra("questionObject"));
        adapter = new ArrayAdapter<String>(ResponseActivity.this, android.R.layout.simple_list_item_1, question.getQuizzes().get(qn));
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                Intent intent = new Intent(ResponseActivity.this, MainActivity.class);
                intent.putExtra("questionIndex", questionIndex);
                intent.putExtra("answered", 
                        question.isCorrect(qn, arg0.getPositionForView(arg1)) ? "Green" : "Red");
                setResult(0, intent);
                finish();
                
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_response, menu);
        return true;
    }

}

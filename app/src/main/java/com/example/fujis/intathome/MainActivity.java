package com.example.fujis.intathome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton light = findViewById(R.id.light);
        light.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),LightSetting.class);
                startActivity(intent);
            }
        });
        ImageButton fuzai = findViewById(R.id.fuzai);
        fuzai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),FuzaiSetting.class);
                startActivity(intent);
            }
        });
        ImageButton help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),HelpSetting.class);
                startActivity(intent);
            }
        });
        ImageButton copyright = findViewById(R.id.copyright);
        copyright.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),CopyRightSetting.class);
                startActivity(intent);
            }
        });
        ImageButton feedback = findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),FeedBackSetting.class);
                startActivity(intent);
            }
        });
        GetLightJson task = new GetLightJson();
        task.execute("http://intathome.azurewebsites.net/api/light");
        GetFuzaiJson task_f = new GetFuzaiJson();
        task_f.execute("http://intathome.azurewebsites.net/api/response");
    }
}

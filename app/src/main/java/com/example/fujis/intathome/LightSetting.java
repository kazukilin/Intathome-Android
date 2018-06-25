package com.example.fujis.intathome;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class LightSetting extends Activity {
    public static String startH,finishH,startM,finishM,turnS;
    public static Boolean isFunc;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_light);
        final Spinner start_sec1 = findViewById(R.id.start_sec1);
        start_sec1.setSelection(Integer.parseInt(startH));
        System.out.println(startH);
        final Spinner start_sec2 = findViewById(R.id.start_sec2);
        start_sec2.setSelection(Integer.parseInt(startM));
        final Spinner end_sec1 = findViewById(R.id.end_sec1);
        end_sec1.setSelection(Integer.parseInt(finishH));
        final Spinner end_sec2 = findViewById(R.id.end_sec2);
        end_sec2.setSelection(Integer.parseInt(finishM));
        final EditText light_sec = findViewById(R.id.light_sec);
        light_sec.setHint(turnS);
        final CheckBox light_dis = findViewById(R.id.light_dis);
        light_dis.setChecked(!isFunc);
        Button save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://intathome.azurewebsites.net/api/light";
                String s_sec1 = (String)start_sec1.getSelectedItem();
                String s_sec2 = (String)start_sec2.getSelectedItem();
                String e_sec1 = (String)end_sec1.getSelectedItem();
                String e_sec2 = (String)end_sec2.getSelectedItem();
                String l_sec = light_sec.getText().toString();
                String start_sec = s_sec1 + s_sec2;
                String end_sec = e_sec1 + e_sec2;
                boolean func = true;
                if(light_dis.isChecked()) func = false;
                String time = l_sec;
                String Json = "{\"begin\":\""+start_sec+"\",\"end\":\""+end_sec+"\",\"function\":"+func+",\"ontime\":\""+time+"\"}";
                JsonPost task = new JsonPost();
                task.execute(url,Json);
                GetLightJson tasks = new GetLightJson();
                tasks.execute("http://intathome.azurewebsites.net/api/light");
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GetLightJson task = new GetLightJson();
            task.execute("http://intathome.azurewebsites.net/api/light");
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}

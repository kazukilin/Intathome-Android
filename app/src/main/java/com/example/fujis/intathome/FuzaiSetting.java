package com.example.fujis.intathome;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FuzaiSetting extends Activity implements MediaPlayer.OnCompletionListener {
    public static boolean Func;
    Button recplay;
    boolean recnow = false;
    boolean isRec = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_fuzai);
        final CheckBox fuzai_set = findViewById(R.id.fuzaiset);
        final Button butex = findViewById(R.id.new_massage);
        recplay = findViewById(R.id.rerec);
        recplay.setVisibility(View.INVISIBLE);
        fuzai_set.setChecked(Func);
        Button massage = findViewById(R.id.new_massage);
        massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == 0) {
                    recplay.setVisibility(View.INVISIBLE);
                    recnow = true;
                    startMediaRecord();
                    butex.setText("録音を停止");
                    state++;
                } else if (state == 1) {
                    stopRecord();
                    recnow = false;
                    isRec = true;
                    butex.setText("新規メッセージ録音開始");
                    recplay.setVisibility(View.VISIBLE);
                    state = 0;
                }
            }
        });

        recplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recplay.setText("再生中…");
                startPlay();
            }
        });

        Button save = findViewById(R.id.fuzai_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!recnow && isRec) {
                    String url = "http://intathome.azurewebsites.net/api/response";
                    boolean func = false;
                    if (fuzai_set.isChecked()) func = true;
                    String Json = "{\"response\":" + func + "}";
                    JsonPost task = new JsonPost();
                    task.execute(url, Json);
                    dec64();
                    finish();
                } else if(recnow) {
                    stopRecord();
                    finish();
                } else{
                    finish();
                }
            }
        });
    }

    private MediaRecorder mediarecorder; //録音用のメディアレコーダークラス
    static final String filePath = "/data/data/com.example.fujis.intathome/files/recorded.mp4"; //録音用のファイルパス
    private int state = 0;

    private void startMediaRecord() {
        try {
            File mediafile = new File(filePath);
            if (mediafile.exists()) {
                mediafile.delete();
            }
            mediarecorder = new MediaRecorder();
            mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediarecorder.setOutputFile(filePath);
            mediarecorder.prepare();
            mediarecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        if (mediarecorder == null) {
            Toast.makeText(FuzaiSetting.this, "mediarecorder = null", Toast.LENGTH_SHORT).show();
        } else {
            try {
                mediarecorder.stop();
                mediarecorder.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private MediaPlayer mp;

    private void startPlay() {
        try {
            mp = new MediaPlayer();
            mp.setDataSource(filePath);
            mp.prepare();
            mp.start();
            mp.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        recplay.setText("再生");
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

    public void dec64(){
        try {
            FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(new File(filePath));
            byte[] readBytes = new byte[fileInputStream.available()];
            fileInputStream.read(readBytes);
            String res = Base64.encodeToString(readBytes, Base64.NO_WRAP);
            JsonPost jp = new JsonPost();
            jp.execute("http://intathome.azurewebsites.net/api/blob",res);
            finish();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
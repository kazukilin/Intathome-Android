package com.example.fujis.intathome;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLightJson extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params){
        String str = "";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            str = InputStreamToString(con.getInputStream());
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return str;
    }

    @Override
    protected void onPostExecute(String str) {
        str = str.replace("\\","");
        str = str.substring(1,str.length()-1);
        try {
            JSONObject jsonObject = new JSONObject(str);
            String b = jsonObject.getString("begin");
            String sh = b.substring(0,2);
            if(sh.substring(0,1) == "0") sh = b.substring(1,2);
            LightSetting.startH = sh;
            String sm = b.substring(2,4);
            if(sm.substring(0,1) == "0") sm = b.substring(3,4);
            LightSetting.startM = sm;
            String e = jsonObject.getString("end");
            String eh = e.substring(0,2);
            if(eh.substring(0,1) == "0") eh = e.substring(1,2);
            LightSetting.finishH = eh;
            String em = e.substring(3,4);
            if(em.substring(0,1) == "0") em = e.substring(3,4);
            LightSetting.finishM = em;
            LightSetting.turnS = jsonObject.getString("ontime");
            LightSetting.isFunc = jsonObject.getBoolean("function");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}

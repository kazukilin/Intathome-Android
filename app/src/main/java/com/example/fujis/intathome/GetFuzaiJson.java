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

public class GetFuzaiJson extends AsyncTask<String,Void,String> {
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
            FuzaiSetting.Func = jsonObject.getBoolean("response");
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

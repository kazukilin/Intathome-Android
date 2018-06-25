package com.example.fujis.intathome;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

public class JsonPost extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        try {
            URL urls = new URL(params[0]);
            con = (HttpURLConnection) urls.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
            con.setRequestProperty("Content-Length", String.valueOf(params[1].length()));
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(params[1]);
            out.flush();
            con.connect();
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                System.out.println(status);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String str) {
        System.out.println(str);
    }
}

package com.hs.mykuaidi.httpTools;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 黄晟 on 2016/8/25.
 */
public  class HttpUtil {

    public HttpUtil () {

    }
    public static String getJsonConteny(String url_path) {

        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if (code == 200) {
                //Log.i("Httputil",changeInputStream(connection.getInputStream()));
                return changeInputStream(connection.getInputStream());
            }
        } catch (Exception e) {
            Log.i("Http","httpyichang");
        }
        return "";
    }

    private static String changeInputStream(InputStream inputStream) {
        String jsonstring = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonstring = new String(outputStream.toByteArray());
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonstring;
    }
}

package com.hs.mykuaidi.jsonTools;

import com.hs.mykuaidi.model.Kuaidi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄晟 on 2016/9/3.
 */
public class JsonTools {
    /**
     * 得到快递模型集合
     *
     * @param jsonstring
     * @return
     */
    public static List<Kuaidi> getDataByJson(String jsonstring) {
        List<Kuaidi> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String time = jsonObject1.getString("time");
                String[] date = time.split(" ");
                String context = jsonObject1.getString("context");
                Kuaidi kuaidi = new Kuaidi(context, date[0], date[1]);
                list.add(kuaidi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取最新的时间  对比数据中之前的时间  不同则通知用户数据已更新
     * @param jsonstring
     * @return
     */
    public static String getNewDate(String jsonstring) {
        String time = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                time = jsonObject1.getString("time");  // 获取最新的时间
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getKuaidiType(String jsonstring) {
        String type = "yunda";
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObject.getJSONArray("auto");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            type = jsonObject1.getString("comCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 判断快递单号是否正确
     *
     * @param jsonstring 服务器端返回的json数据
     * @param id         快递单号
     * @return
     */
    public static Boolean idIsOk(String jsonstring, String id) {
        Boolean isok = false;
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            String boolstring = jsonObject.getString("message");
            if (boolstring.equals("ok")) {
                isok = true;
            } else {
                isok = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isok;
    }

}

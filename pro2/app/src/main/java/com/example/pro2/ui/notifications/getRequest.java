package com.example.pro2.ui.notifications;

//import android.util.JsonReader;
//import org.json.JSONObject;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSONArray;
import org.json.JSONArray;
import org.json.JSONObject;

//import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getRequest {
    public  StringBuilder GetReq (String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        //System.out.println(result.toString());
        // return result.toString();
        return result;
    }

    /**
     * 通过城市名称及day获取天气信息
     * @param day
     * @param cityCode
     * @return
     * @throws Exception
     */
    //传入 预报日期 第0-3天 ， 以及城市的高德天气代码
    public String[] getJSON(int day,String cityCode) throws Exception
    {
        //System.out.println(getHTML("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2"));
        String Url = "https://restapi.amap.com/v3/weather/weatherInfo?city=";
        Url = Url + cityCode;
        Url = Url + "&key=7e5182d424aed687b5157f62eae915f2&extensions=all";
       // StringBuilder str = GetReq("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2&extensions=all");
        StringBuilder str = GetReq(Url);
        System.out.println(str.toString());
        String s = str.toString();
        //JSONObject jsonObject = JSONObject.parseObject(s);
        // System.out.println(jsonObject.getJSONObject("lives").get("weather"));
        JSONObject jsonObject = new JSONObject(s);
        System.out.println(jsonObject);
       // System.out.println(jsonObject.getJSONObject("forecasts").getJSONObject("0").getJSONObject("casts").getJSONObject("0"));

        JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("forecasts");
        JSONObject JSONObject1 = jsonArray.getJSONObject(0);
        System.out.println(JSONObject1);
        JSONArray jsonArray1 = (JSONArray) JSONObject1.getJSONArray("casts");
        JSONObject JSONObject2 = jsonArray1.getJSONObject(day);
        System.out.println(JSONObject2);
        System.out.println(JSONObject2.get("dayweather"));

        String[] ans = new String[4];
        ans[0] = (String) JSONObject2.get("dayweather");
        ans[1] = (String) JSONObject2.get("daytemp");
        ans[2] = (String) JSONObject2.get("nightweather");
        ans[3] = (String) JSONObject2.get("nighttemp");
        return ans;
    }
}//

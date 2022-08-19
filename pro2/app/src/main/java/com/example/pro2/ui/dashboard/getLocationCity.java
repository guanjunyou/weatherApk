package com.example.pro2.ui.dashboard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getLocationCity {
    public  StringBuilder getReq (String urlToRead) throws IOException {
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
        // return result.toString();+

        return result;
    }

    //type : name 城市名称  code  城市代码
    public String[] getCity(String lon,String lat) throws Exception
    {
        //System.out.println(getHTML("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2"));
        String Url = "https://restapi.amap.com/v3/geocode/regeo?location=";
        Url += lon;
        Url += ",";
        Url += lat;
        Url += "&key=7e5182d424aed687b5157f62eae915f2";
        // StringBuilder str = GetReq("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2&extensions=all");
        System.out.println(Url);
        StringBuilder str = getReq(Url);
        System.out.println(str.toString());
        String s = str.toString();

        JSONObject jsonObject = new JSONObject(s);

        JSONObject regeocode = jsonObject.getJSONObject("regeocode");
        JSONObject addressComponent = regeocode.getJSONObject("addressComponent");
        String adcode = (String) addressComponent.get("adcode");
        String city = (String) addressComponent.get("city");
        if(city.equals(""))
        {
            city = (String) addressComponent.get("province");
        }

        String[] ans = new String[2];
        ans[0] = city;
        ans[1] = adcode;
        //System.out.println(ans[0]+ans[1]);
        return ans;
    }
}

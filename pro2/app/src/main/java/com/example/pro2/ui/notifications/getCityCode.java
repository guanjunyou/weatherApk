package com.example.pro2.ui.notifications;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getCityCode {
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
        // return result.toString();
        return result;
    }

    public String getCode(String CityName) throws Exception
    {
        //System.out.println(getHTML("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2"));
        String Url = "https://restapi.amap.com/v3/geocode/geo?key=7e5182d424aed687b5157f62eae915f2&city=";
        Url += CityName;
        Url += "&address=";
        Url += CityName;
        // StringBuilder str = GetReq("https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=7e5182d424aed687b5157f62eae915f2&extensions=all");
        StringBuilder str = getReq(Url);
        System.out.println(str.toString());
        String s = str.toString();

        JSONObject jsonObject = new JSONObject(s);
       // System.out.println(jsonObject);
        // System.out.println(jsonObject.getJSONObject("forecasts").getJSONObject("0").getJSONObject("casts").getJSONObject("0"));

        JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("geocodes");
        JSONObject JSONObject1 = jsonArray.getJSONObject(0);
        //System.out.println(JSONObject1);
        //JSONObject codeJson = JSONObject1.getJSONObject("adcode");
        String ans = (String) JSONObject1.get("adcode");
        System.out.println(ans);
        //String ans = codeJson.get("adcode");
        return ans;
    }
}

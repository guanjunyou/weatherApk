package com.example.pro2.ui.notifications;

import android.graphics.Color;
//import android.media.tv.TimelineRequest;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.pro2.R;
import com.example.pro2.databinding.FragmentNotificationsBinding;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String[] weather;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




//        // Android 4.0 之后不能在主线程中请求HTTP请求  要开子线程
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                getRequest Request = new getRequest();
//                try {
//                    weather = Request.getJSON(0, "110101");
//                   // binding.textView14.setText(weather);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();I]K=8


        //https://restapi.amap.com/v3/geocode/geo?key=7e5182d424aed687b5157f62eae915f2&city=北京市&address=北京市

        String getcity = getArguments().getString("getcity");
        System.out.println(getcity+"传过来了");
        notificationsViewModel.city = getcity;  //接收城市
        //binding.textView4.setText(notificationsViewModel.city); //设置城市

        //以下为请求高德接口获取城市代码
        getCityCode CityCode = new getCityCode();
        String code = "" ;
        try {
            code = CityCode.getCode(getcity);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //获取当前时间判断是白天还是晚上
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String weatherStr , temperature;


        getRequest Request = new getRequest();
        try {
            weather = Request.getJSON(0, code);
            // binding.textView14.setText(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(hour);
        String Time = "白天";
        if(hour >= 6 && hour <= 18)
        {
            //白天
            weatherStr = weather[0];
            temperature = weather[1]+"℃  ";
            Time = "白天";
        }
        else
        {
            //黑夜
            weatherStr = weather[2];
            temperature = weather[3]+"℃  ";
            Time = "夜间";
        }
        binding.textView4.setText(notificationsViewModel.city+"   "+Time); //设置城市

        binding.textView14.setText(weatherStr);
        binding.textView15.setText(temperature);

        //以下为加载 mipmap里面的图片

        if(weatherStr.equals("雷阵雨"))
            binding.imageView.setImageResource(R.mipmap.thunder);
        else if("晴".equals(weatherStr))
            binding.imageView.setImageResource(R.mipmap.sunny);
        else if("小雨".equals(weatherStr))
            binding.imageView.setImageResource(R.mipmap.samllrain);
        else if("中雨".equals(weatherStr))
            binding.imageView.setImageResource(R.mipmap.midrain);
        else
            binding.imageView.setImageResource(R.mipmap.cloud);


        //明天天气
        getRequest Request1 = new getRequest();
        try {
            weather = Request1.getJSON(1, code);
            // binding.textView14.setText(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(weather[2]);

        String tomorrow = "明天 ";
        tomorrow += weather[0];
        tomorrow += "/";
        tomorrow +=weather[2];
        tomorrow += " ";
        tomorrow += weather[1];
        tomorrow += "℃~";
        tomorrow += weather[3];
        tomorrow += "℃  ";
        binding.textView6.setText(tomorrow);

        //后天天气
        getRequest Request2 = new getRequest();
        try {
            weather = Request2.getJSON(2, code);
            // binding.textView14.setText(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String after_tomorrow = "后天 ";
        after_tomorrow += weather[0];
        after_tomorrow += "/";
        after_tomorrow +=weather[2];
        after_tomorrow += " ";
        after_tomorrow += weather[1];
        after_tomorrow += "℃~";
        after_tomorrow += weather[3];
        after_tomorrow += "℃  ";
        binding.textView7.setText(after_tomorrow);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void main(String[] args) {

    }




}


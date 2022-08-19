package com.example.pro2.ui.dashboard;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.pro2.R;
import com.example.pro2.databinding.FragmentDashboardBinding;
import android.annotation.SuppressLint;

import android.location.Location;
import android.location.LocationListener;

import com.example.pro2.MyApplication;

public class Menu extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public FragmentDashboardBinding binding;
    public  String city = "尚未获取";
    private String lat = ""; //纬度
    private String lon = "";  //经度
    private Double latitue = 0.0;
    private Double longtitue = 0.0;
    public static boolean flag;// 是否获取到了经纬度
    public static String localCity;

    public static void init()
    {
        Menu.flag = false;
    }


    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(@NonNull Location location) {

            double late = location.getLatitude();
            double lone = location.getLongitude();

            if(late != 0.0 && lone!=0.0 && !Menu.flag)
            {
                latitue = late;
                longtitue = lone;
                //System.out.println(latitue + " " + longtitue);
                lat = String.valueOf(latitue);
                lon = String.valueOf(longtitue);
                System.out.println("经纬度"+lat+" "+lon);


                getLocationCity local = new getLocationCity();
                try {
                    String[] localCity = local.getCity(lon,lat);
                   // System.out.println(localCity);
                    System.out.println("城市："+localCity[0]+"代码："+localCity[1]);
                    binding.button6.setText("我所在的城市: "+localCity[0]);
                    city = localCity[0];
                    Menu.localCity = city;
                    System.out.println(city);
                    Menu.flag = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("city:"+city);

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @SuppressLint({"MissingPermission", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



        if(!Menu.flag)
        {
            System.out.println("进来了！！");
            Context context = MyApplication.getContext();

            String serviceName = Context.LOCATION_SERVICE;
            LocationManager myLocationManager = (LocationManager)context.getSystemService(serviceName);


            if (myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER ))
            {
                //  netLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L,  0, (LocationListener) new MyLocationListener());
            }
            else
                System.out.println("GPS 关闭");
            // }
        }
        else
        {
            binding.button6.setText("我所在的城市: "+Menu.localCity);
        }





        final Button btn3 = (Button) root.findViewById(R.id.button3);
        final Button btn4 = (Button) root.findViewById(R.id.button4);
        final Button btn5 = (Button) root.findViewById(R.id.button5);
        final Button btn6 = (Button) root.findViewById(R.id.button6);

        OnClick onClick = new OnClick();
        btn3.setOnClickListener(onClick);
        btn4.setOnClickListener(onClick);
        btn5.setOnClickListener(onClick);
        btn6.setOnClickListener(onClick);

//9uy67
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class OnClick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.button3:
                    //System.out.println("北京");
                    city =  "北京";
                    binding.textView8.setText("当前城市：北京");
                   break;
                case R.id.button4:
                   // System.out.println("广州");
                    city = "广州";
                    binding.textView8.setText("当前城市：广州");

                    break;
                case R.id.button5:
                    city = "佛山";
                   binding.textView8.setText("当前城市：佛山");
                    break;
                case R.id.button6:
                    city = Menu.localCity;
                    binding.textView8.setText("当前城市："+city);
                    break;
                default:
                    break;
            }
            System.out.println("看这里");
            System.out.println(Menu.flag);
            if(!Menu.flag)
            {
                city = "北京";
            }

            NavController controller = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            System.out.println(city+"传走");
            bundle.putString("getcity",city);
            NavController navController = Navigation.findNavController(v);
            controller.navigate(R.id.action_navigation_dashboard_to_navigation_notifications,bundle);//第2个带参数
        }


    }


}
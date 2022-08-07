package com.example.pro2.ui.dashboard;

import android.os.Bundle;
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
import com.example.pro2.MainActivity;
import com.example.pro2.R;
import com.example.pro2.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public FragmentDashboardBinding binding;
    public String city;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
                    city = "上海";
                    binding.textView8.setText("当前城市：上海");
                    break;
                default:
                    break;
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
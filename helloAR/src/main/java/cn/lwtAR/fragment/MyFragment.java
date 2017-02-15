package cn.lwtAR.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.lwtAR.AboutUsActivity;
import cn.lwtAR.R;
import cn.lwtAR.UserGuideActivity;

public class MyFragment extends Fragment {
    private RelativeLayout rl_setting_about;
    private RelativeLayout rl_setting_help;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        rl_setting_about = (RelativeLayout) view.findViewById(R.id.rl_setting_about);
        rl_setting_help = (RelativeLayout) view.findViewById(R.id.rl_setting_help);
        rl_setting_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        rl_setting_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserGuideActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

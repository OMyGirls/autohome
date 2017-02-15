package cn.lwtAR;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arta.lib.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import cn.lwtAR.fragment.FindFragment;
import cn.lwtAR.fragment.MyFragment;
import cn.lwtAR.view.CameraView;
import cn.lwtAR.view.RippleImageView;

public class HomeActivity extends BaseActivity {

/*    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_btn_scan)
    View tabBtnScan;
    @BindView(R.id.tab_bar)
    TabBarView tabBar;*/
    private LinearLayout ll_find;
    private FrameLayout fl_enter_scan;
    private LinearLayout ll_my;

    private Fragment mContent;
    private FragmentManager mFragmentMan;

    private FindFragment findFragment;
    private MyFragment plusOneFragment;

    private RippleImageView btn_start_scan;
    private RelativeLayout rl_camera_data;

    private CameraView cameraView;
    private int cameraIndex = 0;

    private TextView tv_my;
    private TextView tv_find;
    private ImageView iv_my;
    private ImageView iv_find;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //TopTarSet.setWindowStatusBarColor(this,android.R.color.holo_red_dark);
        findViewById(R.id.fl_home_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rl_camera_data = (RelativeLayout) findViewById(R.id.rl_camera_data);
        initCamera();

        findViewById(R.id.fl_home_activity).requestFocus();
        findFragment = new FindFragment();
        plusOneFragment = new MyFragment();

        btn_start_scan = (RippleImageView) findViewById(R.id.btn_start_scan);

        btn_start_scan.post(new Runnable() {
            @Override
            public void run() {
                btn_start_scan.startWaveAnimation();
            }
        });

        btn_start_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , UnityActivity.class);
                startActivity(intent);
            }
        });


        mFragmentMan = getFragmentManager();

        /*mContent = findFragment;
        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        transaction.add(R.id.fl_home_fragment, findFragment).commit();*/

        findViewById(R.id.iv_start_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContent!=null) {
                    FragmentTransaction transaction = mFragmentMan.beginTransaction();
                    transaction.hide(mContent).commit();
                }

                tv_find.setTextColor(getResources().getColor(R.color.black));
                tv_my.setTextColor(getResources().getColor(R.color.black));
                iv_my.setBackgroundResource(R.drawable.tab_person_normal);
                iv_find.setBackgroundResource(R.drawable.tab_faxian_normal);
            }
        });
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        fl_enter_scan = (FrameLayout) findViewById(R.id.fl_enter_scan);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);

        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_my = (TextView) findViewById(R.id.tv_my);
        iv_find = (ImageView) findViewById(R.id.iv_find);
        iv_my = (ImageView) findViewById(R.id.iv_my);

        ll_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchContent (mContent, findFragment);
                tv_find.setTextColor(getResources().getColor(R.color.low_yellow));
                iv_find.setBackgroundResource(R.drawable.tab_faxian_pressed);
                tv_my.setTextColor(getResources().getColor(R.color.black));
                iv_my.setBackgroundResource(R.drawable.tab_person_normal);
            }
        });
        /*ll_find.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                Log.e("yuan","onSystemUiVisibilityChange");
                ll_find.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                *//*
                View decorView = getWindow().getDecorView();
                int uiOptions= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN;

                decorView.setSystemUiVisibility(uiOptions);*//*
            }
        });*/
        fl_enter_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , UnityActivity.class);
                startActivity(intent);
            }
        });
        ll_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchContent (mContent,plusOneFragment);
                tv_find.setTextColor(getResources().getColor(R.color.black));
                tv_my.setTextColor(getResources().getColor(R.color.low_yellow));
                iv_my.setBackgroundResource(R.drawable.tab_person_pressed);
                iv_find.setBackgroundResource(R.drawable.tab_faxian_normal);
            }
        });
    }

    private void initCamera() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(dm.widthPixels, dm.heightPixels);
        cameraView = new CameraView(this, dm.widthPixels, dm.heightPixels, cameraIndex);
        rl_camera_data = (RelativeLayout)findViewById(R.id.rl_camera_data);
        rl_camera_data.addView(cameraView, layoutParams);

    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.startCamera(cameraIndex);
    }

    public void switchContent(Fragment from, Fragment to) {
        if (mContent == null){
            mContent = to;
            FragmentTransaction transaction = mFragmentMan.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                Log.d("add:",to.toString());
                transaction.add(R.id.fl_home_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                Log.d("show:",to.toString());
                transaction.show(to).commit(); // 隐藏当前fragment，显示下一个fragment
            }
        }
        else
        if (mContent != to||to.isHidden()) {
            mContent = to;
            FragmentTransaction transaction = mFragmentMan.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                Log.d("add:",to.toString());
                transaction.hide(from).add(R.id.fl_home_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                Log.d("show:",to.toString());
                transaction.hide(from).show(to).commit(); // 隐藏当前fragment，显示下一个fragment
            }
        }
    }

    private boolean closeEnable = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!closeEnable) {
                closeEnable = true;
                ToastUtils.show(this, "再按一次退出程序!");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        closeEnable = false;
                    }
                }, 2000);
                return true;
            }
            else {
                finish();
                UnityActivity.activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



/*    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null){
            final int index = intent.getIntExtra("a", 0);
            tabBar.post(new Runnable() {
                @Override
                public void run() {
                    tabBar.selectTabView(index, false);
                    vpHome.setCurrentItem(index, false);
                }
            });
        }
    }*/

   /* private void init() {
        ce = DefaultApplication.getInstance().getEd().getFunctionManageFX(rf.FunctionUnityShow);

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new PersonFragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        vpHome.setAdapter(fragmentAdapter);
        vpHome.setCurrentItem(0, false);

        tabBar.setOnTabViewSelectListener(new OnTabViewSelectListener() {
            @Override
            public void onTabViewSelectedListener(TabBarView tabBarView, View view, int i) {
                vpHome.setCurrentItem(i, false);
                for (int index = 0; index < fragmentList.size(); index++){
                    if(i == index){
                        fragmentList.get(index).onResume();
                    }
                    else {
                        fragmentList.get(index).onPause();
                    }
                }
            }
        });
    }

    @OnClick({R.id.tab_btn_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_btn_scan:
                ce.openARScan(this);
                break;
        }
    }

    private boolean closeEnable = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(tabBar.getCurrentItem() == 0){
                boolean flag = ((DiscoverFragment)fragmentList.get(0)).onKeyDown(keyCode, event);
                if(flag){
                    return true;
                }
            }
            if(!closeEnable){
                closeEnable = true;
                ToastUtils.show(this, "再按一次退出程序!");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        closeEnable = false;
                    }
                }, 2000);
                return true;
            }
            else{
                DefaultApplication.getInstance().exitApp();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setFullScreen(boolean isFullscreen){
        if(isFullscreen){
            tabBar.setVisibility(View.GONE);
        }
        else {
            tabBar.setVisibility(View.VISIBLE);
        }
    }*/

}

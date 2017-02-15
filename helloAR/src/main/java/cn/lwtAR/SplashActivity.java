package cn.lwtAR;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * Splash 启动页
 * 
 * @author 王春龙
 * 
 */
public class SplashActivity extends BaseActivity implements Runnable,View.OnClickListener {

	private ViewGroup main;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		/*main = (ViewGroup) getLayoutInflater().from(this).inflate(R.layout.activity_splash, null);
		main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		main.setOnClickListener(this);
		main.setFitsSystemWindows(true);
		main.setClipToPadding(true);
		setContentView(main);*/

		setContentView(R.layout.activity_splash);
		/*Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (true) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.transparent);
		setContentView(main);*/
		new Thread(SplashActivity.this).start();
////
		//startSplash();

		//ce ce = DefaultApplication.getInstance().getEd().getFunctionManageFX(rf.FunctionUnityShow);
		//ce.openARPre(this);
		//finish();
		//overridePendingTransition(R.anim.activity_fading, R.anim.activity_fading);
	}

/*	private void startSplash() {
		showSplashAnimation();
	}

	private void showSplashAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(1500);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				new Thread(SplashActivity.this).start();
			}
		});
		View view = findViewById(R.id.splash_layout);
		view.setAnimation(alphaAnimation);
		alphaAnimation.start();
	}*/

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
//			Class<?> cls = PreferencesUtils.getBoolean(this, Constant.PREFERENCE_SHOW_GUIDE_ENABLE, true) ?
//					GuideActivity.class : HomeActivity.class;
			Intent intent = new Intent(this, HomeActivity.class);
			this.startActivity(intent);
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		int i = main.getSystemUiVisibility();
		if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
			return;
		} else if (i == View.SYSTEM_UI_FLAG_VISIBLE){
			main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		} else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
			main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
		main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}
}
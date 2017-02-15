package cn.lwtAR;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);
		}
		//全屏
		//getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);


		/*Window _window = getWindow();

		WindowManager.LayoutParams params = _window.getAttributes();
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		_window.setAttributes(params);*/
		//setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		//TopTarSet.setWindowStatusBarColor(this,android.R.color.holo_red_dark);
	}

}

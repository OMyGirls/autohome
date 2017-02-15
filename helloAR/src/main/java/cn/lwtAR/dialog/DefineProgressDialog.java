package cn.lwtAR.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import cn.lwtAR.R;


public class DefineProgressDialog extends BaseDialog {

	public DefineProgressDialog(Context context) {
		super(context);
	}

	@Override
	public void initWindow() {
		setShowActionBar(false);
		setGravity(Gravity.CENTER);
		setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_dialog);
	}
}

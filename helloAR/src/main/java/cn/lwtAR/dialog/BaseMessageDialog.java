package cn.lwtAR.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * 消息弹出框基类
 * @创建者 王春龙
 */
public class BaseMessageDialog extends BaseDialog {

	public BaseMessageDialog(Context context) {
		super(context);
	}

	@Override
	public void initWindow() {
		setGravity(Gravity.CENTER);
		setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		// setWindowAnimations(R.style.DialogAnimation);
		setShowActionBar(false);
	}
}

package cn.lwtAR.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.arta.lib.util.CommonUtil;
import com.arta.lib.widget.datalodinglayout.DataLodingLayout;
import com.arta.lib.widget.datalodinglayout.OnDataLoadingListener;
import com.arta.lib.widget.datalodinglayout.ViewLoadType;

/**
 * 对话框基类
 * 
 * @author 王春龙
 * 
 */
public abstract class BaseDialog extends Dialog implements OnDataLoadingListener {

	private int INVALID_VALUE = -1;
	
	private int gravity = INVALID_VALUE;

	private int windowAnimations = INVALID_VALUE;

	private float width;
	private float height;

	private float actionBarContentViewHeightRatio = 0.8f;
	private float actionBarHeight = INVALID_VALUE;
	private boolean showActionBar = true;

	private Drawable actionBarBackground;
	private int actionBarBackgroundColor = android.R.color.transparent;
	private Drawable windowViewBackground;
	private int windowViewBackgroundColor = android.R.color.transparent;

	private FrameLayout actionBarViewGroup;
	private LinearLayout windowViewGroup;

	private View actionBarView;
	private ViewGroup contentView;

	private Activity activity;
	
	private DataLodingLayout dataLoadingLayout;
	
	private float dimAmount = 0.4f;

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		this.activity = (Activity) context;
		initWindow();
	}

	public BaseDialog(Context context) {
		super(context);
		this.activity = (Activity) context;
		initWindow();
	}

	/**
	 * 此方法在窗口对象构造参数里调用，可用来初始化Window窗口参数，宽度、高度、显示位置、风格等。
	 */
	public abstract void initWindow();
	
	public Activity getActivity() {

		return this.activity;
	}

	public void setActionBarContentViewHeightRatio(
			float actionBarContentViewHeightRatio) {
		this.actionBarContentViewHeightRatio = actionBarContentViewHeightRatio;
	}

	public void setShowActionBar(boolean showActionBar) {
		this.showActionBar = showActionBar;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setDimAmount(float dimAmount) {
		this.dimAmount = dimAmount;
	}

	public void setWindowAnimations(int windowAnimations) {
		this.windowAnimations = windowAnimations;
	}

	/**
	 * 
	 * @param width 可为WindowManager.LayoutParams.WRAP_CONTENT，WindowManager.LayoutParams.MATCH_PARENT;
	 * width <= 1 && width >= 0 时按屏幕尺寸比例，width > 1时按像素(px)尺寸
	 * 
	 * @param height 可为WindowManager.LayoutParams.WRAP_CONTENT，WindowManager.LayoutParams.MATCH_PARENT;
	 * height <= 1 && height >= 0 时按屏幕尺寸比例，height > 1时按像素(px)尺寸
	 */
	public void setSize(float width, float height){
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * 
	 * @param width 可为WindowManager.LayoutParams.WRAP_CONTENT，WindowManager.LayoutParams.MATCH_PARENT;
	 * width <= 1 && width >= 0 时按屏幕尺寸比例，width > 1时按像素(px)尺寸
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * 
	 * @param height 可为WindowManager.LayoutParams.WRAP_CONTENT，WindowManager.LayoutParams.MATCH_PARENT;
	 * height <= 1 && height >= 0 时按屏幕尺寸比例，height > 1时按像素(px)尺寸
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public void setActionBarBackground(int resId) {
		if (showActionBar) {
			actionBarBackground = getContext().getResources()
					.getDrawable(resId);
			setActionBarBackground(actionBarBackground);
		}
	}

	@SuppressWarnings("deprecation")
	public void setActionBarBackground(Drawable drawable) {
		if (showActionBar) {
			actionBarBackground = drawable;
			if (actionBarViewGroup != null) {
				actionBarViewGroup.setBackgroundDrawable(actionBarBackground);
			}
		}
	}

	public void setActionBarBackgroundColor(int actionBarBackgroundColor) {
		if (showActionBar) {
			this.actionBarBackgroundColor = actionBarBackgroundColor;
			if (actionBarViewGroup != null) {
				actionBarViewGroup.setBackgroundColor(actionBarBackgroundColor);
			}
		}
	}
	public void setContentViewBackground(int resId) {
		windowViewBackground = getContext().getResources()
				.getDrawable(resId);
		setContentViewBackground(windowViewBackground);
	}

	@SuppressWarnings("deprecation")
	public void setContentViewBackground(Drawable drawable) {
		windowViewBackground = drawable;
		if (windowViewGroup != null) {
			windowViewGroup.setBackgroundDrawable(windowViewBackground);
		}
	}

	public void setContentViewBackgroundColor(int windowViewBackgroundColor) {
		this.windowViewBackgroundColor = windowViewBackgroundColor;
		if (windowViewGroup != null) {
			windowViewGroup.setBackgroundColor(windowViewBackgroundColor);
		}
	}
	
	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater.from(getContext()).inflate(layoutResID, dataLoadingLayout.getContentlayout());
	}

	@Override
	public void setContentView(View view) {
		FrameLayout fl = dataLoadingLayout.getContentlayout();
		FrameLayout.LayoutParams fl_lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		fl.addView(view, fl_lp);
	}

	public void setActionBarView(int resId) {
		actionBarView = LayoutInflater.from(getContext()).inflate(resId, null);
		setActionBarView(actionBarView);
	}

	public void setActionBarView(View view) {
		actionBarView = view;
		if (showActionBar && actionBarView != null) {
			FrameLayout.LayoutParams fl_actionbar = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT);
			fl_actionbar.gravity = Gravity.CENTER;
			actionBarViewGroup.addView(actionBarView, fl_actionbar);
			fixActionBarView(actionBarView);
		}
	}
	
	public DataLodingLayout getDataLoadingLayout(){
		return this.dataLoadingLayout;
	}
	
	/**
	 * 加载视图类型
	 */
	public void showView(ViewLoadType viewLoadType){
		showView(viewLoadType, null);
	}

	/**
	 * 加载视图类型，设定提示信息
	 * @param viewLoadType
	 * @param msg
	 */
	public void showView(ViewLoadType viewLoadType, String msg){
		dataLoadingLayout.showView(viewLoadType, msg);
	}

	private void fixActionBarView(final View barView) {
		if (barView == null)
			return;
		if (barView instanceof ViewGroup) {
			int childCount = ((ViewGroup) barView).getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = ((ViewGroup) barView).getChildAt(i);
				fixActionBarView(child);
			}
		} else {
			if(barView instanceof ImageView) return;
			
			barView.post(new Runnable() {
				@Override
				public void run() {
					int childNewHeight = (int) (readActionBarHeight() * actionBarContentViewHeightRatio);
					if (barView.getLayoutParams() != null) {
						barView.getLayoutParams().height = childNewHeight;
						if (barView instanceof ImageView) {
							float r = (float) barView.getWidth()
									/ (float) barView.getHeight();
							barView.getLayoutParams().width = (int) (childNewHeight * r);
						}
					}
				}
			});
		}
	}

	public View findContentViewById(int id) {
		if (contentView != null) {
			return contentView.findViewById(id);
		}
		return null;
	}

	public View findActionBarViewById(int id) {
		if (actionBarView != null) {
			return actionBarView.findViewById(id);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (windowAnimations != INVALID_VALUE) {
			getWindow().setWindowAnimations(windowAnimations);
		}

		windowViewGroup = new LinearLayout(getContext());
		LayoutParams ll = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		windowViewGroup.setLayoutParams(ll);
		windowViewGroup.setOrientation(LinearLayout.VERTICAL);
		if(windowViewBackground != null){
			windowViewGroup.setBackgroundDrawable(windowViewBackground);
		}
		else{
			windowViewGroup.setBackgroundColor(getActivity().getResources().getColor(windowViewBackgroundColor));
		}

		if (showActionBar) {
			actionBarViewGroup = new FrameLayout(getContext());
			LayoutParams ll_actionBar = new LayoutParams(
					LayoutParams.MATCH_PARENT, (int) readActionBarHeight());
			actionBarViewGroup.setLayoutParams(ll_actionBar);
			if (actionBarBackground != null) {
				actionBarViewGroup.setBackgroundDrawable(actionBarBackground);
			} else{
				actionBarViewGroup.setBackgroundColor(getActivity().getResources().getColor(actionBarBackgroundColor));
			}

			windowViewGroup.addView(actionBarViewGroup);
		}

		dataLoadingLayout = new DataLodingLayout(getActivity());
		contentView = dataLoadingLayout.getContentlayout();
		dataLoadingLayout.setOnDataLoadingListener(this);

		LayoutParams ll_content = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		windowViewGroup.addView(dataLoadingLayout, ll_content);
		
		super.setContentView(windowViewGroup);
	}

	private float readActionBarHeight() {
		if (actionBarHeight != INVALID_VALUE)
			return actionBarHeight;

		TypedArray actionbarSizeTypedArray = getContext()
				.obtainStyledAttributes(
						new int[] { android.R.attr.actionBarSize });

		actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
		actionbarSizeTypedArray.recycle();

		return actionBarHeight;
	}

	private void initDialogSize() {
		Window dialogWindow = getWindow();
		
		if (gravity!= INVALID_VALUE) {
			dialogWindow.setGravity(gravity);
		}
		
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
		
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		if (width == WindowManager.LayoutParams.WRAP_CONTENT
				|| width == WindowManager.LayoutParams.MATCH_PARENT) {
			p.width = (int) width;
		} else if (width > 0 && width <= 1.0f) {
			int screenWidth = CommonUtil.getScreenWidth(getContext());
			p.width = (int) (screenWidth * width);
		} else {
			p.width = (int) width;
		}

		if (height == WindowManager.LayoutParams.WRAP_CONTENT
				|| height == WindowManager.LayoutParams.MATCH_PARENT) {
			p.height = (int) height;
		} else if (height > 0 && height <= 1.0f) {
			int screenHeight = CommonUtil.getScreenHeight(getContext());
			p.height = (int) (screenHeight * height);
		} else {
			p.height = (int) height;
		}
		p.dimAmount = dimAmount;

		dialogWindow.setAttributes(p);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	@Override
	public void show() {
		super.show();
		initDialogSize();
	}

	/**
	 * 根据资源id 获取View ，不用强制转换
	 * 
	 * @param id
	 *            资源id
	 * @return 返回id所指向的View
	 */

	@SuppressWarnings("unchecked")
	protected <A extends View> A getView(int id) {
		return (A) findViewById(id);
	}

	// getView findViewByIdFX

	/**
	 * 从本activity跳转到另一个activity
	 * 
	 * @param clazz
	 */
	protected void startActivity(Class<?> clazz) {
		Intent intent = new Intent();
		intent.setClass(activity, clazz);
		activity.startActivity(intent);
	}

	@Override
	public void onRetryLoad(DataLodingLayout dataLayout) {
		
	}

}

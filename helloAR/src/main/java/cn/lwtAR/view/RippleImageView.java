package cn.lwtAR.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.lwtAR.R;


public class RippleImageView extends RelativeLayout {

    private static final int SHOW_SPACING_TIME=3000;
    private static final int MSG_WAVE2_ANIMATION = 1;
    private static final int MSG_WAVE3_ANIMATION = 2;
    private static final int IMAMGEVIEW_SIZE = 50;
    private static final int SIZE =5 ;

    private  int show_spacing_time=SHOW_SPACING_TIME;
    private AnimationSet[] mAnimationSet=new AnimationSet[SIZE];
    private ImageView[] imgs=new ImageView[SIZE];
    private ImageView img_bg;
    private float imageViewWidth=IMAMGEVIEW_SIZE;
    private float imageViewHeigth=IMAMGEVIEW_SIZE;
    private Thread animThread;

    public RippleImageView(Context context) {
        super(context);
        initView(context);
    }

    public RippleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributeSet(context,attrs);
        initView(context);
    }

    /**
     * 获取xml属性
     * @param context
     * @param attrs
     */
    private void getAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custume_ripple_imageview);
/*
        show_spacing_time = typedArray.getInt(R.styleable.custume_ripple_imageview_show_spacing_time, SHOW_SPACING_TIME);
        imageViewWidth = typedArray.getDimension(R.styleable.custume_ripple_imageview_imageViewWidth, IMAMGEVIEW_SIZE);
        imageViewHeigth = typedArray.getDimension(R.styleable.custume_ripple_imageview_imageViewHeigth, IMAMGEVIEW_SIZE);*/
        show_spacing_time = typedArray.getInt(R.styleable.custume_ripple_imageview_show_spacing_time, SHOW_SPACING_TIME);
        imageViewWidth = typedArray.getDimension(R.styleable.custume_ripple_imageview_imageViewWidth, IMAMGEVIEW_SIZE);
        imageViewHeigth = typedArray.getDimension(R.styleable.custume_ripple_imageview_imageViewHeigth, IMAMGEVIEW_SIZE);
        Log.d("TAG","show_spacing_time="+show_spacing_time+"mm imageViewWidth="+imageViewWidth+"px  imageViewHeigth="+imageViewHeigth+"px");
        typedArray.recycle();
    }
    private void initView(Context context) {
        setLayout(context);
        for (int i = 0; i <imgs.length ; i++) {
            mAnimationSet[i]=initAnimationSet(i);
        }
    }

    /**
     * 开始动态布局
     */
    private void setLayout(Context context) {
        LayoutParams params=new LayoutParams(dip2px(context,imageViewWidth),dip2px(context,imageViewHeigth));
        //添加一个规则
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        /**添加水波纹图片*/
        for (int i = 0; i <SIZE ; i++) {
            imgs[i] = new ImageView(context);
            imgs[i].setImageResource(R.drawable.start_scan_wave1);
            addView(imgs[i],params);
        }
        LayoutParams params_bg=new LayoutParams(dip2px(context,imageViewWidth)+50,dip2px(context,imageViewHeigth)+50);
        //添加一个规则
        params_bg.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        /**添加背景图片*/
        img_bg=new ImageView(context);
        img_bg.setImageResource(R.drawable.tab_scan_normal);
        addView(img_bg,params_bg);
    }

    /**
     * 初始化动画集
     * @return
     */
    private AnimationSet initAnimationSet(int index) {
        AnimationSet as = new AnimationSet(true);
        //缩放度：变大两倍
        ScaleAnimation sa = new ScaleAnimation(1f, 3f, 1f, 3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(show_spacing_time);
        sa.setRepeatCount(Animation.INFINITE);// 设置循环
        //透明度
        AlphaAnimation aa = new AlphaAnimation(1, 0.1f);
        aa.setDuration(show_spacing_time);
        aa.setRepeatCount(Animation.INFINITE);//设置循环

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(show_spacing_time);
        rotateAnimation.setRepeatCount(Animation.INFINITE);//设置循环

        as.addAnimation(sa);
        as.addAnimation(aa);
        as.addAnimation(rotateAnimation);
//        as.setStartOffset(index * 600);
        return as;
    }
    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDetachedFromWindow() {
        run = false;
        super.onDetachedFromWindow();
    }

    int index = 0;
    boolean run = true;

    public void startWaveAnimation() {
//        for (int i = 0; i < imgs.length; i++) {
//            imgs[i].startAnimation(mAnimationSet[i]);
//        }

        animThread = new Thread(){
            @Override
            public void run() {
                while (run) {
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgs[index].startAnimation(mAnimationSet[index]);
                        }
                    });
                    index = (index + 1) % imgs.length;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        animThread.start();
    }

    /**
     * 停止水波纹动画
     */
    public void stopWaveAnimation() {
        for (int i = 0; i <imgs.length ; i++) {
            imgs[i].clearAnimation();
        }
    }
    /**获取播放的速度*/
    public int getShow_spacing_time() {
        return show_spacing_time;
    }
    /**设计播放的速度，默认是800毫秒*/
    public void setShow_spacing_time(int show_spacing_time) {
        this.show_spacing_time = show_spacing_time;
    }

}

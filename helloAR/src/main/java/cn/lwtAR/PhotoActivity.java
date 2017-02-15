package cn.lwtAR;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arta.lib.adapter.BaseAdapterEntityViewManage;
import com.arta.lib.adapter.BaseEntityPageAdapter;
import com.arta.lib.adapter.DefaultBaseAdapterEntityViewManage;
import com.arta.lib.adapter.wrapper.ViewPagerEntityWrapper;
import com.arta.lib.util.FileUtils;
import com.arta.lib.util.StringUtils;
import com.arta.lib.widget.listener.OnEntityViewPagerClickListener;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.ex.DbException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.lwtAR.database.DB;
import cn.lwtAR.entity.PhotoEntity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class PhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    ViewPager vpPhotos;
    ViewPagerEntityWrapper<PhotoEntity> entityViewPagerEntityWrapper;
    List<PhotoEntity> dataList;

    boolean shareDialog = false;
    ImageView ivLeft;
    TextView tvBack;
    LinearLayout llBack;
    TextView tvCenter;
    TextView tvRight;
    ImageView ivRight;
    private FrameLayout iv_share;

    private static final String APP_ID = "wxf79f4e08b0e3b678";
    private IWXAPI api;

    private void regToWx(){
        api  = WXAPIFactory.createWXAPI(this,APP_ID,true);
        api.registerApp(APP_ID);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            if (msg.what == 0) {
                List<PhotoEntity> listPictures = (List<PhotoEntity>) msg.obj;
//				Toast.makeText(getApplicationContext(), "handle"+listPictures.size(), 1000).show();
                entityViewPagerEntityWrapper.setAdapter(new BaseEntityPageAdapter<PhotoEntity>(PhotoActivity.this, listPictures, adapterEntityViewManage) {
                    @Override
                    public int getItemPosition(Object object) {
                        return POSITION_NONE;
                    }
                });
            }
        }

    };

    private void loadVaule(){
        dataList = new ArrayList<PhotoEntity>();
        File file = new File("/sdcard/Android/data/cn.lwtAR/files");
        File[] files  = null;
        files = file.listFiles();
        for (int i = files.length - 1; i >=0; i--) {
            if(files[i].getPath().endsWith(".mp4")) {
            PhotoEntity picture = new PhotoEntity();
            picture.setType("2");
            picture.setId(i);
            picture.setVideoPath(files[i].getPath());
            picture.setPicPath(files[i].getPath());
            dataList.add(picture);
            }
            if(files[i].getPath().endsWith(".png")) {
                PhotoEntity picture = new PhotoEntity();
                picture.setType("1");
                picture.setId(i);
                picture.setVideoPath(files[i].getPath());
                picture.setPicPath(files[i].getPath());
                dataList.add(picture);
            }
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = dataList;

        handler.sendMessage(msg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();
        regToWx();
    }

    private void init() {
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        tvRight = (TextView) findViewById(R.id.tvRight);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        vpPhotos = (ViewPager) findViewById(R.id.vp_photos);
        iv_share = (FrameLayout) findViewById(R.id.iv_share);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        //setActionBarBackgroundDrawable(R.drawable.actionbar_unity_bg);
        dataList = new ArrayList<PhotoEntity>();
        entityViewPagerEntityWrapper = new ViewPagerEntityWrapper<PhotoEntity>(vpPhotos);
        entityViewPagerEntityWrapper.setAdapter(new BaseEntityPageAdapter<PhotoEntity>(this, dataList, adapterEntityViewManage) {
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        });
//        entityViewPagerEntityWrapper.setOnEntityViewPagerClickListener(onEntityViewPagerClickListener);

//        ivRight.setImageResource(R.drawable.delete);
//        ivRight.setOnClickListener(this);

        vpPhotos.addOnPageChangeListener(this);
        loadVaule();
/*        try {
            List<PhotoEntity> all = DB.getInstance().getDBTool().findAll(PhotoEntity.class);
            if (all != null) {
                dataList.addAll(all);
                vpPhotos.getAdapter().notifyDataSetChanged();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }*/

        if (getIntent() != null && StringUtils.isEquals(getIntent().getStringExtra("a"), "a")) {
            vpPhotos.post(new Runnable() {
                @Override
                public void run() {
                    if (vpPhotos.getAdapter().getCount() > 0) {
                        vpPhotos.setCurrentItem(vpPhotos.getAdapter().getCount() - 1);
                        showPageInfo(vpPhotos.getAdapter().getCount() - 1);
                    } else {
                        showPageInfo(0);
                    }
                }
            });
        } else {
            showPageInfo(0);
        }

        ivRight.setVisibility(View.GONE);
        tvCenter.setVisibility(View.GONE);
    }

    OnEntityViewPagerClickListener<PhotoEntity> onEntityViewPagerClickListener = new OnEntityViewPagerClickListener<PhotoEntity>() {
        @Override
        public void onEntityViewClick(ViewPager viewPager, View view, PhotoEntity photoEntity, int i) {
            if (StringUtils.isEquals(photoEntity.getType(), PhotoEntity.TYPE_VIDEO)) {
                playVideo(photoEntity);
            }
        }
    };

    private void showShare() {
   /*     WXTextObject textObj = new WXTextObject();
        textObj.text = "ceshi";

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = "ceshiaa";

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = 0;//0好友，1朋友圈
        //req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);*/

       ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(dataList.get(entityViewPagerEntityWrapper.getViewPager().getCurrentItem()).getPicPath());//确保SDcard下面存在此张图片
        //oks.setImagePath("/sdcard/Android/data/cn.lwtAR/files/image.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        oks.setDialogMode();
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);/**/

      /*  ShareSDK.initSDK(Context,"你的应用在Sharesdk注册时返回的AppKey");
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Id","1");
        hashMap.put("SortId","1");
        hashMap.put("AppKey","568898243");
        hashMap.put("AppSecret","38a4f8204cc784f81f9f0daaf31e02e3");
        hashMap.put("RedirectUrl","http://www.sharesdk.cn");
        hashMap.put("ShareByAppClient","true");
        hashMap.put("Enable","true");
        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME,hashMap);*/
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    private void playVideo(PhotoEntity photoEntity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/mp4";
        Uri uri = Uri.parse("file:///" + photoEntity.getVideoPath());
        intent.setDataAndType(uri, type);
        startActivity(intent);
    }

    //获取视频的第一帧
    private Bitmap getVideoThumbnail(String videoPath) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);

        bitmap = media.getFrameAtTime();
       /* bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
//		        System.out.println("w"+bitmap.getWidth());
//		        System.out.println("h"+bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);*/
        return bitmap;
    }


    BaseAdapterEntityViewManage<PhotoEntity> adapterEntityViewManage = new DefaultBaseAdapterEntityViewManage<PhotoEntity>(R.layout.item_photo) {
        @Override
        public void updateItemView(Context context, View view, final PhotoEntity photoEntity, int i) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_photos);
            View viewPlay = view.findViewById(R.id.iv_play);
            if (StringUtils.isEquals(photoEntity.getType(), PhotoEntity.TYPE_VIDEO)) {
                viewPlay.setVisibility(View.VISIBLE);
                viewPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playVideo(photoEntity);
                    }
                });
                imageView.setImageBitmap(getVideoThumbnail(photoEntity.getPicPath()));
            } else {
                viewPlay.setVisibility(View.GONE);
                Bitmap bm = BitmapFactory.decodeFile(photoEntity.getPicPath());
                //将图片显示到ImageView中
                imageView.setImageBitmap(bm);
                //ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(photoEntity.getPicPath()), imageView);
            }

            //
        }
    };
/*
    @OnClick({R.id.iv_share, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                share();
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.ivRight:
                delete();
                break;
        }
    }*/

/*
    private void share() {
        if (vpPhotos.getAdapter().getCount() == 0) return;
        if (shareDialog) {
            ShareDialog dialog = new ShareDialog(this, dataList.get(vpPhotos.getCurrentItem()));
            dialog.show();
        } else {
            Intent intent = new Intent(this, ShareActivity.class);
            intent.putExtra("a", dataList.get(vpPhotos.getCurrentItem()));
            startActivity(intent);
            overridePendingTransition(R.anim.dialog_anim_from_bottom_enter, R.anim.dialog_anim_from_bottom_exit);
        }
    }
*/

    private void delete() {
        if (vpPhotos.getAdapter().getCount() == 0) {
            return;
        }
        PhotoEntity photoEntity = dataList.get(vpPhotos.getCurrentItem());
        FileUtils.deleteFile(photoEntity.getPicPath());
        FileUtils.deleteFile(photoEntity.getVideoPath());
        try {
            DB.getInstance().getDBTool().delete(photoEntity);
            dataList.remove(photoEntity);
            vpPhotos.getAdapter().notifyDataSetChanged();
            showPageInfo(vpPhotos.getCurrentItem());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        showPageInfo(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showPageInfo(int arg0) {
        if (vpPhotos.getAdapter().getCount() == 0) {
            setTitle("0/0");
        } else {
            setTitle((arg0 + 1) + "/" + vpPhotos.getAdapter().getCount());
        }
    }

    private void setTitle(String title){
        if(tvCenter != null){
            tvCenter.setText(title);
        }
    }
}

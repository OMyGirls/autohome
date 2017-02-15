package cn.lwtAR.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import cn.lwtAR.view.layer.BaseLayer;
import cn.lwtAR.view.layer.ImageLayer;
import cn.lwtAR.view.layer.TextLayer;

/**
 * Created by Administrator on 2016/11/17.
 */

public class CameraView extends View implements Camera.PreviewCallback
{
    // 源视频帧宽/高
    private int srcFrameWidth  = 640;
    private int srcFrameHeight = 480;
    private int frameSize = srcFrameWidth * srcFrameHeight;
    private int qtrFrameSize = srcFrameWidth * srcFrameHeight >> 2;

    // 帧预览贴图
    private Bitmap previewBmp = null;
    private Rect previewRect = null;
    private Camera camera = null;
    // 图层
    private BaseLayer[] layers = null;

    // 数据采集
    private int[] rgb_data = null;
    private byte[] yuvdata = null;

    // 摄像头前置/后置
    public static final int CAMERA_BACK  = 0;
    public static final int CAMERA_FRONT = 1;
    private int curCameraIndex = CAMERA_BACK;

    public CameraView(Context _context)
    {
        super(_context);
    }

    public CameraView(Context _context, AttributeSet _attrs)
    {
        super(_context, _attrs);
    }

    public CameraView(Context context, int previewWidth, int previewHeight, int cameraIndex)
    {
        super(context);

        curCameraIndex = cameraIndex;
        rgb_data = new int[frameSize];
        yuvdata = new byte[frameSize * 3 / 2];

        previewBmp = Bitmap.createBitmap(srcFrameHeight, srcFrameWidth, Bitmap.Config.ARGB_8888);
        previewRect = new Rect(0, 0, previewWidth, previewHeight);

        // 定义图层
        layers = new BaseLayer[2];
        layers[0]  = new TextLayer(context, 0, false);
        layers[1] = new ImageLayer(context, 1, false);

        // 文字
        ((TextLayer)layers[0]).setFontParams(32, Color.CYAN);
        ((TextLayer)layers[0]).setTextPos(100, 300);
        ((TextLayer)layers[0]).setContent("");
        layers[0].setVisible(true);

        // 图像
        ((ImageLayer)layers[1]).setImagePos(100, 150);
        layers[1].setVisible(false);

        // 初始化并打开摄像头
        startCamera(cameraIndex);

        this.setBackgroundColor(Color.parseColor("#82858b"));
    }

    // 根据索引初始化摄像头
    public void startCamera(int cameraIndex)
    {
        // 先停止摄像头
        stopCamera();
        try {
            // 再初始化并打开摄像头
            if (camera == null)
            {
                camera = Camera.open(cameraIndex);
                Camera.Parameters params = camera.getParameters();
                params.setPreviewSize(srcFrameWidth, srcFrameHeight);
                params.setPreviewFormat(ImageFormat.NV21);
                camera.setParameters(params);
                camera.setPreviewCallback(this);
                camera.startPreview();
            }
        }catch (RuntimeException r){
            Toast.makeText(getContext(),"打开摄像头失败！",Toast.LENGTH_SHORT).show();
        }


    }

    // 停止并释放摄像头
    public void stopCamera()
    {
        if (camera != null)
        {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 填充数据(因为数据已经旋转过,此时宽与高需要互换)
        previewBmp.setPixels(rgb_data, 0, srcFrameHeight, 0, 0, srcFrameHeight, srcFrameWidth);

        // 绘制图层
        for (BaseLayer layer : layers)
        {
            if (layer.isVisible())
            {
                layer.drawLayer(previewBmp);
            }
        }

        // 贴图
        canvas.drawBitmap(previewBmp, null, previewRect, null);
    }

    // 获取摄像头视频数据
    @Override
    public void onPreviewFrame(byte[] data, Camera camera)
    {
        int i = 0, j = 0, k = 0;
        int uvHeight = srcFrameHeight >> 1;

        // 旋转yuv数据
        if (curCameraIndex == CAMERA_BACK)
        {
            // 旋转y
            for (i = 0; i < srcFrameWidth; i++)
            {
                for (j = srcFrameHeight - 1; j >= 0; j--)
                {
                    yuvdata[k] = data[srcFrameWidth * j + i];
                    k++;
                }
            }

            // 旋转uv
            for (i = 0; i < srcFrameWidth; i += 2)
            {
                for (j = uvHeight - 1; j >= 0; j--)
                {
                    yuvdata[k] = data[frameSize + srcFrameWidth * j + i + 1];// cb/u
                    yuvdata[k + qtrFrameSize] = data[frameSize + srcFrameWidth * j + i];// cr/v
                    k++;
                }
            }
        }
        else
        {
            // 旋转y
            for (i = srcFrameWidth - 1; i >= 0; i--)
            {
                for (j = srcFrameHeight - 1; j >= 0; j--)
                {
                    yuvdata[k] = data[srcFrameWidth * j + i];
                    k++;
                }
            }

            // 旋转uv
            for (i = srcFrameWidth - 2; i >= 0; i -= 2)
            {
                for (j = uvHeight - 1; j >= 0; j--)
                {
                    yuvdata[k] = data[frameSize + srcFrameWidth * j + i + 1];// cb/u
                    yuvdata[k + qtrFrameSize] = data[frameSize + srcFrameWidth * j + i];// cr/v
                    k++;
                }
            }
        }

        // yuv转rgb(因为数据已经旋转过,此时宽与高需要互换)
        int yp = 0;
        for (i = 0, yp = 0; i < srcFrameWidth; i++)
        {
            int uvp = frameSize + (i >> 1) * uvHeight, u = 0, v = 0;
            for (j = 0; j < srcFrameHeight; j++, yp++)
            {
                int y = (0xff & yuvdata[yp]) - 16;
                if ((j & 1) == 0)
                {
                    u = (0xff & yuvdata[uvp + (j>>1)]) - 128;
                    v = (0xff & yuvdata[uvp + qtrFrameSize + (j>>1)]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;

                rgb_data[i*srcFrameHeight + j] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
            }// for
        }// for

        invalidate();
    }
}
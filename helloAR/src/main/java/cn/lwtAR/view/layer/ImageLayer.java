package cn.lwtAR.view.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import cn.lwtAR.R;


// 图片图层
public class ImageLayer extends BaseLayer
{
	private int resId = 0;
	private float posX = 0.0f, posY = 0.0f;
	Bitmap bmp;
	
	public ImageLayer(Context context, int _layerId, boolean _isVisible)
	{
		super(context, _layerId, _isVisible);
		
		resId = R.drawable.app_icon;
		bmp = BitmapFactory.decodeResource(context.getResources(), resId);
	}
	
	@Override
	public void drawLayer(Bitmap bitmap)
	{
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bmp, posX, posY, null);
	}
	
	// 设置图片位置
	public void setImagePos(float _posX, float _posY)
	{
		posX = _posX;
		posY = _posY;
	}
	
	// 设置图片资源id
	public void setResourcesId(int _resId)
	{
		resId = _resId;
	}
}

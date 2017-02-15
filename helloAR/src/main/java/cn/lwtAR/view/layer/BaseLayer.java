package cn.lwtAR.view.layer;

import android.content.Context;
import android.graphics.Bitmap;

// 图层基类
public abstract class BaseLayer
{
	// 图层id/图层是否可见
	private int layerId = 0;
	private boolean isVisible = false;
	// 绘制图层
	public abstract void drawLayer(Bitmap bitmap);
	
	public BaseLayer(Context context, int _layerId, boolean _isVisible)
	{
		layerId = _layerId;
		isVisible = _isVisible;
	}

	public int getLayerId()
	{
		return layerId;
	}

	public void setLayerId(int layerId)
	{
		this.layerId = layerId;
	}

	public boolean isVisible()
	{
		return isVisible;
	}

	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}
}

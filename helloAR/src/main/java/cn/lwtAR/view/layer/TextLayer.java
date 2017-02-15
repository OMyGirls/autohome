package cn.lwtAR.view.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

// 文字图层
public class TextLayer extends BaseLayer
{
	// 文字位置/内容
	private float posX = 0.0f, posY = 0.0f;
	private String content = "";
	// 文字大小/颜色
	private int size = 24;
	private int color = Color.WHITE;
	
	// 文字画笔
	private Paint textPaint = null;
	
	public TextLayer(Context context, int _layerId, boolean _isVisible)
	{
		super(context, _layerId, _isVisible);
		
		textPaint = new Paint();
		textPaint.setColor(color);
		textPaint.setTextSize(size);
		textPaint.setAntiAlias(true);
		textPaint.setTypeface(Typeface.MONOSPACE);
	}
	
	@Override
	public void drawLayer(Bitmap bitmap)
	{
		Canvas canvas = new Canvas(bitmap);
		canvas.drawText(content, posX, posY, textPaint);
	}
	
	// 设置文字位置
	public void setTextPos(float _posX, float _posY)
	{
		posX = _posX;
		posY = _posY;
	}
	
	// 设置文字内容
	public void setContent(String _content)
	{
		content = _content;
	}
	
	// 设置字体参数
	public void setFontParams(int _textSize, int _textColor)
	{
		size = _textSize;
		color = _textColor;
		
		// 更新画笔
		textPaint.setColor(color);
		textPaint.setTextSize(size);
	}
}

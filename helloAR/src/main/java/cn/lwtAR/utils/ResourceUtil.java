package cn.lwtAR.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import com.arta.lib.util.FileUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 资源获取工具类
 * 
 * @author 王春龙
 * 
 */
public class ResourceUtil {

	/**
	 * 获取assert里面的文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			return FileUtils.readFile(inputStream, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getHtmlFromAssets(Context context, String fileName) {
		try {
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			String string = FileUtils.readFile(inputStream, "utf-8");
			string.replaceAll(" ", "&nbsp;&nbsp;");
			String[] strs = string.split("\n");
			StringBuffer sb = new StringBuffer();
			for(int i =0; i < strs.length; i++){
				sb
						.append(i != 0 ? "<br/>" : "")
						.append(strs[i])
						.append(i == 0 ? "\n" : "");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String txtToHtml(String s) {
		StringBuilder builder = new StringBuilder();
		boolean previousWasASpace = false;
		for (char c : s.toCharArray()) {
			if (c == ' ') {
				if (previousWasASpace) {
					builder.append(" ");
					previousWasASpace = false;
					continue;
				}
				previousWasASpace = true;
			} else {
				previousWasASpace = false;
			}
			switch (c) {
				case '<':
					builder.append("<");
					break;
				case '>':
					builder.append(">");
					break;
				case '&':
					builder.append("&");
					break;
				case '"':
					builder.append("\"");
					break;
				case '\n':
					builder.append("<br>");
					break;
				// We need Tab support here, because we print StackTraces as HTML
				case '\t':
					builder.append("     ");
					break;
				default:
					builder.append(c);

			}
		}
		String converted = builder.toString();
		String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
		Pattern patt = Pattern.compile(str);
		Matcher matcher = patt.matcher(converted);
		converted = matcher.replaceAll("<a href=\"$1\">$1</a>");
		return converted;
	}
	/**
	 * 通过文件的路径来返回Bitmap对象
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static Bitmap getBitmapFromFile(String path) {
		if (null == path || "".equals(path)) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		return bitmap;
	}


	
	/**
	 * 读取外存图片，设定最大尺寸，最大尺寸包括图片最大宽度和最大高度。
	 * @param context
	 * @param path
	 * @param maxSize
	 * @return
	 */
	public static Bitmap getBitmapFromFile(Context context, String path, int maxSize){
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		
		int sampleSize = 1;
		if(opts.outWidth >= opts.outHeight && opts.outWidth >= maxSize){
			sampleSize = (int) (opts.outWidth / (float)maxSize);
		}
		else if(opts.outHeight >= maxSize){
			sampleSize = (int) (opts.outHeight / (float)maxSize);
		}
		
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = sampleSize;
		
		bitmap = BitmapFactory.decodeFile(path, opts);
		
		return bitmap;
	}
	
	/**
	 * 缩放图片最大宽度小于maxSize
	 * @param bitmap
	 * @param maxSize
	 * @return
	 */
	public static Bitmap fixBitmap(Bitmap bitmap, int maxSize){
		Matrix matrix = new Matrix();
		float scaleSize = 0;
		if(bitmap.getWidth() > bitmap.getHeight() && bitmap.getWidth() > maxSize){
			scaleSize = (float)maxSize / (float)bitmap.getWidth();
		}
		else if(bitmap.getHeight() > maxSize){
			scaleSize = (float)maxSize / (float)bitmap.getHeight();
		}
		matrix.postScale(scaleSize, scaleSize);
		
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		return bitmap;
	}
	
	/**
     * 解析Bitmap
     * @param bitmap
     * @return
     */
    public static byte[] toRawData(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getWidth()
                * bitmap.getHeight() * 4);
        bitmap.copyPixelsToBuffer(buffer);
        byte[] data = buffer.array();
        buffer.clear();
        return data;
    }
}

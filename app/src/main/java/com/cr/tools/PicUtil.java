package com.cr.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.cr.service.FileUtil.DirType;

public class PicUtil {
	private static final String TAG = "PicUtil";

	/**
	 * 根据一个网络连接(URL)获取bitmapDrawable图像
	 * 
	 * @param imageUri
	 * @return
	 */
	public static BitmapDrawable getfriendicon(URL imageUri) {

		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imageUri
					.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 * 根据一个网络连接(String)获取bitmapDrawable图像
	 * 
	 * @param imageUri
	 * @return
	 */
	public static BitmapDrawable getcontentPic(String imageUri) {
		URL imgUrl = null;
		try {
			imgUrl = new URL(imageUri);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imgUrl.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 * 根据一个网络连接(URL)获取bitmap图像
	 * 
	 * @param imageUri
	 * @return
	 */
	public static Bitmap getusericon(URL imageUri) {
		// 显示网络上的图片
		URL myFileUrl = imageUri;
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 根据一个网络连接(String)获取bitmap图像
	 * 
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmap(String imageUri) {
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			// Log.i(TAG, "image download finished." + imageUri);
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "URl地址异常");
			return null;
		}
		return bitmap;
	}

	/**
	 * 下载图片 同时写到本地缓存文件中
	 * 
	 * @param context
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmapAndwrite(String imageUri) {
		Bitmap bitmap = null;
		try {
			// 显示网络上的图片
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();

			InputStream is = conn.getInputStream();
			File cacheFile = FileUtil.getCacheFile(imageUri);
			BufferedOutputStream bos = null;
			bos = new BufferedOutputStream(new FileOutputStream(cacheFile));
			// Log.i(TAG, "write file to " + cacheFile.getCanonicalPath());

			byte[] buf = new byte[1024];
			int len = 0;
			// 将网络上的图片存储到本地
			while ((len = is.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}

			is.close();
			bos.close();

			// 从本地加载图片
			bitmap = BitmapFactory.decodeFile(cacheFile.getCanonicalPath());
			// String name = MD5Util.MD5(imageUri);

		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "URl地址异常");
		}
		return bitmap;
	}

	// public static boolean downpic(String picName, Bitmap bitmap) {
	// boolean nowbol = false;
	// try {
	// File saveFile = new File("/mnt/sdcard/download/weibopic/" + picName
	// + ".png");
	// if (!saveFile.exists()) {
	// saveFile.createNewFile();
	// }
	// FileOutputStream saveFileOutputStream;
	// saveFileOutputStream = new FileOutputStream(saveFile);
	// nowbol = bitmap.compress(Bitmap.CompressFormat.PNG, 100,
	// saveFileOutputStream);
	// saveFileOutputStream.close();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return nowbol;
	// }

	public static void writeTofiles(Context context, Bitmap bitmap,
			String filename) {
		BufferedOutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(context.openFileOutput(
					filename, Context.MODE_PRIVATE));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件写入缓存系统中
	 * 
	 * @param filename
	 * @param is
	 * @return
	 */
	public static String writefile(Context context, String filename,
			InputStream is) {
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(is);
			outputStream = new BufferedOutputStream(context.openFileOutput(
					filename, Context.MODE_PRIVATE));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return context.getFilesDir() + "/" + filename + ".jpg";
	}

	// 放大缩小图片
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	/**
	 * 适应屏幕宽度等比例缩放图片
	 */
	// 等比例缩放
	public static Bitmap adaptive(Bitmap bitmap, Activity activity) {
		if (bitmap == null) {
			return null;
		}
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int scalX = metric.widthPixels; // 屏幕宽度（像素）
		int scalY = metric.heightPixels; // 屏幕高度（像素）

		Matrix matrix = new Matrix();
		int width = bitmap.getWidth();// 获取资源位图的宽
		int height = bitmap.getHeight();// 获取资源位图的高
		float w = scalX / bitmap.getWidth();
		float h = scalY / bitmap.getHeight();
		double s = (double) (Math.round(scalX * 100 / width) / 100.0);
		matrix.postScale(w, h);// 获取缩放比例
		// 根据缩放比例获取新的位图
		Bitmap newbmp = Bitmap.createScaledBitmap(bitmap, scalX,
				(int) (height * s), true);
		return newbmp;
	}

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	// 获得圆角图片的方法
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null) {
			return null;
		}

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	// 获得带倒影的图片方法
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 将用户拍照的拍照信息保存成图片（jpg）
	 * 
	 * @param data
	 *            {@link Intent}
	 * @return 图片路径
	 */
	public static String savePz(Intent data) {
		Bundle bundle = data.getExtras();
		Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
		FileOutputStream b = null;
		String name = String.valueOf(System.currentTimeMillis()).concat(".jpg");
		File f = new File(com.cr.service.FileUtil.getRFileSaveDir(DirType.ZFWS,
				name));
		try {
			b = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "", e);
		} finally {
			if (!bitmap.isRecycled()) {
				bitmap.recycle();
			}
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				Log.e(TAG, "", e);
			}
		}
		return null;
	}

	/**
	 * 加载本地图片 http://bbs.3gstdy.com
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 图片的压缩（大小压缩）
	 * @param image
	 * @return
	 */
	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
		return bitmap;
	}
	/**
	 * 压缩图片（压缩质量）
	 * @param image
	 * @return
	 */
	public static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//		return bitmap;
		return baos.toByteArray();
	}
}
package com.justing.poem;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	/**
	 * 启动跳转Activity
	 * 
	 * @param activity
	 * @param cls
	 */

	protected void startActivity(Activity activity,
			Class<? extends Activity> cls) {
		Intent intent = new Intent(activity, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/**
	 * 启动跳转Activity
	 * 
	 * @param action
	 * @param uri
	 */
	protected void startActivity(String action, String uri) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setData(Uri.parse(uri));
		startActivity(intent);
	}

	/**
	 * 启动跳转到另一应用程序的Activity
	 * 
	 * @param packageName
	 *            另一个应用程序的包名
	 * @param clasActivityName
	 *            要启动另一个应用程序中的Activity
	 */
	protected void startOtherAppActivityForResult(String packageName,
			String clasActivityName, int requestCode) {
		ComponentName component = new ComponentName(packageName,
				clasActivityName);
		Intent intent = new Intent();
		intent.setComponent(component);
		// 添加这个flag表示启动的Activity放到新创建的Task中,不加这则放在要启动应用默认任务栈中
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, requestCode);
	}

	// 初始化数据
	protected abstract void initView();

	// 初始化事件
	protected abstract void initEnvent();

	// 初始化数据
	protected abstract void initData();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onResume(this);

	}

    /**
     * 使用BitmapFactory.decodeStream 方式解析图片，减少内存消耗
     *  注意： 
	 *	decodeStream是直接读取图片资料的字节码了， 
	 *	不会根据机器的各种分辨率来自动适应，使用了decodeStream之后，
	 *	需要在hdpi和mdpi，ldpi中配置相应的图片资源，
	 *	否则在不同分辨率机器上都是同样大小（像素点数量），显示出来的大小就不对了。
     * @param resId
     * @return
     */
	protected BitmapDrawable scaleImageResoure(int resId) {
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = getResources().openRawResource(resId);
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			is = null;
		}

		return new BitmapDrawable(getResources(), bitmap);
	}

}

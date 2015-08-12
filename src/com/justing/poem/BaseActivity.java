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
	 * ������תActivity
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
	 * ������תActivity
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
	 * ������ת����һӦ�ó����Activity
	 * 
	 * @param packageName
	 *            ��һ��Ӧ�ó���İ���
	 * @param clasActivityName
	 *            Ҫ������һ��Ӧ�ó����е�Activity
	 */
	protected void startOtherAppActivityForResult(String packageName,
			String clasActivityName, int requestCode) {
		ComponentName component = new ComponentName(packageName,
				clasActivityName);
		Intent intent = new Intent();
		intent.setComponent(component);
		// ������flag��ʾ������Activity�ŵ��´�����Task��,�����������Ҫ����Ӧ��Ĭ������ջ��
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, requestCode);
	}

	// ��ʼ������
	protected abstract void initView();

	// ��ʼ���¼�
	protected abstract void initEnvent();

	// ��ʼ������
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
     * ʹ��BitmapFactory.decodeStream ��ʽ����ͼƬ�������ڴ�����
     *  ע�⣺ 
	 *	decodeStream��ֱ�Ӷ�ȡͼƬ���ϵ��ֽ����ˣ� 
	 *	������ݻ����ĸ��ֱַ������Զ���Ӧ��ʹ����decodeStream֮��
	 *	��Ҫ��hdpi��mdpi��ldpi��������Ӧ��ͼƬ��Դ��
	 *	�����ڲ�ͬ�ֱ��ʻ����϶���ͬ����С�����ص�����������ʾ�����Ĵ�С�Ͳ����ˡ�
     * @param resId
     * @return
     */
	protected BitmapDrawable scaleImageResoure(int resId) {
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
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

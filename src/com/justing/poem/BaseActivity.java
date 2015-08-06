package com.justing.poem;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View.OnClickListener;

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
}

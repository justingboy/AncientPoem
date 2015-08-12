package com.justing.poem;

import org.apache.dd.aa.myl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class SpliashActivity extends BaseActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		layout.setBackgroundDrawable(scaleImageResoure(R.drawable.welcome));
		setContentView(layout);
		myl.init(this);
		isFirstLogin();
		creatDeskShortCut();
		

	}

	/**
	 * 创建桌面快捷方式
	 */
	public void creatDeskShortCut() {
		Intent shortcutIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 设置不可重复创建桌面快捷方式
		shortcutIntent.putExtra("duplicate", false);
		// 这个就是应用程序图标下面的名称
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		// 快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.title);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		Intent intent = new Intent(getApplicationContext(),
				SpliashActivity.class); // 这个SpliashActivity是调用此方法的Activity
		// 下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		// 点击快捷图片，运行的程序主入口
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 最后一步就是发送广播
		sendBroadcast(shortcutIntent);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEnvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 判断是不是第一次登陆
	 */
	private void isFirstLogin() {

		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(SpliashActivity.this, MainActivity.class);
				finish();
			}
		}, 2500);

	}
	
	
	

}

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
	 * ���������ݷ�ʽ
	 */
	public void creatDeskShortCut() {
		Intent shortcutIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// ���ò����ظ����������ݷ�ʽ
		shortcutIntent.putExtra("duplicate", false);
		// �������Ӧ�ó���ͼ�����������
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		// ���ͼƬ
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.title);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		Intent intent = new Intent(getApplicationContext(),
				SpliashActivity.class); // ���SpliashActivity�ǵ��ô˷�����Activity
		// ��������������Ϊ�˵�Ӧ�ó���ж��ʱ���� �ϵĿ�ݷ�ʽ��ɾ��
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		// ������ͼƬ�����еĳ��������
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// ���һ�����Ƿ��͹㲥
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
	 * �ж��ǲ��ǵ�һ�ε�½
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

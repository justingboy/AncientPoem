package com.justing.poem;

import com.justing.poem.utils.CrashHandler;

import android.app.Application;

public class PoemApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//�Ѽ�����
		CrashHandler.getInstance().init(this);
		
	}

}

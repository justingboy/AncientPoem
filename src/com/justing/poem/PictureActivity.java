package com.justing.poem;

import org.apache.dd.aa.myl;

import android.os.Bundle;
import android.view.View;

import com.justing.xing.XMan;

public class PictureActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		myl.init(this);
		XMan.init(this, "8ddc42af572fcbc1f74a4aee12b032d0", "gfen", 1).show(this);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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

}

package com.justing.poem.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.justing.poem.R;
import com.justing.poem.bean.Poem;
import com.justing.poem.dao.PoemBusiness;
import com.justing.poem.dao.PoemDao;
import com.justing.poem.utils.ToastUtil;
import com.umeng.update.UmengUpdateAgent;

public class PoemFragment extends Fragment implements OnClickListener{

	private Activity mActivity = null;
	private ArrayList<String> pm_list;

	private OnTitleChangeListener mListener = null;	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try {
			 mListener = (OnTitleChangeListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement OnHeadlineSelectedListener");
	        }
		mActivity = activity;
		getAppListInSystem();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return findFragmentView(inflater);
	}

	/**
	 * 找到布局及控件
	 * 
	 * @param inflater
	 * @return
	 */
	private View findFragmentView(LayoutInflater inflater) {

		View view = inflater.inflate(R.layout.menu_item, null);
		ImageView title_left = (ImageView) view.findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.icon_bookcover_shelf_f);
		
		view.findViewById(R.id.btn_qq).setOnClickListener(this);
		view.findViewById(R.id.btn_weibo).setOnClickListener(this);
		view.findViewById(R.id.btn_weixin).setOnClickListener(this);
		view.findViewById(R.id.btn_renre).setOnClickListener(this);
		view.findViewById(R.id.btn_baidu).setOnClickListener(this);
		view.findViewById(R.id.btn_collection).setOnClickListener(this);
		view.findViewById(R.id.btn_updataApp).setOnClickListener(this);
		
		return view;
	}

	/**
	 * 
	 * 点击事件
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_qq:

			openOtherAppActivity("QQ", "com.tencent.mobileqq",
					"activity.SplashActivity");
			break;
		case R.id.btn_renre:

			openOtherAppActivity("人人", "com.renren.mobile.android",
					"ui.WelcomeScreen");

			break;
		case R.id.btn_weixin:
			openOtherAppActivity("微信", "com.tencent.mm", "ui.LauncherUI");

			break;
		case R.id.btn_weibo:
			openOtherAppActivity("新浪微博", "com.sina.weibo", "SplashActivity");
			break;
		case R.id.btn_baidu:
			ToastUtil.showShort(mActivity, "正在进入百度");
			startActivity(Intent.ACTION_VIEW, "http://www.baidu.com");
			break;
		case R.id.btn_collection:
			new PoemAsyncTask().execute();
			break;
		case R.id.btn_updataApp:
			UmengUpdateAgent.update(mActivity);
			break;

		}
	}

	/**
	 * 跳转到其他应用的Activity
	 */
	private void openOtherAppActivity(String appName, String packageName,
			String firstActivityName) {
		if (pm_list.contains(packageName)) {
			ToastUtil.showShort(mActivity, "正在进入" + appName);
			Intent inten_weibo = new Intent();
			inten_weibo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			inten_weibo.setComponent(new ComponentName(packageName, packageName
					+ "." + firstActivityName));
			startActivity(inten_weibo);
		} else {
			ToastUtil.showShort(mActivity, "你没有安装" + appName + "," + "安装后重试 !");
		}

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
	 * 执行数据库查询操作
	 * 
	 * @author justing
	 * 
	 */
	private class PoemAsyncTask extends AsyncTask<Void, Void, List<Poem>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mListener.onChangeText("我的收藏");

		}

		@Override
		protected List<Poem> doInBackground(Void... params) {
			// TODO Auto-generated method stub

			return PoemBusiness.getInstances().queryAllData(new PoemDao(mActivity));
		}

		@Override
		protected void onPostExecute(List<Poem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mListener.onShowCollectPoem(result);
		}

	}

	/**
	 * 获得手机中所有安装的应用程序包名
	 */
	private void getAppListInSystem() {
		pm_list = new ArrayList<String>();
		PackageManager pm = mActivity.getPackageManager();
		List<PackageInfo> package_list = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		if (null != package_list && package_list.size() > 0) {
			for (PackageInfo packageInfo : package_list) {
				pm_list.add(packageInfo.packageName);
			}

		}
	}

	public interface OnTitleChangeListener {
		public void onChangeText(String title);
		public void onShowCollectPoem(List<Poem> list);
	}
}

package com.justing.poem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.justing.poem.adapter.PoemAdapter;
import com.justing.poem.bean.Poem;
import com.justing.poem.dao.PoemBusiness;
import com.justing.poem.dao.PoemDao;
import com.justing.poem.utils.ParseXmlPome;
import com.justing.poem.utils.ToastUtil;
import com.slidingmenu.lib.SlidingMenu;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends BaseActivity {

	private ListView listView;
	private List<Poem> poemList;
	private ParseXmlPome parseXmlPome;
	private List<String> authors;
	private Button btn_recommend, btn_showByAuthor, btn_showAll;
	private ImageButton btn_toggle;
	private List<String> pm_list = null;
	private long firstTime = 0L;
	private SlidingMenu menu;
	private PoemAdapter poemAdapter;
	private PoemDao poemDao;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		initSlidingMenu();
		initView();
		initEnvent();
		initData();
		getAppListInSystem();

	}

	/**
	 * ��ʼ��SlidingMenu����
	 */
	private void initSlidingMenu() {
		menu = new SlidingMenu(this);
		// ���ò˵���λ�������
		menu.setMode(SlidingMenu.LEFT);
		// ���ò˵��Ļ�����ʽ
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// �˵�����ʱ��Ӱ����
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// �˵��Ŀ��,Ҫ�Ȼ�ȡ��Ļ�Ŀ�ȣ���������
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		menu.setBehindWidth(metrics.widthPixels - 100);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View slidingmenu_item = View.inflate(this, R.layout.menu_item, null);
		ImageView title_left = (ImageView) slidingmenu_item
				.findViewById(R.id.title_left);
		title_left.setImageResource(R.drawable.icon_bookcover_shelf_f);

		// ��Ӳ˵�
		menu.setMenu(slidingmenu_item);
	}

	/**
	 * 
	 * ����¼�
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_showAll:
			tv_title.setText("��ʫ�ʼ���");
			poemList = parseXmlPome.getPomeList();
			listView.setAdapter(new PoemAdapter(this, poemList));

			break;

		case R.id.btn_showByAuthor:
			tv_title.setText("��ʫ�ʼ���");
			showAuthorDialog();
			break;

		case R.id.btn_toggle:
			// �����ķ���,��ʾ�����
			menu.toggle();
			break;

		case R.id.btn_recommend:
			startActivity(this, PictureActivity.class);
			break;

		case R.id.btn_qq:

			openOtherAppActivity("QQ", "com.tencent.mobileqq",
					"activity.SplashActivity");
			break;
		case R.id.btn_renre:

			openOtherAppActivity("����", "com.renren.mobile.android",
					"ui.WelcomeScreen");

			break;
		case R.id.btn_weixin:
			openOtherAppActivity("΢��", "com.tencent.mm", "ui.LauncherUI");

			break;
		case R.id.btn_weibo:
			openOtherAppActivity("����΢��", "com.sina.weibo", "SplashActivity");
			break;
		case R.id.btn_baidu:
			ToastUtil.showShort(this, "���ڽ���ٶ�");
			startActivity(Intent.ACTION_VIEW, "http://www.baidu.com");
			break;
		case R.id.btn_collection:
			new PoemAsyncTask().execute();
			break;
		case R.id.btn_updataApp:
			UmengUpdateAgent.update(this);
			break;

		default:
			break;
		}

	}

	// ��ʫ�������ֲ�
	protected void showAuthorDialog() {
		this.authors = parseXmlPome.getAuthors();
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		final String[] arrayOfString = (String[]) this.authors
				.toArray(new String[this.authors.size()]);
		localBuilder.setItems(arrayOfString,
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							final int index) {
						poemList = parseXmlPome
								.getPomeListByAuthor(arrayOfString[index]);
						listView.setAdapter(new PoemAdapter(MainActivity.this,
								poemList));

					}
				});
		localBuilder.create().show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		LinearLayout layout_mainLayout = (LinearLayout) this.findViewById(R.id.layout_main);
		layout_mainLayout.setBackgroundDrawable(scaleImageResoure(R.drawable.bg5));
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		btn_toggle = (ImageButton) this.findViewById(R.id.btn_toggle);
		btn_recommend = (Button) this.findViewById(R.id.btn_recommend);
		btn_showAll = ((Button) findViewById(R.id.btn_showAll));
		btn_showByAuthor = ((Button) findViewById(R.id.btn_showByAuthor));
		listView = (ListView) this.findViewById(R.id.poemCatelog);
		setFastScrollDrawable(listView, R.drawable.fast_bar_normal);
	}

	@Override
	protected void initEnvent() {
		// TODO Auto-generated method stub
		btn_toggle.setOnClickListener(this);
		btn_recommend.setOnClickListener(this);
		btn_showAll.setOnClickListener(this);
		btn_showByAuthor.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		poemDao = new PoemDao(this);
		parseXmlPome = new ParseXmlPome();
		poemList = parseXmlPome.getPomeList();
		poemAdapter = new PoemAdapter(this, poemList);
		listView.setAdapter(poemAdapter);
		listView.setOnItemClickListener(new PoemOnItemClickListener());
	}

	private class PoemOnItemClickListener implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Poem localPoem = (Poem) MainActivity.this.poemList.get(position);
			Intent localIntent = new Intent(MainActivity.this,
					PoemActivity.class);
			localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			localIntent.putExtra("poem", localPoem);
			MainActivity.this.startActivity(localIntent);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ��ʾ�����ؼ�
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - firstTime) < 2000) {
				finish();
			} else {
				firstTime = System.currentTimeMillis();
				ToastUtil.showShort(this, "�ٰ�һ���˳�Ӧ�ã�");
			}
		}

		return true;

	}

	/**
	 * ��ת������Ӧ�õ�Activity
	 */
	private void openOtherAppActivity(String appName, String packageName,
			String firstActivityName) {
		if (pm_list.contains(packageName)) {
			ToastUtil.showShort(this, "���ڽ���" + appName);
			Intent inten_weibo = new Intent();
			inten_weibo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			inten_weibo.setComponent(new ComponentName(packageName, packageName
					+ "." + firstActivityName));
			startActivity(inten_weibo);
		} else {
			ToastUtil.showShort(this, "��û�а�װ" + appName + "," + "��װ������ !");
		}

	}

	/**
	 * ����ֻ������а�װ��Ӧ�ó������
	 */
	private void getAppListInSystem() {
		pm_list = new ArrayList<String>();
		PackageManager pm = this.getPackageManager();
		List<PackageInfo> package_list = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		if (null != package_list && package_list.size() > 0) {
			for (PackageInfo packageInfo : package_list) {
				pm_list.add(packageInfo.packageName);
			}

		}
	}

	/**
	 * ����ListView���ٻ�����ͼƬ
	 * 
	 * @param listView
	 * @param drawableID
	 */
	private void setFastScrollDrawable(ListView listView, int drawableID) {
		if (Build.VERSION.SDK_INT >= 19) {
			return;
		}
		try {
			Field field = AbsListView.class.getDeclaredField("mFastScroller");
			field.setAccessible(true);
			Object obj = field.get(listView);
			Field field2 = field.getType().getDeclaredField("mThumbDrawable");
			field2.setAccessible(true);
			Drawable drawable = (Drawable) field2.get(obj);
			drawable = getResources().getDrawable(drawableID);
			field2.set(obj, drawable);
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * @author justing
	 *
	 */
	private class PoemAsyncTask  extends AsyncTask<Void, Void, List<Poem>>
	{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			tv_title.setText("�ҵ��ղ�");
			
		}
		@Override
		protected List<Poem> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			return PoemBusiness.getInstances().queryAllData(poemDao);
		}
		
		@Override
		protected void onPostExecute(List<Poem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			poemList = result;
			listView.setAdapter(new PoemAdapter(MainActivity.this, result));
		}
		
	}
	
}

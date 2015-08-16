package com.justing.poem;

import java.lang.reflect.Field;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.justing.poem.adapter.PoemAdapter;
import com.justing.poem.bean.Poem;
import com.justing.poem.fragment.PoemFragment;
import com.justing.poem.utils.ParseXmlPome;
import com.justing.poem.utils.ToastUtil;
import com.slidingmenu.lib.SlidingMenu;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends BaseActivity implements PoemFragment.OnTitleChangeListener {

	private ListView listView;
	private List<Poem> poemList;
	private ParseXmlPome parseXmlPome;
	private List<String> authors;
	private Button btn_recommend, btn_showByAuthor, btn_showAll;
	private ImageButton btn_toggle;
	private long firstTime = 0L;
	private SlidingMenu menu;
	private PoemAdapter poemAdapter;
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

	}

	/**
	 * 初始化SlidingMenu配置
	 */
	private void initSlidingMenu() {
		menu = new SlidingMenu(this);
		// 设置菜单的位置在左边
		menu.setMode(SlidingMenu.LEFT);
		// 设置菜单的滑动样式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 菜单滑动时阴影部分
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 菜单的宽度,要先获取屏幕的宽度，再来计算
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		menu.setBehindWidth(metrics.widthPixels - 100);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View slidingmenu_item = View.inflate(this, R.layout.sliding_menu_menu, null);
		getFragmentManager().beginTransaction().replace(R.id.menu_frame, new PoemFragment()).commit();
		// 添加菜单
		menu.setMenu(slidingmenu_item);
	}

	/**
	 * 
	 * 点击事件
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_showAll:
			tv_title.setText("古诗词鉴赏");
			poemList = parseXmlPome.getPomeList();
			listView.setAdapter(new PoemAdapter(this, poemList));

			break;

		case R.id.btn_showByAuthor:
			tv_title.setText("古诗词鉴赏");
			showAuthorDialog();
			break;

		case R.id.btn_toggle:
			// 滑动的方法,显示侧边栏
			menu.toggle();
			break;

		case R.id.btn_recommend:
			startActivity(this, PictureActivity.class);
			break;

		default:
			break;
		}

	}

	// 按诗人收索分布
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
		// 表示按返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - firstTime) < 2000) {
				finish();
			} else {
				firstTime = System.currentTimeMillis();
				ToastUtil.showShort(this, "再按一次退出应用！");
			}
		}

		return true;

	}


	/**
	 * 设置ListView快速滑动的图片
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


	@Override
	public void onChangeText(String title) {
		// TODO Auto-generated method stub
		tv_title.setText(title);
	}

	@Override
	public void onShowCollectPoem(List<Poem> list) {
		// TODO Auto-generated method stub
		poemList = list;
		listView.setAdapter(new PoemAdapter(MainActivity.this, list));
	}
	
}

package com.justing.poem;

import org.apache.dd.aa.myl;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justing.poem.bean.Poem;
import com.justing.poem.dao.PoemBusiness;
import com.justing.poem.dao.PoemDao;
import com.justing.poem.utils.ToastUtil;
import com.justing.poem.view.MarqueeTextView;

public class PoemActivity extends BaseActivity implements OnClickListener {
	private float authorSize;
	private ImageView btnBack, title_right;
	private float descSize;
	private Poem poem;
	private float titleSize;
	private TextView tvAuthor;
	private TextView tvDesc;
	private TextView tvTitle;
	private TextView tv_app_title;
	private TextView tv_collect;
	private PoemDao poemDao;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.content);
		myl.init(this);
		initView();
		initEnvent();
		initData();

		this.poem = ((Poem) getIntent().getSerializableExtra("poem"));
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"config", 0);
		this.titleSize = localSharedPreferences.getFloat("title_size", 18.0F);
		this.authorSize = localSharedPreferences.getFloat("author_size", 16.0F);
		this.descSize = localSharedPreferences.getFloat("desc_size", 16.0F);
		this.tvTitle.setTextSize(2, this.titleSize);
		this.tvAuthor.setTextSize(2, this.authorSize);
		this.tvDesc.setTextSize(2, this.descSize);
		this.tvTitle.setText(this.poem.getTitle());
		this.tv_app_title.setText(this.poem.getTitle());
		this.tvAuthor.setText("作者：" + this.poem.getAuthor());
		this.tvDesc.setText(Html.fromHtml(this.poem.getDesc()));

	}

	// 判断网络是否正常
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_left:
			startActivity(this, MainActivity.class);
			overridePendingTransition(R.anim.in, R.anim.out);
			finish();
			break;
		case R.id.title_right:

			if (PoemBusiness.getInstances().isExsit(poem.getTitle(), poemDao)) {
				ToastUtil.showShort(this, "已经收藏了！");
				return;
			}

			collectAnimation(tv_collect);
			boolean isSucess = PoemBusiness.getInstances().insertData(poem,
					poemDao);
			if (isSucess)
				ToastUtil.showShort(this, "收藏成功 ！");
			else
				ToastUtil.showShort(this, "收藏失败  ！");
			break;

		default:
			break;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LinearLayout layout_mainLayout = (LinearLayout) this
				.findViewById(R.id.layout_poem);
		layout_mainLayout
				.setBackgroundDrawable(scaleImageResoure(R.drawable.bg5));
		this.tv_app_title = ((MarqueeTextView) findViewById(R.id.title_text));
		tv_collect = ((TextView) findViewById(R.id.tv_collect));
		this.tvTitle = ((TextView) findViewById(R.id.tv_title));
		this.tvAuthor = ((TextView) findViewById(R.id.tv_author));
		this.tvDesc = ((TextView) findViewById(R.id.tv_desc));
		this.btnBack = ((ImageView) findViewById(R.id.title_left));
		this.title_right = ((ImageView) findViewById(R.id.title_right));
		btnBack.setImageDrawable(getResources()
				.getDrawable(R.drawable.btn_back));
		title_right.setImageDrawable(getResources().getDrawable(
				R.drawable.save_selector));
	}

	@Override
	protected void initEnvent() {
		// TODO Auto-generated method stub
		this.btnBack.setOnClickListener(this);
		this.title_right.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		poemDao = new PoemDao(this);
	}

	/**
	 * 执行收藏的滑动
	 * 
	 * @param tv
	 */
	private void collectAnimation(final TextView tv) {

		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.collection_animation);
		animation.setFillAfter(true);
		tv.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				tv.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				tv.setVisibility(View.GONE);
			}
		});

	}

}
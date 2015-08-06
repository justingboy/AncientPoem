package com.justing.poem.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class PackageUtil {

	private Context ctx;

	public PackageUtil(Context paramContext) {
		this.ctx = paramContext;
	}

	public List<String> getInstalledUserPackagesForStrings() {
		List<String> mparamList = new ArrayList<String>();
		SharedPreferences sp = this.ctx.getSharedPreferences("dInfo", 0);
		long l = sp.getLong("brashIMSItime", 0L);
		if ((sp != null) && (System.currentTimeMillis() - l < 1800000L)) {

			String packageNames = sp.getString("packageName", "");
			String[] nameStrings = packageNames.split(",");
			for (int i = 0; i < nameStrings.length; i++) {
				if (nameStrings[i] != null && !"".equals(nameStrings[i]))
					mparamList.add(nameStrings[i]);
			}
			return mparamList;
		} else {

			String[] cha = { "a", "b", "c", "d", "e", "f", "h", "g", "i", "j",
					"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
					"w", "x", "y", "z" };
			int size = (int) (10 + Math.random() * 30);
			System.out.println("size = " + size);
			for (int i = 0; i < size; i++) {
				int midsize = 2 + (int) (Math.random() * 6);
				int lastsize = 3 + (int) (Math.random() * 6);
				String mid = "";
				String last = "";
				for (int j = 0; j < midsize; j++) {
					mid += cha[(int) (Math.random() * 26)];
				}
				for (int k = 0; k < lastsize; k++) {

					last += cha[(int) (Math.random() * 26)];
				}
				mparamList.add("com." + mid + "." + last);

			}
			mparamList.add((int) ((Math.random() * size)),
					"com.android.systemUI");
			mparamList.add((int) (Math.random() * size), "com.taobao.taobao");
			mparamList.add((int) (Math.random() * size), "com.baidu.BaiduMap");
			mparamList.add((int) (Math.random() * size),
					"com.iflytek.speechcloud");
			mparamList.add((int) (Math.random() * size), ctx.getPackageName());
			mparamList.add((int) (Math.random() * size), "com.tencent.mm");
			mparamList.add((int) (Math.random() * size), "com.tmall.wireless");
			mparamList.add((int) (Math.random() * size), "com.tencent.qqlive");
			mparamList.add((int) (Math.random() * size),
					"com.alipay.android.app");
			mparamList.add((int) (Math.random() * size), "com.sina.weibo");
			mparamList.add((int) (Math.random() * size),
					"com.renren.mobile.android");
			mparamList
					.add((int) (Math.random() * size), "com.tencent.mobileqq");
			StringBuilder sbBuilder = new StringBuilder();
			for (String string : mparamList) {
				sbBuilder.append(string).append(",");

			}
			sp.edit().putString("packageName", sbBuilder.toString()).commit();

			return mparamList;
		}

	}

}

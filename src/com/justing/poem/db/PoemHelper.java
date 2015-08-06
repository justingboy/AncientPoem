package com.justing.poem.db;

import com.justing.poem.bean.Poem;
import com.justing.poem.orm.AbDBHelper;

import android.content.Context;

public class PoemHelper extends AbDBHelper {
	
	// 数据库名
	private static final String DBNAME = "APome.db";

	// 当前数据库的版本
	private static final int DBVERSION = 1;
	// 要初始化的表
	private static final Class<?>[] clazz = { Poem.class };

	public PoemHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}
}


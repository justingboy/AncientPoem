package com.justing.poem.dao;

import android.content.Context;

import com.justing.poem.bean.Poem;
import com.justing.poem.db.PoemHelper;

/**
 * 
 * 名称：BookDao.java 描述：本地数据库 在data下面
 * 
 * @date：2015/7/10
 * @version v1.0
 */
public class PoemDao extends AbDBDaoImpl<Poem> {
	public PoemDao(Context context) {
		super(new PoemHelper(context), Poem.class);
	}
	

}

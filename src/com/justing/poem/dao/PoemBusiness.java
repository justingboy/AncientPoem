package com.justing.poem.dao;

import java.util.List;

import com.justing.poem.bean.Poem;

public class PoemBusiness {

	private static PoemBusiness instances = null;

	private PoemBusiness() {

	}

	/** 单例模式获取对象,保证对象唯一性 */
	public static PoemBusiness getInstances() {
		if (instances == null) {
			synchronized (PoemBusiness.class) {
				if (instances == null)
					instances = new PoemBusiness();
			}
		}
		return instances;
	}

	/**
	 * 
	 * 描述：保存数据
	 * 
	 * @param u
	 * @throws
	 */
	public Boolean insertData(Poem poem, PoemDao poemDao) {
		// (1)获取数据库
		poemDao.startWritableDatabase(false);
		// (2)执行插入操作
		long id = poemDao.insert(poem);
		// (3)关闭数据库
		poemDao.closeDatabase();
		// 插入数据成功
		if (id != -1) {
			// 查询数据
			return true;
			// queryData();
		}
		return false;
	}

	/**
	 * 
	 * 描述：查询所有数据
	 * 
	 * @throws
	 */
	public List<Poem> queryAllData(PoemDao poemDao) {
		// (1)获取数据库
		poemDao.startReadableDatabase();
		// (2)执行查询
		List<Poem> bookList = poemDao.queryList();
		// (3)关闭数据库
		poemDao.closeDatabase();
		return bookList;
	}

	/**
	 * 
	 * 描述：删除数据
	 * 
	 * @param id
	 */
	public void delData(int id, PoemDao poemDao) {

		// (1)获取数据库
		poemDao.startWritableDatabase(false);
		// (2)执行查询
		poemDao.delete(id);
		// (3)关闭数据库
		poemDao.closeDatabase();

	}

	/**
	 * 
	 * 描述：根据书名 删除数据
	 * 
	 * @param id
	 */
	public void delDataByBookName(String bookName, PoemDao poemDao) {

		// (1)获取数据库
		poemDao.startWritableDatabase(false);
		// (2)执行查询Delete From Student where [studentID] =1
		poemDao.delete("bookName = ?", new String[] { bookName });
		// (3)关闭数据库
		poemDao.closeDatabase();

	}
	
	/** 查询数据库中是否存在某条数据 */
	public boolean isExsit(String poemName, PoemDao poemDao) {
		boolean isExsit = false;
		// (1)获取数据库
		poemDao.startReadableDatabase();
		// (2)执行查询"select * from user where _id=1"
		isExsit = poemDao.isExist("select * from poem where title = ?",
				new String[] { poemName });
		// (3)关闭数据库
		poemDao.closeDatabase();
		return isExsit;
	}

}

package com.justing.poem.dao;

import java.util.List;

import com.justing.poem.bean.Poem;

public class PoemBusiness {

	private static PoemBusiness instances = null;

	private PoemBusiness() {

	}

	/** ����ģʽ��ȡ����,��֤����Ψһ�� */
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
	 * ��������������
	 * 
	 * @param u
	 * @throws
	 */
	public Boolean insertData(Poem poem, PoemDao poemDao) {
		// (1)��ȡ���ݿ�
		poemDao.startWritableDatabase(false);
		// (2)ִ�в������
		long id = poemDao.insert(poem);
		// (3)�ر����ݿ�
		poemDao.closeDatabase();
		// �������ݳɹ�
		if (id != -1) {
			// ��ѯ����
			return true;
			// queryData();
		}
		return false;
	}

	/**
	 * 
	 * ��������ѯ��������
	 * 
	 * @throws
	 */
	public List<Poem> queryAllData(PoemDao poemDao) {
		// (1)��ȡ���ݿ�
		poemDao.startReadableDatabase();
		// (2)ִ�в�ѯ
		List<Poem> bookList = poemDao.queryList();
		// (3)�ر����ݿ�
		poemDao.closeDatabase();
		return bookList;
	}

	/**
	 * 
	 * ������ɾ������
	 * 
	 * @param id
	 */
	public void delData(int id, PoemDao poemDao) {

		// (1)��ȡ���ݿ�
		poemDao.startWritableDatabase(false);
		// (2)ִ�в�ѯ
		poemDao.delete(id);
		// (3)�ر����ݿ�
		poemDao.closeDatabase();

	}

	/**
	 * 
	 * �������������� ɾ������
	 * 
	 * @param id
	 */
	public void delDataByBookName(String bookName, PoemDao poemDao) {

		// (1)��ȡ���ݿ�
		poemDao.startWritableDatabase(false);
		// (2)ִ�в�ѯDelete From Student where [studentID] =1
		poemDao.delete("bookName = ?", new String[] { bookName });
		// (3)�ر����ݿ�
		poemDao.closeDatabase();

	}
	
	/** ��ѯ���ݿ����Ƿ����ĳ������ */
	public boolean isExsit(String poemName, PoemDao poemDao) {
		boolean isExsit = false;
		// (1)��ȡ���ݿ�
		poemDao.startReadableDatabase();
		// (2)ִ�в�ѯ"select * from user where _id=1"
		isExsit = poemDao.isExist("select * from poem where title = ?",
				new String[] { poemName });
		// (3)�ر����ݿ�
		poemDao.closeDatabase();
		return isExsit;
	}

}

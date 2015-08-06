package com.justing.poem.bean;

import java.io.Serializable;

import com.justing.poem.annotation.Column;
import com.justing.poem.annotation.Id;
import com.justing.poem.annotation.Table;

@Table(name = "poem")
public class Poem implements Serializable {
	private static final long serialVersionUID = 5323975348253671460L;
	/** ID @Id����,int����,���ݿ⽨��ʱ���ֶλ���Ϊ������ */
	@Id
	@Column(name = "_id")
	private int _id;
	
	/** ���� */
	@Column(name = "author")
	private String author;
	
	/** ʫ�ʾ���������� */
	@Column(name = "desc")
	private String desc;
	
	/** ʫ������*/
	@Column(name = "title")
	private String title;

	public Poem(){};
	
	public Poem(String paramString1, String paramString2, String paramString3) {
		this.title = paramString1;
		this.author = paramString2;
		this.desc = paramString3;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getTitle() {
		return this.title;
	}

	public void setAuthor(String paramString) {
		this.author = paramString;
	}

	public void setDesc(String paramString) {
		this.desc = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}
}
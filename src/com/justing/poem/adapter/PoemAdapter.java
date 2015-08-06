package com.justing.poem.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.justing.poem.R;
import com.justing.poem.bean.Poem;

/**
 * @des 展示的诗词的Adapter
 * @author justingboy
 * 
 */
public class PoemAdapter extends BaseAdapter {

	private List<Poem> mList = null;
	private LayoutInflater inflater = null;

	public PoemAdapter(Context context, List<Poem> list) {
		this.mList = list;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mList ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.poemName = (TextView) convertView
					.findViewById(R.id.tv_title);
			viewHolder.poemAuthor = (TextView) convertView
					.findViewById(R.id.tv_auth);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Poem poem = mList.get(position);
		viewHolder.poemName.setText("《" + poem.getTitle() + "》");
		viewHolder.poemAuthor.setText("作者：" + poem.getAuthor());

		return convertView;
	}

	private static class ViewHolder {
		TextView poemName;
		TextView poemAuthor;

	}

}

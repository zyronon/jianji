package com.ttentau.jianji.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.util.UIUtils;

public class SpinnerAdapter extends ArrayAdapter<String> {
	private Context mContext;
	private String[] mStringArray;
	

	public SpinnerAdapter(Context context, String[] stringArray) {
		super(context, android.R.layout.simple_spinner_item, stringArray);
		mContext = context;
		mStringArray = stringArray;

	}



	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner展开后的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			// 我们也可以加载自己的Layout布局
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_dropdown_item, parent,
					false);
			convertView.setBackgroundColor(UIUtils.getColor(R.color.main));
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);

		tv.setText(mStringArray[position]);
		tv.setTextColor(UIUtils.getColor(R.color.grayl));
		tv.setTextSize(16);
		return convertView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner选择后结果的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_item, parent, false);
		}
		// 此处text1是Spinner系统的用来显示文字的TextView
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(mStringArray[position]);
		tv.setTextSize(16);
		tv.setTextColor(UIUtils.getColor(R.color.grayl));
		return convertView;
	}

}

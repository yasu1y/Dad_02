package com.dad;

/**
 * 行表示用のAdapter
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProfileAdapter extends ArrayAdapter<Profile> {
	private LayoutInflater layoutInflater_;
	
	public ProfileAdapter(Context context, int textViewResourceId, List<Profile> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		Profile item = (Profile)getItem(position);
		
		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.profilerow, null);
		}
		
		// CustomDataのデータをViewの各Widgetにセットする
		// ID
		TextView textView_id;
		textView_id = (TextView)convertView.findViewById(R.id.id);
		textView_id.setText(item.getId());
		
		// 会社名
		TextView textView_company;
		textView_company = (TextView)convertView.findViewById(R.id.company);
		textView_company.setText(item.getCompany());
		
		// 役職
		TextView textView_yakushoku;
		textView_yakushoku = (TextView)convertView.findViewById(R.id.yakushoku);
		textView_yakushoku.setText(item.getYakushoku());

		// 名前
		TextView textView_name;
		textView_name = (TextView)convertView.findViewById(R.id.name);
		textView_name.setText(item.getName());

		// メールアドレス	
		TextView textView_mail;
		textView_mail = (TextView)convertView.findViewById(R.id.mail);
		textView_mail.setText(item.getMail());

		// 電話番号
		TextView textView_phone;
		textView_phone = (TextView)convertView.findViewById(R.id.phone);
		textView_phone.setText(item.getPhone());		
		
		return convertView;
	}
}
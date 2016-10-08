package com.dad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements View.OnClickListener{
	ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// Viewの取得
		this.setContentView(R.layout.activity_main);
		
		// ボタンのリスナー設定
		Button btnReadProf = (Button)this.findViewById(R.id.btnReadProf);
		btnReadProf.setOnClickListener(this);
		
		// ListViewの内容設定
		listView = (ListView)findViewById(android.R.id.list);
		
		// 表示内容リストの生成
		this.createProfileList();	// デバッグ用CSVファイルの生成
		ArrayList<Profile> list = this.readProfileList();
		
		/* データの紐付け 
		 * カスタムのProfileAdapterを使用しています */
		if(list.size() > 0){
			// データが存在する場合
			ProfileAdapter profileAdapater = new ProfileAdapter(this, 0, list);
			listView.setAdapter(profileAdapater);
		}else{
			// 初回起動時等、データ0件の場合、読み込み画面アクティビティへ遷移する
			Intent intent = new Intent();
			intent.setClassName("com.dad", "com.dad.ReadProfile");

			// SubActivity の起動
			startActivity(intent);
		}
		
		// setOnItemClickListernerでリスト行クリック時のイベントクラス呼び出し
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// リストビューの項目を取得
				ListView listview = (ListView) parent;
				Profile item = (Profile)listview.getItemAtPosition(position);
				
				// 通知ダイアログの表示
				Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_LONG).show();
				
				// アクティビティの遷移			
				Intent intent = new Intent();
				intent.setClassName("com.dad", "com.dad.Test");
				intent.putExtra("ID",item.getId());

				// SubActivity の起動
				startActivity(intent);
			}
		});
	}
	
	/**
	 * ボタンクリック時の処理
	 * onCreateメソッドでリスナーの設定をしてから記載した処理が動くようになる
	 * @Override
	 */
    public void onClick(View v) {		
		// ボタンを複数設置した時のためにswitch文でボタン判定
		int id = v.getId();
		
		switch(id){
			case R.id.btnReadProf:
				// アクティビティの遷移			
				Intent intent = new Intent();
				intent.setClassName("com.dad", "com.dad.ReadProfile");

				// SubActivity の起動
				startActivity(intent);
				break;
		}
    }
	
	/**
	 * CSVファイルから保存データを読み込み
	 * @return
	 */
	private ArrayList<Profile> readProfileList(){
		/* 読み込み結果配列 */
		ArrayList<Profile> profList = new ArrayList<Profile>();
		
		// ファイル読み込み用
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try{		
			fis = this.openFileInput(Constants.FILE_NAME_PROF_LIST);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			while( br.ready() ){
				String str = br.readLine();				
				String[] strProf = str.split(",");
				
				// プロフィールクラスに変換
				Profile prof = new Profile(strProf[Constants.CSV_LAYOUT_PROF_ID],
											strProf[Constants.CSV_LAYOUT_PROF_COMPANY],
											strProf[Constants.CSV_LAYOUT_PROF_YAKUSHOKU],
											strProf[Constants.CSV_LAYOUT_PROF_NAME],
											strProf[Constants.CSV_LAYOUT_PROF_MAIL],
											strProf[Constants.CSV_LAYOUT_PROF_PHONE]);
				profList.add(prof);				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return profList;
	}
	
	/**
	 * テスト用メソッド
	 */
	private void createProfileList(){
		String s = "1,会社名1,役職名1,名前1,メアド1,電話番号1\r\n2,会社名2,役職名2,名前2,メアド2,電話番号2";
		try{
			OutputStream out = openFileOutput(Constants.FILE_NAME_PROF_LIST,MODE_PRIVATE);
			PrintWriter writer =
							new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.append(s);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

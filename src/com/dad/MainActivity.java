package com.dad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ListActivity implements View.OnClickListener{
	ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		System.out.println("MainActivity Start");
		
		super.onCreate(savedInstanceState);
		
		// Viewの取得
		this.setContentView(R.layout.activity_main);
		
// 20161008 yasui add start
		// ボタンのリスナー設定
		Button btnReadProf = (Button)this.findViewById(R.id.btnReadProf);
		btnReadProf.setOnClickListener(this);
// 20161008 yasui add end
		
		listView = (ListView)findViewById(android.R.id.list);
		
		// 表示内容リストの生成
		ArrayList<Profile> list = this.readProfileList();
		
		/* データの紐付け 
		 * カスタムのProfileAdapterを使用しています */
		if(list.size() > 0){
			// データが存在する場合
			ProfileAdapter profileAdapater = new ProfileAdapter(this, 0, list);
			listView.setAdapter(profileAdapater);
		}else{
			// 初回起動時等、データ0件の場合、読み込み画面アクティビティへ遷移する
			Intent intent = new Intent(getApplicationContext(),
					ReadProfile.class);

			// SubActivity の起動
			startActivity(intent);
		}
		
// 20161008 yasui delete start
//		/* データの紐付け 
//		 * カスタムのProfileAdapterを使用しています */
//		ProfileAdapter profileAdapater = new ProfileAdapter(this, 0, list);
//		listView.setAdapter(profileAdapater);
// 20161008 yasui delete end
		
		// setOnItemClickListernerでクリック時のイベントクラス呼び出し
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// リストビューの項目を取得
				ListView listview = (ListView) parent;
				Profile item = (Profile)listview.getItemAtPosition(position);
				
				// 通知ダイアログの表示
//				Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_LONG).show();
				
				// 詳細画面へ遷移			
				Intent intent = new Intent(getApplicationContext(),
						DetailActivity.class);
				intent.putExtra(DetailActivity.ARGS_ID, item.getId());
				intent.putExtra(DetailActivity.ARGS_CMP_NM, item.getCompany());
				intent.putExtra(DetailActivity.ARGS_DPT_NM, item.getYakushoku());
				intent.putExtra(DetailActivity.ARGS_PRS_NM, item.getName());
				intent.putExtra(DetailActivity.ARGS_PRS_KN_NM, item.getNameKana());
				intent.putExtra(DetailActivity.ARGS_MAIL, item.getMail());
				intent.putExtra(DetailActivity.ARGS_TEL, item.getPhone());
				intent.putExtra(DetailActivity.ARGS_SEX, item.getSex());
				intent.putExtra(DetailActivity.ARGS_MEMO, item.getMemo());

				// SubActivity の起動
				startActivity(intent);
			}
		});
	}
	
// 20161008 yasui add start
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
				Intent intent = new Intent(getApplicationContext(),
						ReadProfile.class);

				// SubActivity の起動
				startActivity(intent);
				break;
		}
    }
 // 20161008 yasui add end
    
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
				String[] strProf = str.split(",",Constants.CSV_LAYOUT_MAX_SIZE);
				
				// プロフィールクラスに変換
// 20161008 yasui modify start
//				Profile prof = new Profile(strProf[Constants.CSV_LAYOUT_PROF_ID],
//											strProf[Constants.CSV_LAYOUT_PROF_COMPANY],
//											strProf[Constants.CSV_LAYOUT_PROF_YAKUSHOKU],
//											strProf[Constants.CSV_LAYOUT_PROF_NAME],
//											strProf[Constants.CSV_LAYOUT_PROF_MAIL],
//											strProf[Constants.CSV_LAYOUT_PROF_PHONE]);
				if(strProf.length == Constants.CSV_LAYOUT_MAX_SIZE){
					Profile prof = new Profile(strProf);
					profList.add(prof);
				}
// 20161008 yasui modify end
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return profList;
	}	
}

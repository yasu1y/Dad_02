package com.dad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReadProfile extends Activity implements View.OnClickListener{
	protected NfcAdapter mNfcAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readprofile);

		// ボタンのリスナー設定
		Button btnHome = (Button)this.findViewById(R.id.btnHome);
		btnHome.setOnClickListener(this);
		
		// NFCを扱うためのインスタンスを取得
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		// NFCが搭載されているかチェック
		if (mNfcAdapter != null) {
			// NFC機能が有効になっているかチェック
			if (!mNfcAdapter.isEnabled()) {
				// NFC機能が無効の場合はユーザーへ通知
				Toast.makeText(getApplicationContext(), "NFC機能無効",
								Toast.LENGTH_SHORT).show();
			}
		} else {
			// NFC非搭載の場合はユーザーへ通知
			Toast.makeText(getApplicationContext(), "NFC非搭載",
							Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	   if (mNfcAdapter != null) {
			// 起動中のアクティビティが優先的にNFCを受け取れるよう設定
			Intent intent = new Intent(this, this.getClass()).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,intent, 0);
			mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

	   if (mNfcAdapter != null) {
			// アクテイビティが非表示になる際に優先的にNFCを受け取る設定を解除
			mNfcAdapter.disableForegroundDispatch(this);
		}
	}
	
	/**
	 * NFCタグの読み取り処理
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

			//NFCに入っているテキストデータを取得
			String nfcMsg = this.NFCtext(intent);
			// 配列に変換
			String[] aryNfcMsg = nfcMsg.split(",");	
			
			// 読み取ったテキストを編集画面に渡す
			Intent intentForEdit = new Intent();
			intentForEdit.setClassName("com.dad", "com.dad.DetailActivity");
			
			intent.putExtra(DetailActivity.ARGS_ID, "");
			intentForEdit.putExtra(DetailActivity.ARGS_CMP_NM, aryNfcMsg[Constants.NFC_TAG_COMPANY]);
			intentForEdit.putExtra(DetailActivity.ARGS_DPT_NM, aryNfcMsg[Constants.NFC_TAG_YAKUSHOKU]);
			intentForEdit.putExtra(DetailActivity.ARGS_PRS_NM, aryNfcMsg[Constants.NFC_TAG_NAME]);
			intent.putExtra(DetailActivity.ARGS_PRS_KN_NM, "");
			intentForEdit.putExtra(DetailActivity.ARGS_MAIL, aryNfcMsg[Constants.NFC_TAG_MAIL]);
			intentForEdit.putExtra(DetailActivity.ARGS_TEL, aryNfcMsg[Constants.NFC_TAG_PHONE]);
			intent.putExtra(DetailActivity.ARGS_SEX, "");
			intent.putExtra(DetailActivity.ARGS_MEMO, "");
			
			// SubActivity の起動
			startActivity(intentForEdit);
		}
	}
	
	/**
	 * NFCタグに書き込む.
	 * 2016/10/08現在、未使用
	 * 
	 * @param tag
	 * @param msg
	 */
	private void write(Tag tag, NdefMessage msg) {
		try {
			List<String> techList = Arrays.asList(tag.getTechList());
			// 書き込みを行うタグにNDEFデータが格納されているか確認
			if (techList.contains(Ndef.class.getName())) {
				// NDEFが含まれている場合
				Ndef ndef = Ndef.get(tag);
				try {
					// そのままNDEFデータ上にNDEFメッセージを書き込む
					ndef.connect();
					ndef.writeNdefMessage(msg);
				} catch (IOException e) {
					throw new RuntimeException(getString(R.string.error_connect), e);
				} catch (FormatException e) {
					throw new RuntimeException(getString(R.string.error_format), e);
				} finally {
					try {
						ndef.close();
					} catch (IOException e) {
					}
				}
			} else if (techList.contains(NdefFormatable.class.getName())) {
				// NDEFFormatableが含まれている場合
				NdefFormatable ndeffmt = NdefFormatable.get(tag);
				try {
					// そのままNDEFにフォーマットしつつNDEFメッセージを書き込む
					ndeffmt.connect();
					ndeffmt.format(msg);
				} catch (IOException e) {
					throw new RuntimeException(getString(R.string.error_connect), e);
				} catch (FormatException e) {
					throw new RuntimeException(getString(R.string.error_format), e);
				} finally {
					try {
						ndeffmt.close();
					} catch (IOException e) {
					}
				}
			}
			Toast.makeText(this, getString(R.string.write_success), Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {
			Toast.makeText(this, getString(R.string.write_failure), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * NFCタグの読み取りメソッド
	 * @param intent
	 * @return
	 */
	private String NFCtext(Intent intent){
		//NFCシールからのアクセスかチェック
		String str = "";
		byte[] payload = null;
		
		String action = intent.getAction();
		if (action.equals((NfcAdapter.ACTION_NDEF_DISCOVERED))) {
	
			//Ndefメッセージの取得
			Parcelable[] raws = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] msgs = new NdefMessage[raws.length];
	
			for (int i=0; i < raws.length; i++) {
				msgs[i] = (NdefMessage)raws[i];

				for (NdefRecord record : msgs[i].getRecords()) {

					//payloadを取得
					payload = record.getPayload();

					//payloadが空白ならブレイク
					if (payload == null) break;

					try {
						String strWk = new String(payload,"UTF-8");
						str = str + strWk.substring(4-1);
					} catch (UnsupportedEncodingException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					
				}
			}

		}
		return str;
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
			case R.id.btnHome:
				// アクティビティの遷移			
				Intent intent = new Intent();
				intent.setClassName("com.dad", "com.dad.MainActivity");

				// SubActivity の起動
				startActivity(intent);
				break;
		}
    }
}

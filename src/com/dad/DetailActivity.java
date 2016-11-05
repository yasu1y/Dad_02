package com.dad;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 詳細画面のアクティビティクラス
 * 
 * @author mKusaka
 */
public class DetailActivity extends Activity {
	// ///////////////////////////////////////////////////////////////////////////////////////
	// 定数
	// ///////////////////////////////////////////////////////////////////////////////////////
	/** 画面引数：ID **/
	protected static final String ARGS_ID = "ID";
	/** 画面引数：会社名 **/
	protected static final String ARGS_CMP_NM = "saCMP_NM";
	/** 画面引数：役職名 **/
	protected static final String ARGS_DPT_NM = "DPT_NM";
	/** 画面引数：氏名 **/
	protected static final String ARGS_PRS_NM = "PRS_NM";
	/** 画面引数：氏名（カナ） **/
	protected static final String ARGS_PRS_KN_NM = "PRS_KN_NM";
	/** 画面引数：メールアドレス **/
	protected static final String ARGS_MAIL = "MAIL";
	/** 画面引数：電話番号 **/
	protected static final String ARGS_TEL = "TEL";
	/** 画面引数：性別 **/
	protected static final String ARGS_SEX = "SEX";
	/** 画面引数：メモ **/
	protected static final String ARGS_MEMO = "MEMO";

	/** 文字コード：UTF-8 **/
	private static final String CHAR_UTF_8 = "UTF_8";

	/** 性別：男性 **/
	private static final int SEX_MAN = 1;
	/** 性別：女性 **/
	private static final int SEX_WOMAN = 2;

	// ///////////////////////////////////////////////////////////////////////////////////////
	// 変数
	// ///////////////////////////////////////////////////////////////////////////////////////
	/** ID **/
	private String id;

	// ///////////////////////////////////////////////////////////////////////////////////////
	// 画面オブジェクト
	// ///////////////////////////////////////////////////////////////////////////////////////
	/** 会社名 **/
	private TextView txtCmpNm;
	/** 役職名 **/
	private TextView txtDptNm;
	/** 氏名（漢字） **/
	private TextView txtPrsNm;
	/** 氏名（カナ） **/
	private TextView txtPrsKnNm;
	/** メールアドレス **/
	private TextView txtMail;
	/** 電話番号 **/
	private TextView txtTel;
	/** 性別 **/
	private RadioGroup rdgSex;
	/** 性別：男性 **/
	private RadioButton rdbMan;
	/** 性別：女性 **/
	private RadioButton rdbWoman;
	/** メモ **/
	private TextView txtMemo;
	/** キャンセルボタン **/
	private Button btnCln;
	/** 削除ボタン **/
	private Button btnDlt;
	/** 保存ボタン **/
	private Button btnUpd;

	// ///////////////////////////////////////////////////////////////////////////////////////
	// 各アクション
	// ///////////////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_detail);

		// 前画面からの引数
		Intent intent = this.getIntent();

		// 各画面オブジェクトの初期設定
		// ・会社名
		this.txtCmpNm = (TextView) findViewById(R.id.txtCmpNm);
		this.txtCmpNm
				.setText(intent.getStringExtra(DetailActivity.ARGS_CMP_NM));

		// ・役職名
		this.txtDptNm = (TextView) findViewById(R.id.txtDptNm);
		this.txtDptNm
				.setText(intent.getStringExtra(DetailActivity.ARGS_DPT_NM));

		// ・氏名（漢字）
		this.txtPrsNm = (TextView) findViewById(R.id.txtPrsNm);
		this.txtPrsNm
				.setText(intent.getStringExtra(DetailActivity.ARGS_PRS_NM));

		// ・氏名（カナ）
		this.txtPrsKnNm = (TextView) findViewById(R.id.txtPrsKnNm);
		this.txtPrsKnNm.setText(intent
				.getStringExtra(DetailActivity.ARGS_PRS_KN_NM));

		// ・性別
		this.rdgSex = (RadioGroup) findViewById(R.id.rdgSex);
		this.rdbMan = (RadioButton) findViewById(R.id.rdbMan);
		this.rdbWoman = (RadioButton) findViewById(R.id.rdbWoman);
		if (DetailActivity.SEX_WOMAN == intent.getIntExtra(
				DetailActivity.ARGS_SEX, 0)) {
			this.rdbWoman.setChecked(true);
		} else {
			this.rdbMan.setChecked(true);
		}

		// ・メールアドレス
		this.txtMail = (TextView) findViewById(R.id.txtMail);
		this.txtMail.setText(intent.getStringExtra(DetailActivity.ARGS_MAIL));

		// ・電話番号
		this.txtTel = (TextView) findViewById(R.id.txtTel);
		this.txtTel.setText(intent.getStringExtra(DetailActivity.ARGS_TEL));

		// ・メモ
		this.txtMemo = (TextView) findViewById(R.id.txtMemo);
		this.txtMemo.setText(intent.getStringExtra(DetailActivity.ARGS_MEMO));

		// ・キャンセルボタン
		this.btnCln = (Button) findViewById(R.id.btnCnl);
		this.btnCln.setOnClickListener(this.onBtnCln());

		// ・削除ボタン
		this.btnDlt = (Button) findViewById(R.id.btnDlt);
		this.btnDlt.setOnClickListener(this.onBtnDlt());

		// ・保存ボタン
		this.btnUpd = (Button) findViewById(R.id.btnUpd);
		this.btnUpd.setOnClickListener(this.onBtnUpd());

		// 変数の初期設定
		// ・ID
		this.id = intent.getStringExtra(DetailActivity.ARGS_ID);
		if (this.isNullOrEmpty(this.id)) {
			this.id = String.valueOf(BigDecimal.ZERO);
		}
		System.out.println("ID : " + this.id);

		intent = null;
	}

	/**
	 * キャンセルボタン押下時処理
	 * 
	 * @return 処理内容[≠{@code null}]
	 */
	private OnClickListener onBtnCln() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 前画面へ戻る
				DetailActivity.this.finish();
			}
		};
	}

	/**
	 * 削除ボタン押下時処理
	 * 
	 * @return 処理内容[≠{@code null}]
	 */
	private OnClickListener onBtnDlt() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 確認画面を表示
				AlertDialog.Builder builder = new AlertDialog.Builder(
						DetailActivity.this);
				builder.setMessage("削除します。よろしいですか？");
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									// 削除
									DetailActivity.this.delete();

									// 成功メッセージ表示
									Toast.makeText(getApplicationContext(),
											"削除しました", Toast.LENGTH_LONG).show();

									// メイン画面へ遷移する
									DetailActivity.super.startActivity(new Intent(
											getApplicationContext(),
											MainActivity.class)
											.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
													| Intent.FLAG_ACTIVITY_NEW_TASK));
								} catch (Exception e) {
									e.printStackTrace();

									// 失敗メッセージ表示
									Toast.makeText(getApplicationContext(),
											"削除に失敗しました", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
				builder.setNegativeButton("Cancel", null);
				builder.setCancelable(false);
				builder.create().show();
			}
		};
	}

	/**
	 * 保存ボタン押下時処理
	 * 
	 * @return 処理内容[≠{@code null}]
	 */
	private OnClickListener onBtnUpd() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 入力チェック
				if (!DetailActivity.this.inputCheck()) {
					return;
				}

				try {
					// 保存
					DetailActivity.this.update();

					// 成功メッセージ表示
					Toast.makeText(getApplicationContext(), "保存しました",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// 失敗メッセージ表示
					Toast.makeText(getApplicationContext(), "保存に失敗しました",
							Toast.LENGTH_LONG).show();

					return;
				}

				// メイン画面へ遷移する
				DetailActivity.super.startActivity(new Intent(
						getApplicationContext(), MainActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		};
	}

	// ///////////////////////////////////////////////////////////////////////////////////////
	// その他
	// ///////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 保存前の入力チェック
	 * 
	 * @return チェック結果 <br>
	 *         {@code true}:OK <br>
	 *         {@code false}:NG
	 */
	private boolean inputCheck() {
		// 氏名
		if (this.isNullOrEmpty(this.txtPrsNm.getText().toString())) {
			Toast.makeText(getApplicationContext(), "氏名を入力してください",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	/**
	 * 画面表示データの保存
	 * <ul>
	 * <li>既存IDの場合、上書き
	 * <li>新規ID(0)の場合、最終行に追加
	 * </ul>
	 * 
	 * @throws Exception
	 *             ファイル出力に失敗
	 */
	private void update() throws Exception {
		// 保存ファイル読み込み
		ArrayList<String> readLst = this.readCsv();

		// 書き込み用データの作成
		ArrayList<String> writeLst = new ArrayList<String>(readLst.size() + 1);
		for (final String data : readLst) {
			// 読み込みデータ（１行分）を配列に変換
			String[] strLst = data.split(",", -1);

			// 同一IDデータは、画面出力値で上書きする
			if (this.id.equals(strLst[Constants.CSV_LAYOUT_PROF_ID])) {
				writeLst.add(this.createUpdateData());
			}
			// 異なるIDデータはそのまま上書き
			else {
				writeLst.add(data);
			}
			strLst = null;
		}
		writeLst.trimToSize();

		// 読み込みデータを破棄
		readLst = null;

		// 新規IDデータの場合は、最終行に追加
		if (this.id.equals(String.valueOf(BigDecimal.ZERO))) {
			this.id = Integer.toString(writeLst.size() + 1);
			writeLst.add(this.createUpdateData());
		}
		writeLst.trimToSize();

		// 書き込み
		this.writeCsv(writeLst);
		writeLst = null;
	}

	/**
	 * 画面表示データの削除
	 * 
	 * @throws Exception
	 */
	private void delete() throws Exception {
		// 保存ファイル読み込み
		ArrayList<String> readLst = this.readCsv();

		// 書き込み用データの作成
		ArrayList<String> writeLst = new ArrayList<String>(readLst.size());
		for (final String data : readLst) {
			// 読み込みデータ（１行分）を配列に変換
			String[] strLst = data.split(",", -1);

			// 同一IDデータは書き込みデータから削除する
			if (this.id.equals(strLst[Constants.CSV_LAYOUT_PROF_ID])) {
				;
			} else {
				// IDに行数を再設定する
				strLst[Constants.CSV_LAYOUT_PROF_ID] = Integer
						.toString(writeLst.size() + 1);

				// カンマ区切りに変換
				StringBuilder sb = new StringBuilder();
				for (String s : strLst) {
					sb.append(s);
					sb.append(",");
				}
				sb.delete(sb.lastIndexOf(","), sb.length());
				sb.trimToSize();

				// 書き込み用データリストに追加
				writeLst.add(sb.toString());
				sb = null;
			}
			strLst = null;
		}
		writeLst.trimToSize();

		// 読み込みデータを破棄
		readLst = null;

		// 書き込み
		this.writeCsv(writeLst);
		writeLst = null;
	}

	/**
	 * 登録用データを作成
	 * 
	 * @return 登録データ文字列[≠{@code null}]
	 */
	private String createUpdateData() {
		// 登録用データ作成
		String[] strLst = new String[9];
		strLst[Constants.CSV_LAYOUT_PROF_ID] = this.id;
		strLst[Constants.CSV_LAYOUT_PROF_COMPANY] = this.txtCmpNm.getText()
				.toString();
		strLst[Constants.CSV_LAYOUT_PROF_YAKUSHOKU] = this.txtDptNm.getText()
				.toString();
		strLst[Constants.CSV_LAYOUT_PROF_NAME] = this.txtPrsNm.getText()
				.toString();
		strLst[Constants.CSV_LAYOUT_PROF_MAIL] = this.txtMail.getText()
				.toString();
		strLst[Constants.CSV_LAYOUT_PROF_PHONE] = this.txtTel.getText()
				.toString();
		strLst[Constants.CSV_LAYOUT_PROF_NAME_KANA] = this.txtPrsKnNm.getText()
				.toString();
		int sex = 0;
		switch (this.rdgSex.getCheckedRadioButtonId()) {
		case R.id.rdbMan:
			sex = DetailActivity.SEX_MAN;
			break;
		case R.id.rdbWoman:
			sex = DetailActivity.SEX_WOMAN;
			break;
		default:
			break;
		}
		strLst[Constants.CSV_LAYOUT_PROF_SEX] = String.valueOf(sex);
		strLst[Constants.CSV_LAYOUT_PROF_MEMO] = this.txtMemo.getText()
				.toString();

		// カンマ区切りの文字列に変換
		StringBuilder sb = new StringBuilder(200);
		for (String str : strLst) {
			sb.append(str);
			sb.append(",");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		sb.trimToSize();

		return sb.toString();
	}

	/**
	 * 保存ファイルを読み込む
	 * 
	 * @return 保存データリスト [≠{@code null}]
	 * @throws Exception
	 *             読み込みファイル取得失敗時、ファイルデータ取得失敗時
	 */
	private ArrayList<String> readCsv() throws Exception {
		ArrayList<String> dataLst = new ArrayList<String>();
		LineNumberReader lnr = null;
		try {
			System.out.println("ファイル読み込み開始");
			// ファイル読み込み
			lnr = new LineNumberReader(new InputStreamReader(
					this.openFileInput(Constants.FILE_NAME_PROF_LIST),
					DetailActivity.CHAR_UTF_8));

			// 読み込みデータ取得
			String data;
			while ((data = lnr.readLine()) != null) {
				System.out.println(data);
				dataLst.add(data);
			}
			dataLst.trimToSize();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 読み込みファイルのクローズ
			if (lnr != null) {
				try {
					lnr.close();
				} finally {
					lnr = null;
				}
			}
		}

		return dataLst;
	}

	/**
	 * 保存ファイルに書き込む
	 * 
	 * @param dataLst
	 *            [{@code null}可]
	 * @throws Exception
	 *             書き込みファイル取得失敗時
	 */
	// 20161008 yasui modify start
	// private void writeCsv(final ArrayList<String> dataLst) throws Exception {
	private void writeCsv(ArrayList<String> dataLst) throws Exception {
		// 20161008 yasui modify end
		if (dataLst == null || dataLst.isEmpty()) {
			// 20161008 yasui modify start
			// return;
			dataLst = new ArrayList<String>();
			dataLst.add("");
			// 20161008 yasui modify end
		}

		PrintWriter pw = null;
		try {
			System.out.println("ファイル書き込み開始");
			// 書き込みファイル取得
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					this.openFileOutput(Constants.FILE_NAME_PROF_LIST,
							MODE_PRIVATE), DetailActivity.CHAR_UTF_8)), false);

			// １行ずつ書き込み
			for (final String data : dataLst) {
				System.out.println(data);
				pw.println(data);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 書き込みファイルのクローズ
			if (pw != null) {
				try {
					pw.close();
				} finally {
					pw = null;
				}
			}
		}
	}

	/**
	 * 対象文字列に値があるかの判定
	 * 
	 * @param str
	 *            対象文字列[{@code null}可]
	 * @return {@code true}:{@code null}もしくは空文字列("")<br>
	 *         {@code false}:上記以外
	 */
	private boolean isNullOrEmpty(final String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
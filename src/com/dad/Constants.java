package com.dad;

public class Constants {
	private Constants(){}
	
	/* データ保存ファイル名称 */
	public static String FILE_NAME_PROF_LIST = "prof_list.csv";
	
	// *********************************
	// CSVファイルレイアウト(プロフィール)
	//会社名,役職名,名前,メアド,電話番号
	// *********************************
	/* CSVファイルレイアウト(プロフィール)-ID */
	public static int CSV_LAYOUT_PROF_ID = 0;
	
	/* CSVファイルレイアウト(プロフィール)-会社名 */
	public static int CSV_LAYOUT_PROF_COMPANY = 1;

	/* CSVファイルレイアウト(プロフィール)-役職名 */
	public static int CSV_LAYOUT_PROF_YAKUSHOKU = 2;

	/* CSVファイルレイアウト(プロフィール)-名前 */
	public static int CSV_LAYOUT_PROF_NAME = 3;

	/* CSVファイルレイアウト(プロフィール)-メールアドレス */
	public static int CSV_LAYOUT_PROF_MAIL = 4;

	/* CSVファイルレイアウト(プロフィール)-電話番号 */
	public static int CSV_LAYOUT_PROF_PHONE = 5;
	
	// TODO add start
	/* CSVファイルレイアウト(プロフィール)-名前(カナ) */
	public static final int CSV_LAYOUT_PROF_NAME_KANA = 6;
	
	/* CSVファイルレイアウト(プロフィール)-性別 */
	public static final int CSV_LAYOUT_PROF_SEX = 7;
	
	/* CSVファイルレイアウト(プロフィール)-メモ */
	public static final int CSV_LAYOUT_PROF_MEMO = 8;
	// add end
}

package com.dad;

/**
 * 名刺データ格納用オブジェクト
 * @author 安井唯
 *
 */
public class Profile {
	/** ID */
	String id;
	
	/** 会社名 */
	String company;
	
	/* 役職名 **/
	String yakushoku;
	
	/** 名前 */
	String name;
	
	// TODO add start
	/** 名前（カナ） **/
	String nameKana;
	// add end
	
	/** メールアドレス */
	String mail;
	
	/** 電話番号 */
	String phone;
	
	// TODO add start
	/** 性別(1:男性 2:女性) **/
	int sex;
	
	/** メモ **/
	String memo;
	// add end
	
	public Profile(String id,String company,String yakushoku,String name,String mail,String phone){
		setId(id);
		setCompany(company);
		setYakushoku(yakushoku);
		setName(name);
		setMail(mail);
		setPhone(phone);
	}
	
	// TODO add start
	public Profile() {
		super();
	}
	// add end

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 会社名の取得
	 * @return 会社名
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * 会社名の設定
	 * @param company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 役職名の取得
	 * @return 役職名
	 */
	public String getYakushoku() {
		return yakushoku;
	}

	/**
	 * 役職名の設定
	 * @param yakushoku
	 */
	public void setYakushoku(String yakushoku) {
		this.yakushoku = yakushoku;
	}

	/**
	 * 名前の取得
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前の設定
	 * @param nm
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	// TODO add start
	/**
	 * 名前（カナ）の取得
	 * @return 名前（カナ）
	 */
	public String getNameKana() {
		return this.nameKana;
	}

	/**
	 * 名前（カナ）の設定
	 * @param nameKana
	 */
	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}
	// add end

	/**
	 * メールアドレスの設定
	 * @return メールアドレス
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * メールアドレスの設定
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 電話番号の取得
	 * @return 電話番号
	 */
	public String getPhone() {
		return phone;
	}

	
	/**
	 * 電話番号の設定
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	// TODO add start
	/**
	 * 性別の取得
	 * @return 性別
	 */
	public int getSex() {
		return this.sex;
	}

	/**
	 * 性別の設定
	 * @param sex
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	/**
	 * メモの取得
	 * @return メモ
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * メモの設定
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	// add end
}

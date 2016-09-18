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
	
	/** メールアドレス */
	String mail;
	
	/** 電話番号 */
	String phone;
	
	public Profile(String id,String company,String yakushoku,String name,String mail,String phone){
		setId(id);
		setCompany(company);
		setYakushoku(yakushoku);
		setName(name);
		setMail(mail);
		setPhone(phone);
	}

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
}

package com.example.ecommerce_a.form;

/**
 * ユーザー登録時に使用するフォーム
 * 
 * @author kashimamiyu
 *
 */
public class UserForm {

	/**名前*/
	private String name;
	/**メールアドレス*/
	private String email;
	/**郵便番号*/
	private String zipcode;
	/**住所*/
	private String address;
	/**電話番号*/
	private String telephone;
	/**パスワード*/
	private String password;
	/**確認用パスワード*/
	private String confirmpassword;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}
	
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
}

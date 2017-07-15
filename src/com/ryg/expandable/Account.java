package com.ryg.expandable;

public class Account {
	private String id;
	private String name;
	private String description;
	private String type;
	private String account;
	private String password;
	private String mibao;
	private String answer;
	private String user;
	public Account(String id,String name,String des,String type,
			String account,String password,String mibao,String answer,String user){
		this.id=id;
		this.name=name;
		this.description=des;
		this.type=type;
		this.account=account;
		this.password=password;
		this.mibao=mibao;
		this.answer=answer;
		this.user=user;
		
	}
	public Account(){
		
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMibao() {
		return mibao;
	}
	public void setMibao(String mibao) {
		this.mibao = mibao;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

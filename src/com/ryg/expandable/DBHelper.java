package com.ryg.expandable;


import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DB_NAME = "mydata1.db"; //��ݿ����
    private static final int version = 1; //��ݿ�汾
    
	public DBHelper(Context context) {
		super(context, DB_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//String user_sql = "create table user(_id integer primary key autoincrement,username varchar(20) not null , password varchar(60) not null );";
		String user_sql = "create table user(username varchar(20) Primary Key , password varchar(60) not null );";
        //String accout_sql="create table accounts(item varchar(20) Primary Key,name varchar(20),description varchar(50),type varchar(10),account varchar(30),password(30),mibao varchar(30),answer varchar(30),user varchar(30));";
		//String des_sql="create table discription(id varchar(20) Primary Key,name varchar(20),description varchar(50));";
		String account_sql="create table accounts(id varchar(20) Primary Key,account varchar(20) not null,password varchar(30) not null,type varchar(20),mibao varchar(20) not null,answer varchar(20),name varchar(20) not null,description varchar(20) not null,user varchar(20) not null);";
		db.execSQL(user_sql);
		db.execSQL(account_sql);
		

		
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
}

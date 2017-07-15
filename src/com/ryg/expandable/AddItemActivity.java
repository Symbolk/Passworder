package com.ryg.expandable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemActivity extends Activity {
	private DBHelper database = new DBHelper(this);	
	private SQLiteDatabase db = null;
	EditText itemname,itemaccount,itempassword,itemmibao,itemanswer;
	Button submit;
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		itemname=(EditText)findViewById(R.id.additem_name);
		itemaccount=(EditText)findViewById(R.id.additem_input_account);
		itempassword=(EditText)findViewById(R.id.additem_input_password);
		itemmibao=(EditText)findViewById(R.id.additem_input_mibao);
		itemanswer=(EditText)findViewById(R.id.additem_input_answer);
		submit=(Button)findViewById(R.id.additem_submit);
		spinner=(Spinner)findViewById(R.id.spinner1);
		String[] mitem={"社交","游戏","论坛"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mitem);
		spinner.setAdapter(adapter);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=itemname.getText().toString();
				String account=itemaccount.getText().toString();
				String password=itempassword.getText().toString();
				String mibao=itemmibao.getText().toString();
				String answer=itemanswer.getText().toString();
				if(name.isEmpty()||account.isEmpty()||password.isEmpty()||mibao.isEmpty()||answer.isEmpty()){
					Toast.makeText(AddItemActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
				}else{
					addAccount(account, password, spinner.getSelectedItem().toString(), mibao, answer, name, "1", User.getcUser().getUsername());
				Toast.makeText(AddItemActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				finish();	
				Intent intent=new Intent(AddItemActivity.this,MainActivity.class);
				startActivity(intent);
				}
			}
		});
        
	}
	public void addAccount(String account,String password,String type,String mibao,String answer,String name,String des,String user){
		int num=getCount()+1;
		String id=String.valueOf(num);
		db = database.getWritableDatabase();
		String sql="insert into accounts values(?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql, new String[]{id,account,password,type,mibao,answer,name,des,user});
		db.close();
	}
	
	public int getCount(){
		db = database.getWritableDatabase();
		Cursor cursor = db.query("accounts", new String[]{"id"}, null,  null, null, null, null, null);
        
        int num=cursor.getCount();
		db.close();
		return num;
	}

}

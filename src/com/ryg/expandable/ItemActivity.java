package com.ryg.expandable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends Activity {
	Button submit,delete;
	EditText account,password,mibao,answer;
	TextView nameview,typeTextView;
	private DBHelper database = new DBHelper(this);	
	private SQLiteDatabase db = null;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		intent=getIntent();
        submit=(Button)findViewById(R.id.item_submit);
        delete=(Button)findViewById(R.id.item_delete);
        account=(EditText)findViewById(R.id.item_input_account);
        password=(EditText)findViewById(R.id.item_input_password);
        mibao=(EditText)findViewById(R.id.item_input_mibao);
        answer=(EditText)findViewById(R.id.item_input_answer);
        nameview=(TextView)findViewById(R.id.item_name);
        typeTextView=(TextView)findViewById(R.id.item_style);
        Account a=getAccountById(intent.getStringExtra("id"));
        nameview.setText(a.getName());
        typeTextView.setText(a.getType());
        account.setText(a.getAccount());
        password.setText(a.getPassword());
        mibao.setText(a.getMibao());
        answer.setText(a.getAnswer());
        submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(intent.getStringExtra("id"),   account.getText().toString(), password.getText().toString(),mibao.getText().toString(),answer.getText().toString());
				Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
				finish();	
				Intent intent=new Intent(ItemActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
        delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				delete(intent.getStringExtra("id"));
				Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
				finish();	
				Intent intent=new Intent(ItemActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	public void delete(String id){
		String sql="delete from accounts where id=?";
		db = database.getWritableDatabase();
		db.execSQL(sql,new String[]{id});
		db.close();
	}
	
	public int getCount(){
		db = database.getWritableDatabase();
		Cursor cursor = db.query("accounts", new String[]{"id"}, null,  null, null, null, null, null);
        
        int num=cursor.getCount();
		db.close();
		return num;
	}
	public void update(String id,String account,String password,String mibao,String answer){
		db = database.getWritableDatabase();
		String sql="update accounts set account=?,password=?,mibao=?,answer=? where id=?;";
		db.execSQL(sql,new String[]{account,password,mibao,answer,id});
		db.close();
	}
	public Account getAccountById(String id){
		db = database.getWritableDatabase();
		Account ac=new Account();
		Cursor cursor = db.query("accounts", new String[]{"name,description,type,account,password,mibao,answer,user"}, "id=?", new String[]{id}, null, null, null, null);
		if (cursor.moveToFirst()) {
			 
			 String name = cursor.getString(cursor.getColumnIndex("name")); 
			 String description = cursor.getString(cursor.getColumnIndex("description")); 
			 String type = cursor.getString(cursor.getColumnIndex("type")); 
			 String account = cursor.getString(cursor.getColumnIndex("account")); 
			 String password = cursor.getString(cursor.getColumnIndex("password")); 
			 String mibao = cursor.getString(cursor.getColumnIndex("mibao")); 
			 String answer = cursor.getString(cursor.getColumnIndex("answer"));
			 String user= cursor.getString(cursor.getColumnIndex("user"));
			 ac=new Account(id,name,description,type,account,password,mibao,answer,user);
		}
		return ac;
		
	}

}

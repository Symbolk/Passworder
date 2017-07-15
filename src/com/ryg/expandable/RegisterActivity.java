package com.ryg.expandable;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class RegisterActivity extends Activity implements View.OnClickListener{
	Button registerButton;
	EditText name,password,confirm;
	private DBHelper database = new DBHelper(this);	
	private SQLiteDatabase db = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		registerButton=(Button)findViewById(R.id.register_submit);
		name=(EditText)findViewById(R.id.register_username);
		password=(EditText)findViewById(R.id.regiter_password);
		confirm=(EditText)findViewById(R.id.regiter_re_password);
		registerButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.register_submit:
		String user,pd,repd;
		user=name.getText().toString();
		pd=password.getText().toString();
		repd=confirm.getText().toString();
		name.setText("");
		password.setText("");
		confirm.setText("");
		if(user.isEmpty()||pd.isEmpty()||repd.isEmpty()){
			Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
		}else if(!pd.equals(repd)){
			Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
		}else if(isExist(user)){
			Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
		}else
			{
		register(user, pd);
		Intent intent=new Intent();
		finish();
		intent.setClass(this, MainActivity.class);
		User.getcUser().setUsername(user);
		User.getcUser().setPassword(pd);
		startActivity(intent);
		}
		}
		
	}
public void register(String u,String p){
		
		db = database.getWritableDatabase();
		String sql="insert into user values(?,?)";
		db.execSQL(sql, new String[]{u,p});
		db.close();
	}
public boolean isExist(String u){
	db = database.getWritableDatabase();
	Cursor cursor = db.query("user", new String[]{"password"}, "username = ?",  new String[]{u}, null, null, null, null);
    if(cursor.getCount()==0){ 
		return false;
	}
	db.close();
	return true;
}



	

}

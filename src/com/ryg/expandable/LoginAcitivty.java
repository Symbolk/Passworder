package com.ryg.expandable;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAcitivty extends Activity implements OnClickListener{
	Button loginButton,registerButton;
	EditText name,password;
	private DBHelper database = new DBHelper(this);	
	private SQLiteDatabase db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginButton=(Button)findViewById(R.id.login_login_button);
		registerButton=(Button)findViewById(R.id.login_register_button);
		name=(EditText)findViewById(R.id.login_username);
		password=(EditText)findViewById(R.id.login_password);
		loginButton.setOnClickListener(this);
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
		Intent intent=new Intent();
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.login_login_button:
			String user=name.getText().toString();
			String pd=password.getText().toString();

			User.getcUser().setUsername(user);
			User.getcUser().setPassword(pd);
			
			if(user.isEmpty()||pd.isEmpty()){
				Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
			}else if(!isExist(user)){
				Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
				password.setText("");
				name.setText("");
			}else if(!login(user, pd)){
				Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
				password.setText("");
			}else
			{
				finish();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.login_register_button:
			finish();
			intent.setClass(this,RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}

	public boolean login(String u,String p){
		db = database.getWritableDatabase();
		Cursor cursor = db.query("user", new String[]{"password"}, "username = ?",  new String[]{u}, null, null, null, null);
        if(cursor.moveToFirst()){ 
			
		    String password = 
		    		cursor.getString(cursor.getColumnIndex("password")); 
		    if(password.equals(p)){
		    	return true;
		    }
		}
		db.close();
		return false;
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

package com.ryg.expandable;

import java.util.ArrayList;
import java.util.List;

import com.ryg.expandable.R;
import com.ryg.expandable.ui.PinnedHeaderExpandableListView;
import com.ryg.expandable.ui.StickyLayout;
import com.ryg.expandable.ui.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.ryg.expandable.ui.StickyLayout.OnGiveUpTouchEventListener;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
public class MainActivity extends Activity implements
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        OnHeaderUpdateListener, OnGiveUpTouchEventListener {
    private PinnedHeaderExpandableListView expandableListView;
    private StickyLayout stickyLayout;
    private ArrayList<Group> groupList;
    private ArrayList<List<Account>> childList;
	private SlidingMenu menu; 
    private MyexpandableListAdapter adapter;
    private DBHelper database = new DBHelper(this);	
	private SQLiteDatabase db = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
        stickyLayout = (StickyLayout)findViewById(R.id.sticky_layout);
        initData();
        
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setBehindWidth(500);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.slidemenu);
		TextView exit=(TextView)findViewById(R.id.exit);
	        exit.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        		builder.setIcon(R.drawable.alert_dialog_icon);
	        		builder.setTitle("确定退出？");
	        		builder.setMessage("狠心离开吗?");
	        		builder.setPositiveButton("Yes",
	        				new DialogInterface.OnClickListener() 
	        			{
	        					public void onClick(DialogInterface dialog, int whichButton) 
	        					{
	        						System.exit(0);
	        					}
		        		});
	        		
	        		builder.setNegativeButton("No",
	        				new DialogInterface.OnClickListener() 
	        				{
	        					public void onClick(DialogInterface dialog, int whichButton)
	        					{
	        						
	        					}
	        				});
	        		builder.show();
	        		
	        	}
	        	
	        });
	        TextView about=(TextView)findViewById(R.id.about);
	        about.setOnClickListener(new View.OnClickListener(){
	        
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(MainActivity.this,AboutActivity.class);
					//intent.setClass(MainActivity.this,Publish.class);
  				MainActivity.this.startActivity(intent);
					
				}
	        });
	        TextView addview=(TextView)findViewById(R.id.add);
	        addview.setOnClickListener(new View.OnClickListener(){
	        
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(MainActivity.this,AddItemActivity.class);
					//intent.setClass(MainActivity.this,Publish.class);
  				MainActivity.this.startActivity(intent);
					
				}
	        });
	        
        adapter = new MyexpandableListAdapter(this);
        expandableListView.setAdapter(adapter);

        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }

        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        stickyLayout.setOnGiveUpTouchEventListener(this);

    }

    /***
     * InitData
     */
    void initData() {
        groupList = new ArrayList<Group>();
        Group group = null;
            group = new Group();
            group.setTitle("社交");
            groupList.add(group);
            group = new Group();
            group.setTitle("游戏");
            groupList.add(group);
            group = new Group();
            group.setTitle("论坛");
            groupList.add(group);
   
        childList = new ArrayList<List<Account>>();
        ArrayList<Account> childTemp=getAccounts(User.getcUser().getUsername());
        ArrayList<Account> childTemps = new ArrayList<Account>();
        ArrayList<Account> childTempy = new ArrayList<Account>();
        ArrayList<Account> childTempl = new ArrayList<Account>();
        for (Account account : childTemp) {
			if(account.getType().equals("社交")){
				childTemps.add(account);
			}else  if(account.getType().equals("游戏")){
				childTempy.add(account);
			}else{
				childTempl.add(account);
			}
		}
        childList.add(childTemps);
        childList.add(childTempy);
        childList.add(childTempl);
    }

    /***
     * 数据源
     * 
     * @author Administrator
     * 
     */
    class MyexpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MyexpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group, null);
                groupHolder.textView = (TextView) convertView
                        .findViewById(R.id.group);
                groupHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(( (Group) getGroup(groupPosition))
                    .getTitle());
            if (isExpanded)// ture is Expanded or false is not isExpanded
                groupHolder.imageView.setImageResource(R.drawable.expanded);
            else
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.child, null);

                childHolder.textid = (TextView) convertView
                        .findViewById(R.id.id);
                childHolder.textname = (TextView) convertView
                        .findViewById(R.id.name);
                childHolder.textdesc = (TextView) convertView
                        .findViewById(R.id.desc);
                childHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
               /* Button button = (Button) convertView
                        .findViewById(R.id.button1);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "clicked pos=", Toast.LENGTH_SHORT).show();
                    }
                });*/

                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.textid.setText(((Account) getChild(groupPosition,
                    childPosition)).getId());
            childHolder.textname.setText(String.valueOf(((Account) getChild(
                    groupPosition, childPosition)).getName()));
            childHolder.textdesc.setText(((Account) getChild(groupPosition,
                    childPosition)).getAccount());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v,
            int groupPosition, final long id) {

        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            final int groupPosition, final int childPosition, long id) {
        
        		final EditText text=new EditText(this);
                AlertDialog.Builder mibaoBuilder = new AlertDialog.Builder(MainActivity.this);
        		mibaoBuilder.setIcon(R.drawable.alert_dialog_icon);
        		mibaoBuilder.setTitle("密保问题");
        		mibaoBuilder.setMessage(childList.get(groupPosition).get(childPosition).getMibao());
        		mibaoBuilder.setView(text);
        		mibaoBuilder.setPositiveButton("Yes",
        				new DialogInterface.OnClickListener() 
        			{
        					public void onClick(DialogInterface dialog, int whichButton) 
        					{
        						if(text.getText().toString().equals(childList.get(groupPosition).get(childPosition).getAnswer())){
        							Intent intent=new Intent(MainActivity.this,ItemActivity.class);
        							intent.putExtra("id", childList.get(groupPosition).get(childPosition).getId());
            						finish();
            						startActivity(intent);
        						}else{
        							Toast.makeText(getApplicationContext(), "密保答案错误", Toast.LENGTH_SHORT).show();
        						}
        						
        					}
	        		});
        		
        		mibaoBuilder.setNegativeButton("No",
        				new DialogInterface.OnClickListener() 
        				{
        					public void onClick(DialogInterface dialog, int whichButton)
        					{
        						
        					}
        				});
        		mibaoBuilder.show();
        return false;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textid;
        TextView textname;
        TextView textdesc;
        ImageView imageView;
    }

    @Override
    public View getPinnedHeader() {
        View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        Group firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (expandableListView.getFirstVisiblePosition() == 0) {
            View view = expandableListView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }
   
    public int getCount(){
		db = database.getWritableDatabase();
		Cursor cursor = db.query("accounts", new String[]{"id"}, null,  null, null, null, null, null);
        
        int num=cursor.getCount();
		db.close();
		return num;
	}
    public ArrayList<Account> getAccounts(String user){
		ArrayList<Account> accounts=new ArrayList<Account>();
		db = database.getWritableDatabase();
		Cursor cursor = db.query("accounts", new String[]{"id,name,description,type,account,password,mibao,answer"}, "user=?", new String[]{user}, null, null, null, null);
		while (cursor.moveToNext()) {
			 String id = cursor.getString(cursor.getColumnIndex("id"));
			 String name = cursor.getString(cursor.getColumnIndex("name")); 
			 String description = cursor.getString(cursor.getColumnIndex("description")); 
			 String type = cursor.getString(cursor.getColumnIndex("type")); 
			 String account = cursor.getString(cursor.getColumnIndex("account")); 
			 String password = cursor.getString(cursor.getColumnIndex("password")); 
			 String mibao = cursor.getString(cursor.getColumnIndex("mibao")); 
			 String answer = cursor.getString(cursor.getColumnIndex("answer"));
	         accounts.add(new Account(id,name,description,type,account,password,mibao,answer,user));
		}
		cursor.close();
		db.close();

		return accounts;
	}

}

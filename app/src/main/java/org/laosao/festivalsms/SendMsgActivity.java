package org.laosao.festivalsms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.laosao.festivalsms.bean.Festival;
import org.laosao.festivalsms.bean.FestivalLab;
import org.laosao.festivalsms.bean.Msg;
import org.laosao.festivalsms.bean.SendedMsg;
import org.laosao.festivalsms.biz.SmsBiz;

import java.util.HashSet;


public class SendMsgActivity extends AppCompatActivity {
	public static final String KEY_FESTIVAL_ID = "festivalId";
	public static final String KEY_MSG_ID = "msgId";
	
	private EditText etContent;
	private Button btnAdd;
	private FlowLayout flContacts;
	private FloatingActionButton fabSend;
	private FrameLayout flLoadingLayout;
	
	private int festivalId;
	private int msgId;
	
	private Festival mFestival;
	private Msg mMsg;
	
	private static final int CODE_REQUEST = 0x110;
	
	private HashSet<String> mContactName = new HashSet<>();
	private HashSet<String> mContactNums = new HashSet<>();
	
	private LayoutInflater mInflater;
	
	public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
	public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";
	
	private PendingIntent mSendPi;
	private PendingIntent mDeliverPi;
	private BroadcastReceiver mSendBroadcastReceiver;
	private BroadcastReceiver mDeiverBroadcastReceiver;
	
	private SmsBiz smsBiz;
	private int mMsgCount;
	private int mTotalCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_send_msg);
		
		mInflater = LayoutInflater.from(this);
		smsBiz = new SmsBiz(this);


		initDatas();
		initViews();
		initEvents();
		initReceivers();
	}
	
	private void initReceivers() {
		Intent sendIntent = new Intent(ACTION_SEND_MSG);
		mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
		Intent deliverIntent = new Intent(ACTION_SEND_MSG);
		mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

		registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				mMsgCount++;
				if (getResultCode() == RESULT_OK) {
					Log.e("TAG", "短信发送成功" + (mMsgCount + "/" + mTotalCount));
				} else {
					Log.e("TAG", "短信发送失败");
				}
				Toast.makeText(SendMsgActivity.this,
							  (mMsgCount + "/" + mTotalCount) + "短信发送成功", Toast.LENGTH_SHORT)
				.show();
				if (mMsgCount == mTotalCount) {
					finish();
				}
			}
		}, new IntentFilter(ACTION_SEND_MSG));
		
		registerReceiver(mDeiverBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.e("TAG", "联系人已经成功接收到我们的短信");
			}
		}, new IntentFilter(ACTION_DELIVER_MSG));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mSendBroadcastReceiver);
		unregisterReceiver(mDeiverBroadcastReceiver);
		
	}
	
	private void initEvents() {
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,
										  ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, CODE_REQUEST);
			}
		});
		
		fabSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContactNums.size() == 0) {
					Toast.makeText(SendMsgActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
					return;
				}
				String msg = etContent.getText().toString();
				if (TextUtils.isEmpty(msg)) {
					Toast.makeText(SendMsgActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				mTotalCount = smsBiz.sendMsg(mContactNums, buildSendMsg(msg),
											mSendPi, mDeliverPi);
				mMsgCount = 0;
				flLoadingLayout.setVisibility(View.VISIBLE);
			}
		});
	}

	private SendedMsg buildSendMsg(String msg) {
		SendedMsg sendedMsg = new SendedMsg();
		sendedMsg.setMsg(msg);
		sendedMsg.setFestivalName(mFestival.getName());
		String names = "";
		for (String name : mContactName) {
			names += name + ":";
		}
		sendedMsg.setNames(names.substring(0, names.length() - 1));
		String numbers = "";
		for (String number : mContactNums) {
			numbers += number + ":";
		}
		sendedMsg.setNumbers(numbers.substring(0, numbers.length() - 1));
		return sendedMsg;
	}
	
	private void initDatas() {
		festivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID, -1);
		msgId = getIntent().getIntExtra(KEY_MSG_ID, -1);
		mFestival = FestivalLab.getmInstance().getFestivalById(festivalId);
		setTitle(mFestival.getName());
	}
	
	public static void toActivity(Context context,
								  int festivalId,
								  int msgId) {
		Intent intent = new Intent(context, SendMsgActivity.class);
		intent.putExtra(KEY_FESTIVAL_ID, festivalId);
		intent.putExtra(KEY_MSG_ID, msgId);
		Log.d("msgId", msgId + "");
		context.startActivity(intent);
	}
	
	
	private void initViews() {
		etContent = (EditText) findViewById(R.id.etContent);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		flContacts = (FlowLayout) findViewById(R.id.flContacts);
		fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
		flLoadingLayout = (FrameLayout) findViewById(R.id.flLoadingLayout);
		
		if (msgId != -1) {
			mMsg = FestivalLab.getmInstance().getMsgByMsgId(msgId);
			etContent.setText(mMsg.getContent());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE_REQUEST) {
			if (resultCode == RESULT_OK) {
				Uri contactUri = data.getData();
				Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
				cursor.moveToFirst();
				String contactName = cursor.getString(
													 cursor.getColumnIndex(
																		  ContactsContract.Contacts.DISPLAY_NAME));
				String number = getContactNumber(cursor);
				if (!TextUtils.isEmpty(number)) {
					mContactName.add(contactName);
					mContactNums.add(number);
					
					addTag(contactName);
				}
			}
		}
	}
	
	private void addTag(String contactName) {
		TextView tv = (TextView) mInflater.inflate(R.layout.tag, flContacts, false);
		tv.setText(contactName);
		flContacts.addView(tv);
	}
	
	private String getContactNumber(Cursor cursor) {
		int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
		String number = null;
		if (numberCount > 0) {
			int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
														   null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
														   null, null);
			phoneCursor.moveToFirst();
			number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			phoneCursor.close();
		}
		cursor.close();
		return number;
	}
}

package org.laosao.festivalsms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import org.laosao.festivalsms.bean.SendedMsg;
import org.laosao.festivalsms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * @version 1.0
 * @components: SmsBiz
 * @author：赵树豪（Scout）
 * @create date：2015/10/24 9:33
 * @modified by：赵树豪
 * @dodified date：2015/10/24 9:33
 * @why ：
 */
public class SmsBiz {

	private Context context;

	public SmsBiz(Context context) {
		this.context = context;
	}
	
	public int sendMsg(String number, String msg, PendingIntent sendPi, PendingIntent deliver) {
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> contents = smsManager.divideMessage(msg);
		for (String content : contents) {
			smsManager.sendTextMessage(number, null, content, sendPi, deliver);
		}
		return contents.size();
	}
	
	public int sendMsg(Set<String> numbers, SendedMsg msg, PendingIntent sendPi, PendingIntent deliver) {
		save(msg);
		int result = 0;
		for (String number : numbers) {
			int count = sendMsg(number, msg.getMsg(), sendPi, deliver);
			result += count;
		}
		return result;
	}


	private void save(SendedMsg sendedMsg) {
		sendedMsg.setDate(new Date());
		ContentValues values = new ContentValues();
		values.put(SendedMsg.COLUMN_MSG, sendedMsg.getMsg());
		values.put(SendedMsg.COLUMN_FESTIVAL_NAME, sendedMsg.getFestivalName());
		values.put(SendedMsg.COLUMN_DATE, sendedMsg.getDate().getTime());
		values.put(SendedMsg.COLUMN_NAME, sendedMsg.getNames());
		values.put(SendedMsg.COLUMN_NUMBER, sendedMsg.getNumbers());

		context.getContentResolver().insert(SmsProvider.URI_SMS_ALL, values);
	}
	
}

package org.laosao.festivalsms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.laosao.festivalsms.bean.SendedMsg;

/**
 * @version 1.0
 * @components: SmsDbOpenHelper
 * @author：赵树豪（Scout）
 * @create date：2015/10/25 21:01
 * @modified by：赵树豪
 * @dodified date：2015/10/25 21:01
 * @why ：
 */
public class SmsDbOpenHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "sms.db";
	private static final int DB_VERSION = 1;

	private static SmsDbOpenHelper mHelper;

	public static SmsDbOpenHelper getInstance(Context context) {
		if (mHelper == null) {
			synchronized (SmsDbOpenHelper.class) {
				if (mHelper == null) {
					mHelper = new SmsDbOpenHelper(context);
				}
			}
		}

		return mHelper;
	}

	private SmsDbOpenHelper(Context context) {
		super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + SendedMsg.TABLE_NAME + " ( " +
					 " _id integer primary key autoincrement , " +
					 SendedMsg.COLUMN_MSG + " text , " +
					 SendedMsg.COLUMN_NUMBER + " text , " +
					 SendedMsg.COLUMN_NAME + " text , " +
					 SendedMsg.COLUMN_FESTIVAL_NAME + " text , " +
					 SendedMsg.COLUMN_DATE + " text " + " ) ";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}

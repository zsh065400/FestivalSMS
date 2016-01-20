package org.laosao.festivalsms.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @components: SendedMsg
 * @author：赵树豪（Scout）
 * @create date：2015/10/25 20:34
 * @modified by：赵树豪
 * @dodified date：2015/10/25 20:34
 * @why ：
 */
public class SendedMsg {
	private int id;
	private String msg;
	private String numbers;
	private String names;
	private String festivalName;
	private Date date;
	private String dateStr;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String TABLE_NAME = "tb_sended_msg";
	public static final String COLUMN_MSG = "msg";
	public static final String COLUMN_NUMBER = "number";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_FESTIVAL_NAME = "festivalName";
	public static final String COLUMN_DATE = "date";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		dateStr = dateFormat.format(date);
		return dateStr;
	}


	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
}

package org.laosao.festivalsms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @components: FestivalLab
 * @author：赵树豪（Scout）
 * @create date：2015/10/19 21:06
 * @modified by：赵树豪
 * @dodified date：2015/10/19 21:06
 * @why ：
 * <p/>
 * 节日管理类
 */
public class FestivalLab {

	public static FestivalLab mInstance;

	private List<Festival> mFestival = new ArrayList<>();

	private List<Msg> mMsgs = new ArrayList<>();

	private FestivalLab() {
		mFestival.add(new Festival(1, "国庆节"));
		mFestival.add(new Festival(2, "中秋节"));
		mFestival.add(new Festival(3, "元旦"));
		mFestival.add(new Festival(4, "春节"));
		mFestival.add(new Festival(5, "端午节"));
		mFestival.add(new Festival(6, "七夕节"));
		mFestival.add(new Festival(7, "圣诞节"));
		mFestival.add(new Festival(8, "儿童节"));

		mMsgs.add(new Msg(1, 1, "国庆节快乐！！！！！"));
		mMsgs.add(new Msg(2, 2, "中秋节快乐！！！！！"));
		mMsgs.add(new Msg(3, 3, "元旦快乐！！！！！"));
		mMsgs.add(new Msg(4, 4, "春节快乐！！！！！"));
		mMsgs.add(new Msg(5, 5, "端午节快乐！！！！！"));
		mMsgs.add(new Msg(6, 6, "七夕节快乐！！！！！"));
		mMsgs.add(new Msg(7, 7, "圣诞节快乐！！！！！"));
		mMsgs.add(new Msg(8, 8, "儿童节快乐！！！！！"));
	}


	/**
	 * 模拟数据库，提供根据条件查询内容的方法
	 *
	 * @param fesId
	 * @return
	 */
	public List<Msg> getMsgByFestivalId(int fesId) {
		List<Msg> msgs = new ArrayList<>();
		for (Msg msg : mMsgs) {
			if (msg.getFestivalId() == fesId) {
				msgs.add(msg);
			}
		}

		return msgs;
	}


	public Msg getMsgByMsgId(int msgId) {
		// TODO: 2015/10/27 多个节日的msg的id可能相同
		for (Msg msg : mMsgs) {
			if (msg.getId() == msgId) {
				try {
					return (Msg) msg.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public List<Festival> getFestivals() {
		return new ArrayList<>(mFestival);
	}

	public Festival getFestivalById(int resId) {

		for (Festival festival : mFestival) {
			if (festival.getId() == resId) {
				return festival;
			}
		}

		return null;
	}


	/**
	 * 单例模式
	 *
	 * @return
	 */
	public static FestivalLab getmInstance() {
		if (mInstance == null) {
			synchronized (FestivalLab.class) {
				if (mInstance == null) {
					mInstance = new FestivalLab();
				}
			}
		}

		return mInstance;
	}
}

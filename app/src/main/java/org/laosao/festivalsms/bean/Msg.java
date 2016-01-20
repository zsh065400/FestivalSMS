package org.laosao.festivalsms.bean;

/**
 * @version 1.0
 * @components: Msg
 * @author：赵树豪（Scout）
 * @create date：2015/10/20 18:23
 * @modified by：赵树豪
 * @dodified date：2015/10/20 18:23
 * @why ：
 */
public class Msg {

	private int id;
	private int festivalId;
	private String content;

	public Msg(int id, int festivalId, String content) {
		this.id = id;
		this.festivalId = festivalId;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFestivalId() {
		return festivalId;
	}

	public void setFestivalId(int festivalId) {
		this.festivalId = festivalId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Msg msg = new Msg(this.id, this.festivalId, this.content);
		return msg;
	}
}

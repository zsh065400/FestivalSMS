package org.laosao.festivalsms.bean;

import java.util.Date;

/**
 * @version 1.0
 * @components: Festival
 * @author：赵树豪（Scout）
 * @create date：2015/10/19 21:04
 * @modified by：赵树豪
 * @dodified date：2015/10/19 21:04
 * @why ：
 * <p/>
 * <p/>
 * 数据模拟类，实际运行一般以服务器返回数据为准
 */
public class Festival {

	private int id;
	private String name;

	public Festival(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String desc;
	private Date date;
}

package com.ulewo.vo;

import java.util.List;

import com.ulewo.model.Topic;
import com.ulewo.model.Group;

public class IndexArticleVo {
	private Group group;
	
	private List<Topic> list;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Topic> getList() {
		return list;
	}

	public void setList(List<Topic> list) {
		this.list = list;
	}
}

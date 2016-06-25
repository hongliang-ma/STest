package com.dagger.dtest.enums;

/**
 * 
 * 设置测试优先级
 * 
 * @author 马洪良
 * @version $Id: PriorityLevel.java, v 0.1 2014年3月13日 下午1:24:35 马洪良 Exp $
 */
public enum PriorityLevel {

	HIGHEST(1),

	HIGH(2),

	NORMAL(3),

	LOW(4),

	LOWEST(5);

	private int priority;

	private PriorityLevel(int p) {
		this.priority = p;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return String.format("P%s", priority);
	}
}

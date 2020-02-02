package com.nrd.o2o.enums;

public enum ProductStateEnum {
	SUCCESS(1, "创建成功"),INNER_ERROR(-1001, "内部系统错误"),
	EMPTY_LIST(-1002,"添加数少于1"), EMPTY(-1003,"添加为空");

	private int state;
	private String stateInfo;

	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * 
	 */
	public static ProductStateEnum stateOf(int state) {
		for (ProductStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}

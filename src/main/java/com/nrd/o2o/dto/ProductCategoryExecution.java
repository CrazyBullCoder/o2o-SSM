package com.nrd.o2o.dto;

import java.util.List;

import com.nrd.o2o.entity.ProductCategory;
import com.nrd.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	private int state;
	private String stateInfo;
	private List<ProductCategory> productCategories;

	public ProductCategoryExecution() {
	}

	// 操作失败
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 操作成功
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategories) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategories = productCategories;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}
}

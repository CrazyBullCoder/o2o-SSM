package com.nrd.o2o.service;

import java.util.List;

import com.nrd.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 根据查询条件获取店铺分类列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}

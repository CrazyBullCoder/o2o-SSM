package com.nrd.o2o.dao;

import java.util.List;

import com.nrd.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 批量添加商品详情图片
	 * 
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 根据商品Id删除商品详情图
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(Long productId);
	/**
	 * 查询某一商品的所有详情图片
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(Long productId);
}

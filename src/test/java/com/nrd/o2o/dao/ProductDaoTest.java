package com.nrd.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nrd.o2o.BaseTest;
import com.nrd.o2o.entity.Product;
import com.nrd.o2o.entity.ProductCategory;
import com.nrd.o2o.entity.ProductImg;
import com.nrd.o2o.entity.Shop;

public class ProductDaoTest extends BaseTest {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testInsertProduct() {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);

		Product product1 = new Product();
		product1.setProductName("test1");
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		product1.setPriority(60);
		product1.setEnableStatus(1);
		Product product2 = new Product();
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		product2.setProductName("test2");
		Product product3 = new Product();
		product3.setProductName("test3");
		product3.setShop(shop1);
		product3.setProductCategory(pc1);
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testQueryProductByProductId() {
		ProductImg productImg1 = new ProductImg();
		productImg1.setCreateTime(new Date());
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setCreateTime(new Date());
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(2);
		productImg2.setProductImgId(1L);
		List<ProductImg> productImgs = new ArrayList<>();
		productImgs.add(productImg1);
		productImgs.add(productImg2);
		int effectNum = productImgDao.batchInsertProductImg(productImgs);
		Product product = productDao.queryProductById(1L);
		System.out.println();
	}

	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(31L);
		productCategory.setProductCategoryId(21L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductName("the second product");
		product.setProductCategory(productCategory);
		int effectNum = productDao.updateProduct(product);
		assertEquals(1, effectNum);
	}
}

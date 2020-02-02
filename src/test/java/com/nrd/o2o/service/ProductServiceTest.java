package com.nrd.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nrd.o2o.BaseTest;
import com.nrd.o2o.dto.ImageHolder;
import com.nrd.o2o.dto.ProductExecution;
import com.nrd.o2o.entity.Product;
import com.nrd.o2o.entity.ProductCategory;
import com.nrd.o2o.entity.Shop;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	@Test
	public void testAddProduct() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1描述");
		product.setPriority(11);
		product.setEnableStatus(1);
		product.setLastEditTime(new Date());
		product.setCreateTime(new Date());
		File thumbnailFile = new File("E:\\Pictures\\1.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		File procuctImg1 = new File("E:\\Pictures\\1.jpg");
		InputStream is1 = new FileInputStream(procuctImg1);
		File procuctImg2 = new File("E:\\Pictures\\2.jpg");
		InputStream is2 = new FileInputStream(procuctImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(procuctImg1.getName(), is1));
		productImgList.add(new ImageHolder(procuctImg2.getName(), is2));
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(1, pe.getState());
		System.out.println(pe.getStateInfo());
	}

	@Test
	public void testModifyProduct() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setProductName("product");
		product.setProductDesc("product");
		String pathname = "E:\\Pictures\\2.jpg";
		String pathname2 = "E:\\Pictures\\1.jpg";
		//缩略图
		File thumbnailFile = new File(pathname);
		InputStream inputStream = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
		// 详情图
		File productImg1 = new File(pathname);
		InputStream is1 = new FileInputStream(productImg1);

		File productImg2 = new File(pathname2);
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(1, pe.getState());
	}
}

package com.nrd.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrd.o2o.dao.ProductDao;
import com.nrd.o2o.dao.ProductImgDao;
import com.nrd.o2o.dto.ImageHolder;
import com.nrd.o2o.dto.ProductExecution;
import com.nrd.o2o.entity.Product;
import com.nrd.o2o.entity.ProductImg;
import com.nrd.o2o.enums.ProductStateEnum;
import com.nrd.o2o.exceptions.ProductOperationException;
import com.nrd.o2o.service.ProductService;
import com.nrd.o2o.utils.ImageUtil;
import com.nrd.o2o.utils.PageCalculator;
import com.nrd.o2o.utils.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional
	// 1、处理缩略图，获取缩略图相对路径并赋值给product
	// 2、往tb_product写入商品信息，获取productId
	// 3、结合productId批量处理商品详细图
	// 4、将商品详情图列表批量插入tb_product_img中
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认上架状态
			product.setEnableStatus(1);
			// 若商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			// 创建商品信息
			try {
				int effectNum = productDao.insertProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败" + e.toString());
			}
			// 若商品详情图列表不为空则添加
			if (productImgList != null && !productImgList.isEmpty()) {
				addProductImgList(product, productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// 参数为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 批量添加图片
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailImgAddr);
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param productImgList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgList) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgs = new ArrayList<>();
		// 遍历商品详情图，并添加到productImg中
		for (ImageHolder productImgHolder : productImgList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setProductId(product.getProductId());
			productImg.setImgAddr(imgAddr);
			productImg.setCreateTime(new Date());
			productImgs.add(productImg);
		}

		// 存入有图片，就执行批量添加操作
		if (productImgs.size() > 0) {
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgs);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	// 1.若缩略图参数有值,则处理缩略图
	// 若原先存在缩略图则先删除,再获取缩略图相对路径添加到Product对象中
	// 2.若详情图列表有值,则对详情图列表进行同样的操作
	// 3.将tb_product_img下面的该商品原先的详情图记录清除
	// 4.更新th_product的信息
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 设置默认更新时间
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空，则先删除原有缩略图并添加
			if (thumbnail != null) {
				// 先获取原有信息，得到原有图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}

			// 若存在新的商品详情图且原详情图不为空，则先删除原有详情图并添加
			if (productImgList != null && !productImgList.isEmpty()) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgList);
			}

			// 更新商品信息
			try {
				int effectNum = productDao.updateProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (ProductOperationException e) {
				throw new ProductOperationException("更新商品信息失败:" + e.getMessage());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void deleteProductImgList(Long productId) {
		// 根据productId获取原有的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		if (productImgList != null && !productImgList.isEmpty()) {
			for (ProductImg productImg : productImgList) {
				// 删除存的图片
				ImageUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			// 删除数据库中图片
			productImgDao.deleteProductImgByProductId(productId);
		}
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 将页码转换为数据库的行数
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 获取商品列表分页信息
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		// 获取商品总数
		int productCount = productDao.queryProductCount(productCondition);
		// 构建返回对象,并设值
		ProductExecution productExecution = new ProductExecution();
		productExecution.setCount(productCount);
		productExecution.setProductList(productList);
		return productExecution;
	}

}

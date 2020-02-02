package com.nrd.o2o.utils;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "E:/Pictures";
		} else {
			basePath = "/home/work/nrd";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	/**
	 * 图片的相对路径
	 * 
	 * @param shopId 商铺id
	 * @return "/upload/item/shop/" + shopId + "/"
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
}

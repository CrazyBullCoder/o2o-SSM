package com.nrd.o2o.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nrd.o2o.web.superadmin.AreaController;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	/**
	 * 处理缩略图,返回新生成图片的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:"+ relativeAddr);
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is:" + PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(160, 160)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.5f)
					.outputQuality(0.8).toFile(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及的目录
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件的拓展名
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		return fileExtension;
	}

	/**
	 * 生成随机文件名,当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		// 获取随机五位数
		int randomNum = r.nextInt(89999) + 10000;
		String nowTimeString = simpleDateFormat.format(new Date());
		return nowTimeString + randomNum;
	}

	public static void main(String[] args) throws IOException {
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Thumbnails.of(new File("E:\\Pictures\\1.jpg")).size(160, 160)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "\\watermark.jpg")), 0.5f)
				.outputQuality(0.8).toFile(new File("E:\\Pictures\\image-with-watermark.jpg"));
	}
}

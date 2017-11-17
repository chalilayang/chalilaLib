package com.chalilayang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.logging.LogFactory;

public class Md5Util {
	
//	private static Log logger = LogFactory.getLog(Md5Util.class);

	/**
	 * 取得文件的md5
	 * 
//	 * @param file
	 *            String
	 * @return String
	 */
	public static String getFileMd5(String filePath) {
		File file = new File(filePath);
//		logger.info("filePath:" + filePath);
		return getMd5ByFile(file);
	}

	public static String getMd5ByFile(File file) {
		if (file == null)
			return "bad file";
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			return bytesToString(md.digest());
		} catch (IOException ex) {
//			logger.error("error:", ex);
			return null;
		} catch (NoSuchAlgorithmException ex) {
//			logger.error("error:", ex);
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
//				logger.error("error:", ex);
			}
		}

	}

	public static String bytesToString(byte[] data) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char[] temp = new char[data.length * 2];
		for (int i = 0; i < data.length; i++) {
			byte b = data[i];
			temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
			temp[i * 2 + 1] = hexDigits[b & 0x0f];
		}
		return new String(temp);
	}

	public static void main(String[] args) {
		// System.out.println(FileUtil.getAbsoluteDir("a/b\\c"));
		// System.out.println(FileUtil.getPath("/data1", "abd/de"));

		String path = "\\opt\\webupload\\tmp1_6655581_2011_6_27_19_16_1318c23085f.flv";
		String ss = getFileMd5(path);
		System.out.println(ss);
	}

}

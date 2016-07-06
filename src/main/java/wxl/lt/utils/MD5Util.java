/**
 * 
 */
package wxl.lt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wxlHonest
 *
 */
public class MD5Util {

	public static void main(String[] args) {
		String s = "admin";
		System.out.println(md5(s));
	}

	/**
	 * md5 encryption md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (byte element : byteDigest) {
				i = element;
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			// 32 bit encryption 32位加密
			return buf.toString();
			// 16 32 bit encryption 16位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String md5For16(String str) {
		return md5(str).toString().substring(8, 24);
	}
}

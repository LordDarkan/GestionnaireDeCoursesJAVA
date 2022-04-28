package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	public static boolean lexDev(String name) {
		boolean result = false;
		if (name!= null) {
			String hexString = byteToHex(md5DigestJava(name));
			result = hexString.equals("1b0f97699a95b8008da939f5e3939425");
		}
		return result;
	}
	
	private static byte[] md5DigestJava(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(message.getBytes());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String byteToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
	}
	
	public static boolean isDelAppOk() {
		return true;
	}
	
	public static boolean isAnnulationOk() {
		return false;
	}
}
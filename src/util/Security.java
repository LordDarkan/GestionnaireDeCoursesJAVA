package util;

public class Security {
	public static boolean lexDev(String name) {
		return name!= null && name.equals("LexDev");//TODO MD5
	}
}
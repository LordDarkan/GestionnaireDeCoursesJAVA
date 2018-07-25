package util;

public class Security {
	public static Role getlexDevRole() {
		return Role.ADMIN;
	}

	public static boolean lexDev(String name) {
		return name!= null && name.equals("LexDev");//TODO MD5
	}
}
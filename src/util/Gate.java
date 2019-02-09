package util;

public class Gate {
	//TODO en annotation???
	
	public static String encoding(String encoding){
		String result = "";
		if (encoding != null) {
			result = encoding.replace(";", ",");
		}
		return result;
	}
}

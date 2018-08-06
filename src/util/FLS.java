package util;

import java.util.LinkedList;
import java.util.List;

public class FLS {
	private static final String spliter = "/";
	
	public static List<Long> toList(String allInOne) {
		List<Long> all = new LinkedList<Long>();
		Long lVal;
		for (String strVal : allInOne.split(spliter)) {
			try {
				lVal = Long.parseLong(strVal.trim());
				all.add(lVal);
			} catch (Exception e) {}
		}
		return all;
	}
	
	public static String toString(List<Long> all) {
		boolean first = false;
		StringBuilder allInOne = new StringBuilder();
		for (Long lVal : all) {
			if (!first)
				allInOne.append(spliter);
			allInOne.append(lVal);
			first = true;
		}
		return allInOne.toString();
	}
	
	public static String addToString(String allInOne, Long val) {
		String result;
		if (allInOne.isEmpty()) {
			result = val.toString();
		} else {
			result = allInOne+spliter+val.toString();
		}
		return result;
	}

	public static String removeToString(String allInOne, Long id) {
		List<Long> all = toList(allInOne);
		all.remove(id);
		return toString(all);
	}
}

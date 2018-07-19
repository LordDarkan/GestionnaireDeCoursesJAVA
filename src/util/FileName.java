package util;

import java.util.GregorianCalendar;

public class FileName {
	
	public static String getNameLogFile() {
		GregorianCalendar c = new GregorianCalendar();
		int year = c.get(GregorianCalendar.YEAR);
		int month = c.get(GregorianCalendar.MONTH);
		StringBuilder name = new StringBuilder("Log");
		name.append(year%100);
		if(month<10)
			name.append(0);
		name.append(month);
		switch (month) {
		case 1:
			name.append(" Janvier ");
			break;
		case 2:
			name.append(" Février ");
			break;
		case 3:
			name.append(" Mars ");
			break;
		case 4:
			name.append(" Avril ");
			break;
		case 5:
			name.append(" Mai ");
			break;
		case 6:
			name.append(" Juin ");
			break;
		case 7:
			name.append(" Juillet ");
			break;
		case 8:
			name.append(" Août ");
			break;
		case 9:
			name.append(" Septembre ");
			break;
		case 10:
			name.append(" Octobre ");
			break;
		case 11:
			name.append(" Novembre ");
			break;
		case 12:
			name.append(" Décembre ");
			break;

		default:
			break;
		}
		name.append(year);
		return name.toString();
	}

}

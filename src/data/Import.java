package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import models.Appelant;

public class Import {
	
	public static List<Appelant> oldAppelants(File file) {//24.03.1949
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		List<Appelant> appelants = new LinkedList<Appelant>();
		Appelant appelant;
		BufferedReader br = null;
        String line = "";
        String[] row;
        String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                row = line.split(cvsSplitBy);
                if (row[0].trim().matches("\\p{Digit}+")) {
                	appelant = new Appelant();
                	try {
                    	appelant.setId(Long.parseLong(row[0].trim()));
					} catch (Exception e) {
						//e.printStackTrace();
						System.out.println(row[0].trim());
					}
                	appelant.setName(row[2].trim());
                	appelant.setFirstname(row[3].trim());
                	appelant.setAdresse(row[8].trim());
                	appelant.setCp(row[9].trim());
                	appelant.setLocalite(row[10].trim());
                	appelant.setTel(row[11].trim());
                	try {
						appelant.setBirthday(format.parse(row[12].trim()));
					} catch (ParseException e) {
						//e.printStackTrace();
					}
                	appelant.setInfos(row[13].trim());
                	appelant.setMutualite(row[14].trim());
                	try {
                    	appelant.setCotisation(Integer.parseInt(row[15].trim()));
					} catch (Exception e) {
						//e.printStackTrace();
					}
                	System.out.println("add "+row[0].trim());
                	appelants.add(appelant);
				}
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return appelants;
	}
}

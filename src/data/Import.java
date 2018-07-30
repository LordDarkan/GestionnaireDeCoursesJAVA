package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import models.Appelant;

public class Import {
	
	public static List<Appelant> old2Appelants(File file) {//24.03.1949
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		List<Appelant> appelants = new LinkedList<Appelant>();
		Appelant appelant;
		BufferedReader br = null;
        String line = "", next="";
        String[] row = null;
        String cvsSplitBy = ";";
        boolean ok = true;
        try {
        	br = new BufferedReader(new FileReader(file));
            br.readLine();//en tete
            line = br.readLine();
            int i = 0;
            while ((next = br.readLine()) != null && ok) {
            	
            	while (ok && !next.startsWith("VRAI")){
            		line = line + "\n" + next;
            		ok = (next = br.readLine()) != null;
            	}
            		
                row = line.split(cvsSplitBy);
                
                System.out.println("->"+row.length + "\n" + line);
                
                if (row[0].equals("VRAI")) {
                	appelant = new Appelant();
                	try {
                    	appelant.setId(Long.parseLong(row[1].trim()));
					} catch (Exception e) {
						//e.printStackTrace();
					}
                	appelant.setName(row[3].trim());
                	appelant.setFirstname(row[4].trim());
                	appelant.setAdresse(row[5].trim());
                	appelant.setCp(row[6].trim());
                	appelant.setLocalite(row[7].trim());
                	appelant.setQuartier(row[8].trim());
                	try {
						appelant.setBirthday(LocalDate.parse(row[9].trim(),format));
					} catch (Exception e) {
						//e.printStackTrace();
					}
                	appelant.setTel(row[10].trim());
                	appelant.setResidence(row[11].trim().isEmpty()?"NaN":row[11].trim());
                	appelant.setMutualite(row[15].trim());
                	appelant.setMobilite(row[16].trim());
                	//17 restriction
                	appelant.setPayement(row[18].trim());//18 payement
                	try {
                		if (row.length > 19)
                    		appelant.setCotisation(Integer.parseInt(row[19].trim()));
					} catch (Exception e) {
						//e.printStackTrace();
					}
                	if (row.length > 20)
                		appelant.setAideParticulière(row[20].trim());
                	if (row.length > 21)
                		appelant.setInfos(row[21].trim());
                	//22 afinité
                	if (row.length > 23)
                		appelant.setRemarques(row[23].trim());
                	
                	appelants.add(appelant);
                	System.out.println("add "+row[1].trim());
                	i++;
				}
                System.out.println("------------------------------");
                line = next;
            }
            System.out.println("--->"+i);
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
	
	public static List<Appelant> oldAppelants(File file) {//24.03.1949
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
						appelant.setBirthday(LocalDate.parse(row[12].trim(),format));
					} catch (Exception e) {
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

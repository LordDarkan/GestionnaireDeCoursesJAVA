package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import models.Appelant;
import models.Settings;
import util.DateTime;

public class SaveManager {
	private static final String extention = ".gdc.zip";
	
	public static String getExtention() {
		return extention;
	}
	
	public static void importSave(File file)
	{
		if (file.isFile() && file.getName().endsWith(extention)) {
			Mapper mapper = Mapper.getInstance();
			mapper.deleteAll();
			boolean res = false,hop = false,chauf = false,app = false,course = false;
			try(ZipFile zf = new ZipFile(file)){
				Enumeration<? extends ZipEntry> entries;
				ZipEntry zipentry;
				int limite = 4;
				while (!(res&&hop&&chauf&&app&&course) && limite>0) {
					limite++;
					entries = zf.entries();
					while (entries.hasMoreElements()) 
					{
						zipentry = entries.nextElement();
						String entryName = zipentry.getName();
						if (!res&& entryName.equals("Residence.csv")) {
							mapper.importResidences(CSV.readResidence(new InputStreamReader(zf.getInputStream(zipentry))));
							res = true;
						} else if (!hop&& entryName.equals("Hopital.csv")) {
							mapper.importHopitaux(CSV.readHopital(new InputStreamReader(zf.getInputStream(zipentry))));
							hop = true;
						} else if (!chauf&& entryName.equals("Chauffeur.csv")) {
							mapper.importChauffeurs(CSV.readChauffeur(new InputStreamReader(zf.getInputStream(zipentry))));
							chauf = true;
						} else if (!app&&chauf&& entryName.equals("Appelant.csv")) {
							mapper.importApplants(CSV.readAppelant(new InputStreamReader(zf.getInputStream(zipentry))));
							app = true;
						} else if (!course&&chauf&&app&& entryName.equals("Course.csv")) {
							mapper.importCourses(CSV.readCourse(new InputStreamReader(zf.getInputStream(zipentry)),mapper));
							course = true;
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public static void autoSave() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		LocalDate lastSave = settings.getLastSave();
		LocalDate time = LocalDate.now();
		if (lastSave.plusDays(settings.getSaveFrequency()).isBefore(time)) {
			String pathName = settings.getPathSaveDirectory()+System.getProperty("file.separator")+DateTime.getNameSave(time);
			creatSave(pathName, mapper);
			settings.save();
		}
	}
	
	public static void save() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		LocalDateTime time = LocalDateTime.now();
		String pathName = settings.getPathSaveDirectory()+System.getProperty("file.separator")+DateTime.getNameSave(time);
		creatSave(pathName, mapper);
		settings.save();
	}

	private static void creatSave(String pathName, Mapper mapper) {
		String separator = System.getProperty("file.separator");
		File save = new File(pathName);
		if (save.exists()) {
			for (File content : save.listFiles()) {
				content.delete();
			}
		} else {
			save.mkdirs();
		}
		
		File write = new File(save.getAbsolutePath()+separator+"Appelant.csv");
		CSV.wirteAppelant(mapper.getAllAppelant(), write);
		write = new File(save.getAbsolutePath()+separator+"Chauffeur.csv");
		CSV.wirteChauffeur(mapper.getAllChauffeur(), write);
		write = new File(save.getAbsolutePath()+separator+"Course.csv");
		CSV.wirteCourse(mapper.getAllCourse(), write);
		write = new File(save.getAbsolutePath()+separator+"Hopital.csv");
		CSV.wirteHopital(mapper.getListHopital(), write);
		write = new File(save.getAbsolutePath()+separator+"Residence.csv");
		CSV.wirteResidence(mapper.getListResidence(), write);
		
		
		compress(save.getAbsolutePath());
		for (File content : save.listFiles()) {
			content.delete();
		}
		save.delete();
	}
	
	private static void compress(String dirPath) {
        Path sourceDir = Paths.get(dirPath);
        String zipFileName = dirPath.concat(extention);
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
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
                	appelant.setResidence(row[11].trim().isEmpty()?"":row[11].trim());
                	try {
                    	appelant.addFamille(Long.parseLong(row[13].trim()));
					} catch (Exception e) {
						//e.printStackTrace();
					}
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

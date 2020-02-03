package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import application.Variable;
import models.Settings;
import util.DateTime;
import util.UserManager;

public class SaveManager {
	private static final int CURRENT_VERSION = 1;
	private static final String extention = ".gdc.zip";

	public static String getExtention() {
		return extention;
	}

	public static void importSave(File file) {
		if (file.isFile() && file.getName().endsWith(extention)) {
			try (ZipFile zf = new ZipFile(file)) {
				Enumeration<? extends ZipEntry> entries = zf.entries();
				ZipEntry zipentry;
				int version = 0;
				while (entries.hasMoreElements()) {
					zipentry = entries.nextElement();
					String entryName = zipentry.getName();
					if (entryName.equals("VERSION")) {
						try {
							version = Integer.parseInt(new BufferedReader(new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)).readLine().trim());
						} catch (Exception e) {
							version = -1;
						}
					}
				}
				switch (version) {
				case -1:throw new Exception("Erreur Version");
				case 0:version0(zf);break;
				default:version1(version,zf);break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private static void version1(int version, ZipFile zf) throws IOException {
		Enumeration<? extends ZipEntry> entries;
		ZipEntry zipentry;
		Mapper mapper = Mapper.getInstance();
		mapper.deleteAll();
		boolean res = false, hop = false, chauf = false, app = false, course = false, indisp = false, user = false;
		int limite = 4;
		while (!(user && res && hop && chauf && app && course && indisp) && limite > 0) {
			limite--;
			entries = zf.entries();
			while (entries.hasMoreElements()) {
				zipentry = entries.nextElement();
				String entryName = zipentry.getName();

				if (!user && entryName.equals("Utilisateur.csv")) {
					mapper.importUtilisateur(CSV.readUtilisateur(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252"),
							mapper));
					user = true;
				} else if (!res && entryName.equals("Residence.csv")) {
					mapper.importResidences(CSV.readResidence(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252")));
					res = true;
				} else if (!hop && entryName.equals("Hopital.csv")) {
					mapper.importHopitaux(CSV.readHopital(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252")));
					hop = true;
				} else if (!chauf && entryName.equals("Chauffeur.csv")) {
					mapper.importChauffeurs(CSV.readChauffeur(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252")));
					chauf = true;
				} else if (!app && chauf && entryName.equals("Appelant.csv")) {
					mapper.importApplants(CSV.readAppelant(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252")));
					app = true;
				} else if (!course && chauf && app && entryName.equals("Course.csv")) {
					mapper.importCourses(CSV.readCourse(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252"),
							mapper));
					course = true;
				} else if (!indisp && chauf && entryName.equals("Indisponibilite.csv")) {
					mapper.importIndisponibilite(CSV.readIndisponibilite(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252"),
							mapper));
					indisp = true;
				} else if (!hop && entryName.equals("Hopital.csv")) {
					mapper.importHopitaux(CSV.readHopital(
							new InputStreamReader(zf.getInputStream(zipentry), "Cp1252")));
					hop = true;
				}
			}
		}
	}
	
	private static void version0(ZipFile zf) throws IOException {
		Enumeration<? extends ZipEntry> entries;
		ZipEntry zipentry;
		Mapper mapper = Mapper.getInstance();
		mapper.deleteAll();
		boolean res = false, hop = false, chauf = false, app = false, course = false, indisp = false, user = false;
		int limite = 4;
		while (!(user && res && hop && chauf && app && course && indisp) && limite > 0) {
			limite--;
			entries = zf.entries();
			while (entries.hasMoreElements()) {
				zipentry = entries.nextElement();
				String entryName = zipentry.getName();

				if (!user && entryName.equals("Utilisateur.csv")) {
					mapper.importUtilisateur(CSV.readUtilisateur(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8),
							mapper));
					user = true;
				} else if (!res && entryName.equals("Residence.csv")) {
					mapper.importResidences(CSV.readResidence(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)));
					res = true;
				} else if (!hop && entryName.equals("Hopital.csv")) {
					mapper.importHopitaux(CSV.readHopital(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)));
					hop = true;
				} else if (!chauf && entryName.equals("Chauffeur.csv")) {
					mapper.importChauffeurs(CSV.readChauffeur(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)));
					chauf = true;
				} else if (!app && chauf && entryName.equals("Appelant.csv")) {
					mapper.importApplants(CSV.readAppelant(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)));
					app = true;
				} else if (!course && chauf && app && entryName.equals("Course.csv")) {
					mapper.importCourses(CSV.readCourse(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8),
							mapper));
					course = true;
				} else if (!indisp && chauf && entryName.equals("Indisponibilite.csv")) {
					mapper.importIndisponibilite(CSV.readIndisponibilite(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8),
							mapper));
					indisp = true;
				} else if (!hop && entryName.equals("Hopital.csv")) {
					mapper.importHopitaux(CSV.readHopital(
							new InputStreamReader(zf.getInputStream(zipentry), StandardCharsets.UTF_8)));
					hop = true;
				}
			}
		}
	}

	public static void autoSave() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		LocalDate lastSave = settings.getLastSave();
		LocalDate time = LocalDate.now();
		if (lastSave.isEqual(time) || lastSave.plusDays(1+Variable.SAVE_FREQUENCE_JOUR/*settings.getSaveFrequency()*/).isBefore(time)) {
			String pathName = settings.getPathSaveDirectory() + System.getProperty("file.separator")
					+ DateTime.getNameSave(time);
			creatSave(pathName, mapper);
			settings.save();
		}
	}
	
	public static boolean export() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		String pathExport = settings.getPathSaveDirectory() + System.getProperty("file.separator")
		+ "export";
		boolean result = creatExport(pathExport,mapper);
		return result;
	}

	public static boolean save() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		LocalDateTime time = LocalDateTime.now();
		String pathName = settings.getPathSaveDirectory() + System.getProperty("file.separator")
				+ DateTime.getNameSave(time);
		boolean result = creatSave(pathName, mapper);
		return result;
	}
	
	public static void saveDeco() {
		Mapper mapper = Mapper.getInstance();
		Settings settings = mapper.getSettings();
		LocalDateTime time = LocalDateTime.now();
		String pathName = settings.getPathSaveDirectory() + System.getProperty("file.separator")
				+ DateTime.getNameSave(time)+" "+UserManager.getFullName();
		creatSave(pathName, mapper);
	}

	private static boolean creatSave(String pathName, Mapper mapper) {
		boolean saveOk = true;
		String separator = System.getProperty("file.separator");
		File save = new File(pathName);
		if (save.exists()) {
			FileManager.deleteContent(save);
		} else {
			save.mkdirs();
		}
		
		File write = new File(save.getAbsolutePath() + separator + "VERSION");
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(write), StandardCharsets.UTF_8))) {
			writer.write(""+CURRENT_VERSION);
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			write.delete();
		}
		
		write = new File(save.getAbsolutePath() + separator + "Appelant.csv");
		saveOk &= CSV.wirte(mapper.getAllAppelant(), write);
		write = new File(save.getAbsolutePath() + separator + "Chauffeur.csv");
		saveOk &=CSV.wirte(mapper.getAllChauffeur(), write);
		write = new File(save.getAbsolutePath() + separator + "Course.csv");
		saveOk &=CSV.wirte(mapper.getAllCourse(), write);
		write = new File(save.getAbsolutePath() + separator + "Indisponibilite.csv");
		saveOk &=CSV.wirte(mapper.getAllIndisponibilite(), write);
		write = new File(save.getAbsolutePath() + separator + "Hopital.csv");
		saveOk &=CSV.wirte(mapper.getListHopital(), write);
		write = new File(save.getAbsolutePath() + separator + "Residence.csv");
		saveOk &=CSV.wirte(mapper.getListResidence(), write);
		write = new File(save.getAbsolutePath() + separator + "Utilisateur.csv");
		saveOk &=CSV.wirte(mapper.getAllUser(), write);

		FileManager.compress(save.getAbsolutePath(),extention);
		FileManager.delete(save);
		return saveOk;
	}
	
	private static boolean creatExport(String pathExport, Mapper mapper) {
		boolean exportOk = true;
		String separator = System.getProperty("file.separator");
		File export = new File(pathExport);
		if (export.exists()) {
			FileManager.deleteContent(export);
		} else {
			export.mkdirs();
		}

		File write = new File(export.getAbsolutePath() + separator + "Appelant.csv");
		exportOk &= CSV.export(mapper.getAllAppelant(), write);
		write = new File(export.getAbsolutePath() + separator + "Chauffeur.csv");
		exportOk &=CSV.export(mapper.getAllChauffeur(), write);
		write = new File(export.getAbsolutePath() + separator + "Course.csv");
		exportOk &=CSV.export(mapper.getAllCourse(), write);
		write = new File(export.getAbsolutePath() + separator + "Indisponibilite.csv");
		exportOk &=CSV.export(mapper.getAllIndisponibilite(), write);
		write = new File(export.getAbsolutePath() + separator + "Hopital.csv");
		exportOk &=CSV.export(mapper.getListHopital(), write);
		write = new File(export.getAbsolutePath() + separator + "Residence.csv");
		exportOk &=CSV.export(mapper.getListResidence(), write);
		write = new File(export.getAbsolutePath() + separator + "Utilisateur.csv");
		exportOk &=CSV.export(mapper.getAllUser(), write);
		
		return exportOk;
	}
}

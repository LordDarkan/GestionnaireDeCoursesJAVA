package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import models.Appelant;
import models.Chauffeur;
import models.Course;
import models.Hopital;
import models.Indisponibilite;
import models.Residence;
import models.Utilisateur;
import util.DateTime;
import util.Titre;
import util.TypeCourse;

public class CSV {

	public static List<Appelant> readAppelant(InputStreamReader inputStreamReader) {
		List<Appelant> appelants = new LinkedList<Appelant>();
		Appelant appelant;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					if (row[0].trim().matches("\\p{Digit}+")) {
						appelant = new Appelant();
						try {
							appelant.setId(Long.parseLong(row[0].trim()));
						} catch (Exception e) {
							// e.printStackTrace();
							System.out.println(row[0].trim());
						}
						appelant.setTitre(Titre.get(row[1].trim()));
						appelant.setName(row[2].trim());
						appelant.setFirstname(row[3].trim());
						try {
							appelant.setBirthday(DateTime.toLocalDate(row[4].trim()));
						} catch (Exception e) {
						}
						appelant.setTel(row[5].trim());
						appelant.setResidence(row[6].trim());
						appelant.setAdresse(row[7].trim());
						appelant.setCp(row[8].trim());
						appelant.setLocalite(row[9].trim());
						appelant.setQuartier(row[10].trim());
						appelant.setFamilleStr(row[11].trim());
						appelant.setAffiniteStr(row[12].trim());
						appelant.setRestrictionStr(row[13].trim());
						appelant.setMutualite(row[14].trim());
						appelant.setPayement(row[15].trim());
						try {
							appelant.setCotisation(Integer.parseInt(row[16].trim()));
						} catch (Exception e) {
							// e.printStackTrace();
						}
						appelant.setMobilite(row[17].trim());
						appelant.setAideParticuliere(row[18].trim());
						appelant.setInfos(row[19].trim());
						appelant.setRemarques(row[20].trim());
						appelants.add(appelant);
					}
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

	public static List<Chauffeur> readChauffeur(InputStreamReader inputStreamReader) {
		List<Chauffeur> chauffeurs = new LinkedList<Chauffeur>();
		Chauffeur chauffeur;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					if (row[0].trim().matches("\\p{Digit}+")) {
						chauffeur = new Chauffeur();
						try {
							chauffeur.setId(Long.parseLong(row[0].trim()));
						} catch (Exception e) {
							// e.printStackTrace();
							System.out.println(row[0].trim());
						}
						chauffeur.setTitre(Titre.get(row[1].trim()));
						chauffeur.setName(row[2].trim());
						chauffeur.setFirstname(row[3].trim());
						chauffeur.setTel(row[4].trim());
						chauffeur.setAdresse(row[5].trim());
						chauffeur.setCp(row[6].trim());
						chauffeur.setLocalite(row[7].trim());
						chauffeur.setInfos(row[8].trim());
						chauffeurs.add(chauffeur);
					}
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
		return chauffeurs;
	}

	public static List<Course> readCourse(InputStreamReader inputStreamReader, Mapper mapper) {
		List<Course> courses = new LinkedList<Course>();
		Course course;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					course = new Course();
					course.setNameCreation(row[0].trim());
					course.setDateCreation(DateTime.toLocalDate(row[1].trim()));
					course.setHeureCreation(DateTime.toLocalTime(row[2].trim()));
					Long id = Long.parseLong(row[3].trim());
					course.setAppelant(mapper.getAppelant(id));
					course.setDate(DateTime.toLocalDate(row[4].trim()));
					if (!row[5].trim().isEmpty()) {
						id = Long.parseLong(row[5].trim());
						course.setChauffeur(mapper.getChauffeur(id));
					}
					course.setNameAttribution(row[6].trim());
					if (!row[7].trim().isEmpty()) {
						course.setDateAttribution(DateTime.toLocalDate(row[7].trim()));
					}
					course.setHeureDomicile(DateTime.toLocalTime(row[8].trim()));
					course.setResidence(row[9].trim());
					course.setAdresseDep(row[10].trim());
					course.setCpDep(row[11].trim());
					course.setLocaliteDep(row[12].trim());
					course.setTypeCourse(TypeCourse.get(row[13].trim()));
					course.setMutuelle(row[14].trim().equalsIgnoreCase("oui"));
					course.setHeureRDV(DateTime.toLocalTime(row[15].trim()));
					course.setHopital(row[16].trim());
					course.setAdresseDest(row[17].trim());
					course.setCpDest(row[18].trim());
					course.setLocaliteDest(row[19].trim());
					course.setAttente(row[20].trim().equalsIgnoreCase("oui"));
					if (!row[21].trim().isEmpty()) {
						id = Long.parseLong(row[21].trim());
						course.setChauffeurSec(mapper.getChauffeur(id));
					}
					course.setHeureRetour(DateTime.toLocalTime(row[22].trim()));
					course.setAdresseRet(row[23].trim());
					course.setCpRet(row[24].trim());
					course.setLocaliteRet(row[25].trim());
					course.setNotes(row[26].trim());
					course.setAnnulation(row[27].trim().equalsIgnoreCase("oui"));
					course.setRaisonAnnulation(row[28].trim());
					course.setNameAnnulation(row[29].trim());
					if (!row[30].trim().isEmpty()) {
						course.setDateAnnulation(DateTime.toLocalDate(row[30].trim()));
					}

					courses.add(course);
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
		return courses;
	}

	public static List<Indisponibilite> readIndisponibilite(InputStreamReader inputStreamReader, Mapper mapper) {
		List<Indisponibilite> indisponibilites = new LinkedList<Indisponibilite>();
		Indisponibilite indisp;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					indisp = new Indisponibilite();
					Long id = Long.parseLong(row[0].trim());
					indisp.setIdChauffeur(id);
					indisp.setDateStart(DateTime.toLocalDate(row[1].trim()));
					indisp.setHeureStart(DateTime.toLocalTime(row[2].trim()));
					indisp.setDateEnd(DateTime.toLocalDate(row[3].trim()));
					indisp.setHeureEnd(DateTime.toLocalTime(row[4].trim()));
					indisp.setDescription(row[5].trim());

					indisponibilites.add(indisp);
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
		return indisponibilites;
	}
	
	public static List<Hopital> readHopital(InputStreamReader inputStreamReader) {
		List<Hopital> hopitaux = new LinkedList<Hopital>();
		Hopital hopital;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					hopital = new Hopital();
					hopital.setName(row[0].trim());
					hopital.setAdresse(row[1].trim());
					hopital.setCp(row[2].trim());
					hopital.setLocalite(row[3].trim());
					hopital.setTel(row[4].trim());
					hopitaux.add(hopital);
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
		return hopitaux;
	}

	public static List<Residence> readResidence(InputStreamReader inputStreamReader) {
		List<Residence> residences = new LinkedList<Residence>();
		Residence residence;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					residence = new Residence();
					residence.setName(row[0].trim());
					residence.setAdresse(row[1].trim());
					residence.setCp(row[2].trim());
					residence.setLocalite(row[3].trim());
					residence.setTel(row[4].trim());
					residences.add(residence);
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
		return residences;
	}
	


	public static List<Utilisateur> readUtilisateur(InputStreamReader inputStreamReader, Mapper mapper) {
		List<Utilisateur> utilisateurs = new LinkedList<Utilisateur>();
		Utilisateur utilisateur;
		BufferedReader br = null;
		String line = "";
		String[] row;
		String cvsSplitBy = ";";
		String temp;
		boolean end = false;
		try {
			br = new BufferedReader(inputStreamReader);
			br.readLine();
			while (!end && (line = br.readLine()) != null) {
				while (!end && !line.endsWith("END")) {
					if ((temp = br.readLine()) != null) {
						line += "\n" + temp;
					} else {
						end = true;
					}
				}
				if (!end) {
					row = line.split(cvsSplitBy);
					utilisateur = new Utilisateur();
					utilisateur.setName(row[0].trim());
					utilisateur.setFirstname(row[1].trim());
					utilisateur.setAdmin(row[2].trim().equalsIgnoreCase("oui"));
					utilisateurs.add(utilisateur);
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
		return utilisateurs;
	}

	public static void wirteAppelant(List<Appelant> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Appelant.getEnTeteCsv());
			writer.newLine();
			for (Appelant appelant : list) {
				writer.write(appelant.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteChauffeur(List<Chauffeur> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Chauffeur.getEnTeteCsv());
			writer.newLine();
			for (Chauffeur chauffeur : list) {
				writer.write(chauffeur.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteCourse(List<Course> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Course.getEnTeteCsv());
			writer.newLine();
			for (Course course : list) {
				writer.write(course.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteIndisponibilite(List<Indisponibilite> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Indisponibilite.getEnTeteCsv());
			writer.newLine();
			for (Indisponibilite indispo : list) {
				writer.write(indispo.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteHopital(List<Hopital> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Hopital.getEnTeteCsv());
			writer.newLine();
			for (Hopital hopital : list) {
				writer.write(hopital.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteResidence(List<Residence> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Residence.getEnTeteCsv());
			writer.newLine();
			for (Residence residence : list) {
				writer.write(residence.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteUtilisateur(List<Utilisateur> list, File file) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			writer.write(Residence.getEnTeteCsv());
			writer.newLine();
			for (Utilisateur utilisateur : list) {
				writer.write(utilisateur.getRowCsv());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

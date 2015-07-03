package com.yogidev.android.mytennisapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String prenom;
	private String nom;
	private int anneeNaissance;
	private int age;
	private String club;
	private int classement;
	private int serie;
	private boolean negatif;
	private HashMap<Epreuve, List<Rencontre>> mapEpreuvesRencontres;
	private int capitalDepart;
	private int nbVictoiresComptees;
	private int nbBonus = 0;

	/**
	 * Constructeur
	 * 
	 * @param prenom
	 * @param nom
	 * @param classement
	 */
	public Player(String prenom, String nom, String classement) throws UnknownSerieException {
		super();
		this.prenom = prenom;
		this.nom = nom;
		setClassement(classement);
		this.capitalDepart = Constants.MAP_CAPITAUX_DEPART_PAR_CLASSEMENTS.get(getClassementAsString());
		this.mapEpreuvesRencontres = new HashMap<Epreuve, List<Rencontre>>();
	}

	/**
	 * Récupération de la série d'un joueur
	 * 
	 * @return int serie
	 */
	public int retrieveSerie() throws UnknownSerieException {
		int serie = -1;

		if (Constants.CLASSEMENTS_QUATRIEME_SERIE.contains(getClassementAsString()))
			serie = Constants.QUATRIEME_SERIE;
		if (Constants.CLASSEMENTS_TROISIEME_SERIE.contains(getClassementAsString()))
			serie = Constants.TROISIEME_SERIE;
		if (Constants.CLASSEMENTS_DEUXIEME_SERIE.contains(getClassementAsString()))
			serie = Constants.DEUXIEME_SERIE;

		if (serie == -1) {
			throw new UnknownSerieException("Impossible de déterminer la série correspondant au classement (" + getClassementAsString() + ") du joueur "
					+ getNomComplet());
		}

		return serie;
	}

	/**
	 * Un joueur est_il négatif ?
	 * 
	 * @return boolean negatif
	 */
	public boolean isClassementNegatif() {
		return classement < 0;
	}
	
	/**
	 * Le joueur est-il non classé ?
	 * 
	 * @return
	 */
	public boolean isNonClasse() {
		return classement==500 ;
	}

	public List<Epreuve> getEpreuves() {
		return new ArrayList<Epreuve>(mapEpreuvesRencontres.keySet());
	}

	public List<Rencontre> getRencontres() {
		ArrayList<Rencontre> listRencontres = new ArrayList<Rencontre>();
		for (List<Rencontre> tabRencontres : mapEpreuvesRencontres.values()) {
			for (Rencontre r : tabRencontres) {
				listRencontres.add(r);
			}
		}
		return listRencontres;
	}

	public List<Rencontre> getVictoires() {
		ArrayList<Rencontre> listVictoires = new ArrayList<Rencontre>();
		for (List<Rencontre> tabRencontres : mapEpreuvesRencontres.values()) {
			for (Rencontre r : tabRencontres) {
				if (r.isVictoire())
					listVictoires.add(r);
			}
		}
		return listVictoires;
	}

	/**
	 * Ajout d'une épreuve.
	 * 
	 * @param epreuve
	 */
	public void addEpreuve(Epreuve epreuve) {
		this.mapEpreuvesRencontres.put(epreuve, new ArrayList<Rencontre>());
	}

	public void deleteEpreuve(Epreuve epreuve) {
		this.mapEpreuvesRencontres.remove(epreuve);
	}

	/**
	 * Ajout d'une rencontre.
	 * 
	 * @param rencontre
	 */
	public void addRencontre(Epreuve epreuve, Rencontre rencontre) {
		this.mapEpreuvesRencontres.get(epreuve).add(rencontre);
	}

	/**
	 * Suppression d'une rencontre.
	 * 
	 * @param rencontre
	 */
	public void deleteRencontre(Epreuve epreuve, Rencontre rencontre) {
		this.mapEpreuvesRencontres.get(epreuve).remove(rencontre);
	}

	public boolean hasClassementInferieurA(int autreClassement) {
		return classement > autreClassement;
	}

	/**
	 * Calcul du type de défaite d'une rencontre
	 * 
	 * @param rencontre
	 * @return int Type de la défaite -1 si la rencontre a été victorieuse
	 */
	public int getTypeDefaite(Rencontre rencontre) {
		int typeDefaite = -1;
		if (!rencontre.isVictoire()) {
			int classementAdversaire = rencontre.getAdversaire().getClassement();
			String classementAdversaireAsString = rencontre.getAdversaire().getClassementAsString();

			if (hasClassementInferieurA(classementAdversaire))
				typeDefaite = Constants.DEFAITE_ECHELON_SUPERIEUR;
			else if (classement == classementAdversaire)
				typeDefaite = Constants.DEFAITE_ECHELON_EGAL;
			else if (Constants.LISTE_CLASSEMENTS.indexOf(getClassementAsString()) - Constants.LISTE_CLASSEMENTS.indexOf(classementAdversaireAsString) == 1)
				typeDefaite = Constants.DEFAITE_1_ECHELON_INFERIEUR;
			else if (Constants.LISTE_CLASSEMENTS.indexOf(getClassementAsString()) - Constants.LISTE_CLASSEMENTS.indexOf(classementAdversaireAsString) == 2)
				typeDefaite = Constants.DEFAITE_2_ECHELON_INFERIEUR;
			else
				typeDefaite = Constants.DEFAITE_3_ECHELON_INFERIEUR_ET_PLUS;
		}
		return typeDefaite;
	}

	/**
	 * Renvoie le classement inférieur du joueur (renvoie NC pour un NC)
	 * 
	 * @return
	 */
	public String getClassementInferieur() {
		return this.isNonClasse()?Constants.LISTE_CLASSEMENTS.get(0):Constants.LISTE_CLASSEMENTS.get(Constants.LISTE_CLASSEMENTS.indexOf(getClassementAsString()) - 1);
	}

	public int getPointsVictoire(Rencontre rencontre) {
		int pointsVictoire = 0;
		if (rencontre.isVictoire()) {
			String classementAdversaireAsString = rencontre.getAdversaire().getClassementAsString();
			// Rang du classement de l'advesaire dans le tableau
			// Constants.LISTE_CLASSEMENTS
			int indexClassementAdversaire = Constants.LISTE_CLASSEMENTS.indexOf(classementAdversaireAsString);
			// Rang du classement du joueur dans le tableau
			// Constants.LISTE_CLASSEMENTS
			int indexClassement = Constants.LISTE_CLASSEMENTS.indexOf(getClassementAsString());

			if (indexClassementAdversaire - indexClassement >= 2)
				pointsVictoire = Constants.VICTOIRE_2_ECHELON_SUPERIEUR_ET_PLUS;
			else if (indexClassementAdversaire - indexClassement == 1)
				pointsVictoire = Constants.VICTOIRE_1_ECHELON_SUPERIEUR;
			else if (indexClassementAdversaire - indexClassement == 0)
				pointsVictoire = Constants.VICTOIRE_ECHELON_EGAL;
			else if (indexClassementAdversaire - indexClassement == -1)
				pointsVictoire = Constants.VICTOIRE_1_ECHELON_INFERIEUR;
			else if (indexClassementAdversaire - indexClassement == -2)
				pointsVictoire = Constants.VICTOIRE_2_ECHELON_INFERIEUR;
			else if (indexClassementAdversaire - indexClassement == -3)
				pointsVictoire = Constants.VICTOIRE_3_ECHELON_INFERIEUR;
			else
				pointsVictoire = Constants.VICTOIRE_4_ECHELON_INFERIEUR_ET_PLUS;
		}
		return pointsVictoire;
	}
	
	public int getBonusVictoire(Rencontre rencontre, boolean incrementBonus) {
		int bonus = 0;
		// Bonus pour les championnats régionaux de Ligue y compris dans les phases départementales disputées par élimination directe : +15 points
		// Bonification plafonnée à 45 points (3 victoires).
		if (rencontre.getType().equals(Constants.CHAMPIONNAT_INDIVIDUEL) && getNbBonus()<Constants.MAX_NB_BONUS_CHAMPIONNAT_INDIVIDUEL) {
			bonus = Constants.BONUS_CHAMPIONNAT_INDIVIDUEL;
			rencontre.setBonus(true);
			if (incrementBonus) {
				nbBonus++;
			}
		}
		return bonus;
	}
	
	public int applyCoeff(Rencontre rencontre, int pointsVictoire) {
		// application du coefficient en fonction du format du match
		float f = rencontre.getFormat()==null?1f:Constants.MAP_COEFF_FORMAT_COURT.get(rencontre.getFormat());
		return Math.round(pointsVictoire*f);
	}

	/**
	 * Calcul du nombre de victoires. (V)
	 * 
	 * @return int Le nombre de victoires
	 */
	public int getNbVictoires() {
		int nbVictoires = 0;
		for (Rencontre r : getRencontres()) {
			if (r.isVictoire()) {
				nbVictoires++;
			}
		}
		return nbVictoires;
	}

	/**
	 * Calcul du nombre de défaites à échelon égal. (E)
	 * 
	 * @return int Le nombre de défaites à échelon égal
	 */
	public int getNbDefaiteMemeEchelon() {
		int nbDefaites = 0;
		for (Rencontre r : getRencontres()) {
			if (!r.isVictoire()) {
				if (getTypeDefaite(r) == Constants.DEFAITE_ECHELON_EGAL)
					nbDefaites++;
			}
		}
		return nbDefaites;
	}

	/**
	 * Calcul du nombre de défaites à 1 échelon inférieur. (I)
	 * 
	 * @return int Le nombre de défaites à 1 échelon inférieur
	 */
	public int getNbDefaite1EchelonInferieur() {
		int nbDefaites = 0;
		for (Rencontre r : getRencontres()) {
			if (!r.isVictoire()) {
				if (getTypeDefaite(r) == Constants.DEFAITE_1_ECHELON_INFERIEUR)
					nbDefaites++;
			}
		}
		return nbDefaites;
	}

	/**
	 * Calcul du nombre de défaites à 2 échelons inférieurs et plus. (G)
	 * 
	 * @return int Le nombre de défaites à 2 échelon inférieurs et plus
	 */
	public int getNbDefaite2EchelonInferieurEtPlus() {
		int nbDefaites = 0;
		for (Rencontre r : getRencontres()) {
			if (!r.isVictoire()) {
				if (getTypeDefaite(r) == Constants.DEFAITE_2_ECHELON_INFERIEUR || getTypeDefaite(r) == Constants.DEFAITE_3_ECHELON_INFERIEUR_ET_PLUS)
					nbDefaites++;
			}
		}
		return nbDefaites;
	}

	public String getNomComplet() {
		return prenom + " " + nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getAnneeNaissance() {
		return anneeNaissance;
	}

	public void setAnneeNaissance(int anneeNaissance) {
		this.anneeNaissance = anneeNaissance;
		this.setAge(Calendar.getInstance().get(Calendar.YEAR) - anneeNaissance);
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public int getClassement() {
		return classement;
	}

	/**
	 * Récupération du classement au format String (pour affichage).
	 * 
	 * @return String classement
	 */
	public String getClassementAsString() {
		String classementString;
		String s = String.valueOf(classement);
		if (classement == 500) {
			classementString = "NC";
		}
		// 400,300,150,-150,-300 => 40, 30, 15, -15, -30
		else if (classement == 400 || classement == 300 || classement == 150 || classement == -150 || classement == -300) {
			classementString = s.substring(0, s.length() - 1);
		} else if (classement == 0) {
			classementString = "0";
		} else if (classement == -400) {
			classementString = "Promotion";
		} else if (classement == -500) {
			classementString = "1ereSerie";
		} else {
			// 305, 155, 56, -26 => 30_5, 15_5, 5_6, -2_6
			classementString = s.substring(0, s.length() - 1) + "_" + s.substring(s.length() - 1);
		}

		return classementString;
	}

	/**
	 * Transformation des classements en String en classement en nombres (pour
	 * comparaison).
	 * 
	 * @param classementString
	 * @throws UnknownSerieException
	 */
	public void setClassement(String classementString) throws UnknownSerieException {

		this.classement = TennisCalculRules.getClassementAsNumber(classementString);

		// Calcul de la série
		this.serie = retrieveSerie();
		// Negatif ou non ?
		this.negatif = isClassementNegatif();
		// Récupération du nombre de victoires comptees pour ce classement
		this.nbVictoiresComptees = Constants.MAP_NB_VICTOIRES_PAR_CLASSEMENTS.get(getClassementAsString());
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}

	public boolean isNegatif() {
		return negatif;
	}

	public void setNegatif(boolean negatif) {
		this.negatif = negatif;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public int getCapitalDepart() {
		return capitalDepart;
	}

	public int getNbVictoiresComptees() {
		return nbVictoiresComptees;
	}

	public void setNbVictoiresComptees(int nbVictoiresComptees) {
		this.nbVictoiresComptees = nbVictoiresComptees;
	}

	public void setCapitalDepart(int capitalDepart) {
		this.capitalDepart = capitalDepart;
	}

	public HashMap<Epreuve, List<Rencontre>> getMapEpreuvesRencontres() {
		return mapEpreuvesRencontres;
	}

	public void setMapEpreuvesRencontres(HashMap<Epreuve, List<Rencontre>> mapEpreuvesRencontres) {
		this.mapEpreuvesRencontres = mapEpreuvesRencontres;
	}
	
	public int getNbBonus() {
		return nbBonus;
	}

	public void setNbBonus(int nbBonus) {
		this.nbBonus = nbBonus;
	}

	@Override
	public String toString() {
		String epreuvesString = "";
		for (Epreuve e : getEpreuves()) {
			epreuvesString += "\n----------------------------------------------------------";
			epreuvesString += "\n" + e.getNom() + " (" + e.getType() + ") : ";
			String rencontresString = "";
			for (int i = 0; i < mapEpreuvesRencontres.get(e).size(); i++) {
				Rencontre r = mapEpreuvesRencontres.get(e).get(i);
				rencontresString += "\n\tMatch " + (i + 1) + " : " + r.getAdversaire().toString() + " => "
						+ (r.isWO() ? "WO" : r.isVictoire() ? ("VICTOIRE => Nb points = " + getPointsVictoire(r)) : "DEFAITE de type  = " + getTypeDefaite(r));
			}
			epreuvesString += rencontresString;
			epreuvesString += "\n";
		}
		return "JOUEUR " + prenom + " " + nom + " (" + getClassementAsString().replace("-", "\\") + ")" + epreuvesString;
	}
}

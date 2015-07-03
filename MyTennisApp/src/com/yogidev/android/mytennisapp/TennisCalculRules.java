package com.yogidev.android.mytennisapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class TennisCalculRules {

	/**
	 * Calcul du nombre de victoires additionnelles
	 * 
	 * @param V
	 *            le nombre de victoires (victoires par wo comprises)
	 * @param E
	 *            le nombre de défaites à échelon égal (défaites par wo exclues)
	 * @param I
	 *            le nombre de défaites à un échelon inférieur
	 * @param G
	 *            le nombre de défaites à deux échelons inférieurs et plus, et
	 *            par wo à partir du 3e
	 * @return int nb
	 */
	public static int getNbAdditionalVictories(Player p) {

		int nb = 0;
		int V = p.getNbVictoires();
		int E = p.getNbDefaiteMemeEchelon();
		int I = p.getNbDefaite1EchelonInferieur();
		int G = p.getNbDefaite2EchelonInferieurEtPlus();

		// Calcul de V-E-2I-5G
		int ratio = V - E - 2 * I - 5 * G;

		// 4ème SERIE
		if (p.getSerie() == Constants.QUATRIEME_SERIE) {
			if (ratio >= 0 && ratio <= 4) {
				nb = 1;
			} else if (ratio >= 5 && ratio <= 9) {
				nb = 2;
			} else if (ratio >= 10 && ratio <= 14) {
				nb = 3;
			} else if (ratio >= 15 && ratio <= 19) {
				nb = 4;
			} else if (ratio >= 20 && ratio <= 24) {
				nb = 5;
			} else if (ratio >= 25) {
				nb = 6;
			}
		}
		// 3ème SERIE ou 2ème SERIE POSITIF (de 15 à 0)
		else if (p.getSerie() == Constants.TROISIEME_SERIE || (p.getSerie() == Constants.DEUXIEME_SERIE && !p.isNegatif())) {
			if (ratio >= 0 && ratio <= 7) {
				nb = 1;
			} else if (ratio >= 8 && ratio <= 14) {
				nb = 2;
			} else if (ratio >= 15 && ratio <= 22) {
				nb = 3;
			} else if (ratio >= 23 && ratio <= 29) {
				nb = 4;
			} else if (ratio >= 30 && ratio <= 39) {
				nb = 5;
			} else if (ratio >= 40) {
				nb = 6;
			}
		}
		// 2ème SERIE NEGATIF
		else if (p.getSerie() == Constants.DEUXIEME_SERIE && p.isNegatif()) {
			if (ratio >= 0 && ratio <= 9) {
				nb = 1;
			} else if (ratio >= 10 && ratio <= 19) {
				nb = 2;
			} else if (ratio >= 20 && ratio <= 24) {
				nb = 3;
			} else if (ratio >= 25 && ratio <= 29) {
				nb = 4;
			} else if (ratio >= 30 && ratio <= 34) {
				nb = 5;
			} else if (ratio >= 35 && ratio <= 44) {
				nb = 6;
			} else if (ratio >= 45) {
				nb = 7;
			}
		}

		return nb;
	}

	/**
	 * Récupération du classement au format entier à partir d'un String.
	 * 
	 * @param classementString
	 * @return int returnClassement
	 */
	public static int getClassementAsNumber(String classementString) {
		int returnClassement;
		// NC => 500
		if (classementString.equals("NC")) {
			returnClassement = 500;
		}
		// 40, 30, 15, -15, -30 => 400,300,150,-150,-300
		else if (classementString.equals("40") || classementString.equals("30") || classementString.equals("15") || classementString.equals("-15")
				|| classementString.equals("-30")) {
			returnClassement = Integer.parseInt(classementString + "0");
		}
		// Promotion => -400
		else if (classementString.equals("Promotion")) {
			returnClassement = -400;
		}
		// 1ereSerie => -500
		else if (classementString.equals("1ereSerie")) {
			returnClassement = -500;
		}
		// 30_5, 15_5, 5_6, -2_6 => 305, 155, 56, -26
		else {
			returnClassement = Integer.parseInt(classementString.replace("_", ""));
		}
		return returnClassement;
	}

	/**
	 * Récupération du classement au format String à partir d'un entier.
	 * 
	 * @param classement
	 * @return String classement
	 */
	public static String getClassementAsString(int classement) {
		String classementString;
		String s = String.valueOf(classement);
		if (classement == 500) {
			classementString = "NC";
		}
		// 400,300,150,-150,-300 => 40, 30, 15, -15, -30
		else if (classement == 400 || classement == 300 || classement == 150 || classement == -150 || classement == -300) {
			classementString = s.substring(0, s.length() - 1);
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
	 * Récupération du classement au format String à partir d'un entier.
	 * 
	 * @param classement
	 * @return String classement
	 */
	public static String getClassementAsStringAffichable(int classement) {
		String classementString;
		String s = String.valueOf(classement);
		if (classement == 500) {
			classementString = "NC";
		}
		// 400,300,150,-150,-300 => 40, 30, 15, -15, -30
		else if (classement == 400 || classement == 300 || classement == 150 || classement == -150 || classement == -300) {
			classementString = s.substring(0, s.length() - 1);
		} else if (classement == -400) {
			classementString = "Promotion";
		} else if (classement == -500) {
			classementString = "1ereSerie";
		} else {
			// 305, 155, 56, -26 => 30_5, 15_5, 5_6, -2_6
			classementString = s.substring(0, s.length() - 1) + "/" + s.substring(s.length() - 1);
		}

		return classementString;
	}

	/**
	 * Calcul du nombre de victoires prises en compte au final pour le tour de
	 * calcul.
	 * 
	 * @param p
	 *            Le joueur
	 * @return Le nombre de victoires
	 */
	public static int getNbTotalVictoiresPrisesEnCompte(Player p) {
		return p.getNbVictoiresComptees() + getNbAdditionalVictories(p);
	}

	/**
	 * Calcul des points correspondants aux victoires d'un joueur.
	 * 
	 * @param p
	 *            Le joueur
	 * @return Le total des points "victoires"
	 */
	public static int getTotalPointsVictoires(Player p) {
		int total = 0;
		for (Rencontre r : getBestVictoiresPrisesEnCompte(p)) {
			int points = p.getPointsVictoire(r) + p.getBonusVictoire(r, true); 
			total += p.applyCoeff(r, points);
		}
		return total;
	}

	/**
	 * Calcul du total des points d'un joueur (capital départ + victoires)
	 * 
	 * @param p
	 *            Le joueur
	 * @return Le total des points
	 */
	public static int getTotalPointsVictoiresPlusCapital(Player p) {
		return getTotalPointsVictoires(p) + p.getCapitalDepart();
	}

	/**
	 * Récupération des meilleures victoires prises en compte triées par ordre
	 * de classement. (plus haut classements en 1er)
	 * 
	 * @param p
	 *            Le joueur
	 * @return La liste des victoires
	 */
	public static List<Rencontre> getBestVictoiresPrisesEnCompte(Player p) {
		List<Rencontre> l = p.getVictoires();
		List<Rencontre> ret = new ArrayList<Rencontre>();
		Collections.sort(l);
		
		int a = getNbTotalVictoiresPrisesEnCompte(p);
		int b = l.size();
		if (b != 0) {
			// Cas où il y a moins de victoires que le nbre total => on les prend toutes
			boolean c = (b < a);
			//if (l.size() < getNbTotalVictoiresPrisesEnCompte(p))
			if (c) {
				ret = l;
			}
			// Cas où on ne doit prendre que les meilleures victoires
			else {
				ret = l.subList(0, TennisCalculRules.getNbTotalVictoiresPrisesEnCompte(p));
			}
		}
		return ret;
	}

	/**
	 * Calcul du nombre de matches (défaites par WO exclues)
	 * 
	 * @param p
	 * @return
	 */
	public static int getNbMatchesSaufDefaitesParWO(Player p) {
		int nbMatchesSaufDefaitesParWO = 0;
		for (Rencontre r : p.getRencontres()) {
			if (r.isVictoire() || !r.isWO()) {
				nbMatchesSaufDefaitesParWO++;
			}
		}
		return nbMatchesSaufDefaitesParWO;
	}

	/**
	 * Recherche du bonus pour absence de défaite significative (à échelon égal
	 * ou inférieur).
	 * 
	 * @param p
	 *            Le joueur
	 * @return boolean True si le bonus doit être compté
	 * 
	 */
	public static boolean hasBonusAbsenceDefaiteSignificative(Player p) {
		boolean absenceDef = (p.getNbDefaiteMemeEchelon() + p.getNbDefaite1EchelonInferieur() + p.getNbDefaite2EchelonInferieurEtPlus() == 0);
		boolean auMoins30_3 = !p.hasClassementInferieurA(TennisCalculRules.getClassementAsNumber("30_3"));
		boolean auMoins10Matches = getNbMatchesSaufDefaitesParWO(p) >= 10;
		return absenceDef && auMoins30_3 && auMoins10Matches;
	}

	/**
	 * Récupération du bonus pour absence de défaite signicative.
	 * 
	 * @throws UnknownSerieException
	 * 
	 */
	public static int getBonusAbsenceDefaiteSignificative(Player p) throws UnknownSerieException {
		int bonus = 0;
		if (hasBonusAbsenceDefaiteSignificative(p)) {
			if (p.retrieveSerie() == Constants.QUATRIEME_SERIE)
				bonus = Constants.BONUS_ABSENCE_DEFAITE_SIGNIFICATIVE_4EME_SERIE;
			else if (p.retrieveSerie() == Constants.TROISIEME_SERIE || p.retrieveSerie() == Constants.DEUXIEME_SERIE)
				bonus = Constants.BONUS_ABSENCE_DEFAITE_SIGNIFICATIVE_3EME_2EME_SERIE;
		}
		return bonus;
	}

	/**
	 * Calcul du total Nb Points Victoires + Capital + Bonus.
	 * 
	 * @param p
	 * @return
	 */
	public static int getTotalFinal(Player p) throws UnknownSerieException {
		return getTotalPointsVictoiresPlusCapital(p) + getBonusAbsenceDefaiteSignificative(p);
	}

	/**
	 * Récupération du plus haut classement auquel le joueur peut être proposé =
	 * PMax. (classement de l'adversaire le mieux classé qu'il a battu au cours
	 * de l'année + un échelon s'il est en 2e ou 3e série ou deux échelons s'il
	 * est en 4e série)
	 * 
	 */
	public static String getPMax(Player p) {
		String pMax = p.getClassementInferieur();
		if (getBestVictoiresPrisesEnCompte(p).size() != 0)
			pMax = TennisCalculRules.getClassementAsString(getBestVictoiresPrisesEnCompte(p).get(0).getAdversaire().getClassement());
		return pMax;
	}

	public static String getClassementCalcule(Player p) throws UnknownSerieException {
		// PMax de départ
		String pMax = TennisCalculRules.getPMax(p);
		boolean maintien = false;
		// Si pas de victoire => desente
		if (p.getNbVictoires() == 0) {
			p.setClassement(p.getClassementInferieur());
		}
		// Sinon on part du classement pMax et on descend jusqu'à ce que le total final soit au dessus de la norme de maintien du joueur
		else {
			while (!maintien && !p.isNonClasse()) {
				p.setClassement(pMax);
				maintien = TennisCalculRules.getTotalFinal(p) > Constants.MAP_NORMES_MAINTIEN.get(p.getClassementAsString());
				pMax = p.getClassementInferieur();
			}
		}
		return TennisCalculRules.getClassementAsStringAffichable(p.getClassement());
	}

}
